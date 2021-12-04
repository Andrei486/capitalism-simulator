package main;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Class representing a Monopoly board. Provides methods that allow
 * player turns to be advanced and players to move along the board.
 */
public class Board implements Serializable {
    private Space[] spaces;
    private final Player[] players;
    private Property[] properties;
    private final TurnOrder turnOrder;
    private final DiceRoller diceRoller;
    private int jailPosition;
    public static final int BOARD_SIZE = 40;
    private int bankruptPlayers;
    private int consecutiveDoubles;
    private HashSet<MonopolyView> gameViews;
    private char moneySymbol;
    public static char MONEY_SYMBOL;
    public static final int TURNS_IN_JAIL = 3;
    public static final int EXIT_JAIL_COST = 50;

    /**
     * Constructs a board with the specified number of players.
     * @param totalPlayers integer number of players (including AI)
     */
    public Board(int totalPlayers, int nAI, InternationalVersion version) {
        int nPlayers = totalPlayers - nAI;
        diceRoller = new DiceRoller();
        initializeBoard(getVersionFile(version));
        bankruptPlayers = 0;
        this.players = new Player[totalPlayers];
        for (int i = 0; i < nPlayers; i++) {
            Player player = new Player("P" + (i + 1), this);
            this.players[i] = player;
        }
        for (int i = 0; i < nAI; i++) {
            Player player = new Player("AI" + (i + 1), this, true);
            this.players[i + nPlayers] = player;
        }
        this.turnOrder = new TurnOrder(this.players);
        this.gameViews = new HashSet<>();
        this.consecutiveDoubles = 0;
    }

    public Board(int nPlayers) {
        this(nPlayers, 0, InternationalVersion.NORTH_AMERICA);
    }

    /**
     * Gets all players that are playing on this board.
     * @return array of all the Players
     */
    public Player[] getPlayers() {
        return this.players;
    }

    /**
     * Gets the player who is currently moving. This player is the one who
     * should act (buy, etc) and can end their turn.
     * @return Player who is currently moving
     */
    public Player getCurrentPlayer() {
        return this.turnOrder.getCurrentPlayer();
    }

    /**
     * Gets the space on this board at a specific position.
     * Positions start at 0 for GO, and increase clockwise up to 39.
     * @param position integer position of the space to get
     * @return the Space at the given position
     */
    public Space getSpace(int position) {
        return this.spaces[position];
    }

    /**
     * Gets all properties present on this board.
     * @return an array of all Properties on this board
     */
    public Property[] getProperties() {
        return this.properties;
    }

    /**
     * Returns the dice roller that controls player movement on this board.
     * @return the DiceRoller object associated to this board.
     */
    public DiceRoller getDiceRoller() {
        return this.diceRoller;
    }

    /**
     * Rolls the dice and moves a Player by the amount rolled.
     * @param player the Player to move.
     */
    public void movePlayer(Player player) {
        diceRoller.roll();
        movePlayerLogic(player);
    }

    public char getMoneySymbol() {
        return moneySymbol;
    }

    /**
     * Controls the logic of what happens when a player is moved.
     * @param player the Player to move.
     */
    public void movePlayerLogic(Player player) {
        consecutiveDoubles = (diceRoller.isDouble()) ? consecutiveDoubles + 1 : 0;

        int oldPosition = player.getPosition();

        if (consecutiveDoubles == 3) {
            player.setJailTimer(Board.TURNS_IN_JAIL);
            player.setPosition(jailPosition);
            consecutiveDoubles = 0;
        } else {
            if(player.getJailTimer() > 0){
                if (diceRoller.isDouble()){
                    player.setJailTimer(0);
                }
                else {
                    this.getSpace(oldPosition).onEndTurn(player);
                }
            }
            else{
                int newPosition = diceRoller.getTotal() + oldPosition;
                newPosition = newPosition % BOARD_SIZE;
                for (int i = 0; i < diceRoller.getTotal(); i++) {
                    this.spaces[(oldPosition + i + 1) % BOARD_SIZE].onPassThrough(player);
                }
                player.setPosition(newPosition);
                this.getSpace(newPosition).onEndTurn(player);
            }
        }
        handleMovePlayerEvent(new MovePlayerEvent(this, player, oldPosition));
    }

    /**
     * Replaces current player's position in queue depending on dice roll.
     * If player rolls a double, player gets another turn.
     */
    public void advanceTurn() {
        do {
            boolean samePlayer = turnOrder.advanceTurnOrder(getDiceRoller().isDouble());
            if (!samePlayer) {
                consecutiveDoubles = 0;
            }
            handleNewTurnEvent(new NewTurnEvent(this));
            if (getCurrentPlayer().isAI()) {
                getCurrentPlayer().getPlayerAI().doTurn();
            }
        } while (getCurrentPlayer().isAI());
    }

    /**
     * Checks whether the game is over. The game is over when all but one player is bankrupt.
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return bankruptPlayers >= (players.length - 1);
    }

    /**
     * Checks whether all non-bankrupt players are AI-controlled.
     * @return true if all remaining players are AI, false otherwise
     */
    public boolean isAllRemainingAI() {
        for (Player player: players) {
            if (!player.isAI() && !player.getIsBankrupt()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the JSON file containing the information needed to load a board of a
     * given international version.
     * @param version the InternationalVersion to load
     * @return File object representing the JSON file
     */
    public File getVersionFile(InternationalVersion version) {
        String filepath = null;
        switch (version) {
            case NORTH_AMERICA:
                filepath = "resources/na.json";
                break;
            case FRANCE:
                filepath = "resources/fr.json";
                break;
            case UNITED_KINGDOM:
                filepath = "resources/uk.json";
                break;
        }
        return new File(filepath);
    }

    /**
     * Initializes the spaces on the board, creating properties as needed.
     */
    private void initializeBoard(File file) {

        JSONTokener tokener;
        JSONObject rootObject = null;
        JSONObject propertyObject;

        try (InputStream input = new FileInputStream(file)) {
            tokener = new JSONTokener(input);
            rootObject = new JSONObject(tokener);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //get money symbol from the JSON file
        MONEY_SYMBOL = rootObject.getString("moneySymbol").charAt(0);
        this.moneySymbol = MONEY_SYMBOL;
        JSONArray propertyArray = rootObject.getJSONArray("spaces");

        this.spaces = new Space[40];

        //define indices which have each type of space
        Property property;
        int[] emptyIndices = {2, 4, 7, 17, 20, 22, 33, 36, 38};
        int[] propertyIndices = {1, 3, 6, 8, 9, 11, 13, 14, 16, 18, 19,
                21, 23, 24, 26, 27, 29, 31, 32, 34, 37, 39
        };
        ColorGroup[] propertyColors = {
                ColorGroup.BROWN, ColorGroup.BROWN,
                ColorGroup.LIGHT_BLUE, ColorGroup.LIGHT_BLUE, ColorGroup.LIGHT_BLUE,
                ColorGroup.PINK, ColorGroup.PINK, ColorGroup.PINK,
                ColorGroup.ORANGE, ColorGroup.ORANGE, ColorGroup.ORANGE,
                ColorGroup.RED, ColorGroup.RED, ColorGroup.RED,
                ColorGroup.YELLOW, ColorGroup.YELLOW, ColorGroup.YELLOW,
                ColorGroup.GREEN, ColorGroup.GREEN, ColorGroup.GREEN,
                ColorGroup.BLUE, ColorGroup.BLUE
        };
        int[] railroadIndices = {5, 15, 25, 35};
        int[] utilityIndices = {12, 28};
        int[] jailIndices = {10, 30};
        int[] goIndices = {0};
        jailPosition = 10;
        this.properties = new Property[propertyIndices.length + railroadIndices.length + utilityIndices.length];

        //create empty spaces
        for (int i : emptyIndices) {
            this.spaces[i] = new EmptySpace();
        }

        //create jail and GO spaces
        this.spaces[jailIndices[0]] = new JailSpace("Jail", jailIndices[0]);
        this.spaces[jailIndices[1]] = new GoToJailSpace("Go to Jail", (JailSpace) this.spaces[jailIndices[0]]);
        this.spaces[goIndices[0]] = new GoSpace("Go");

        //create color groups (don't need JSON file input, colors never change)
        HashMap<ColorGroup, RealEstateGroup> groups = new HashMap<>();
        for (ColorGroup c: ColorGroup.values()) {
            groups.put(c, new RealEstateGroup(c));
        }
        //create real estate spaces with values from JSON file
        for (int i = 0; i < propertyIndices.length; i++) {
            propertyObject = (JSONObject) propertyArray.get(propertyIndices[i]);
            property = new RealEstate(propertyObject.getString("name"), propertyObject.getInt("cost"),
                    propertyColors[i], groups.get(propertyColors[i]));
            this.properties[i] = property;
            this.spaces[propertyIndices[i]] = new PropertySpace(property);
        }
        //create railroad spaces with values from JSON file
        for (int i = 0; i < railroadIndices.length; i++) {
            propertyObject = (JSONObject) propertyArray.get(railroadIndices[i]);
            property = new Railroad(propertyObject.getString("name"));
            this.properties[i + propertyIndices.length] = property;
            this.spaces[railroadIndices[i]] = new PropertySpace(property);
        }
        //create utility spaces with values from JSON file
        for (int i = 0; i < utilityIndices.length; i++) {
            propertyObject = (JSONObject) propertyArray.get(utilityIndices[i]);
            property = new Utility(propertyObject.getString("name"), this.diceRoller);
            this.properties[i + propertyIndices.length + railroadIndices.length] = property;
            this.spaces[utilityIndices[i]] = new PropertySpace(property);
        }
    }

    /**
     * Adds a MonopolyView to the set of registered views.
     * @param view the MonopolyView to be added
     */
    public void addGameView(MonopolyView view) {
        gameViews.add(view);
    }

    /**
     * Remove a MonopolyView from the set of registered views.
     * @param view the MonopolyView to be removed
     */
    public void removeGameView(MonopolyView view) {
        gameViews.remove(view);
    }

    /**Sends the MovePlayerEvent to current listeners
     * @param e the event to be sent out
     */
    public void handleMovePlayerEvent(MovePlayerEvent e){
        for (MonopolyView view: gameViews) {
            view.handleMovePlayer(e);
        }
    }

    /**
     * Sends the BankruptcyEvent to all registered views, advancing the bankrupt player's turn afterwards.
     * @param e the event to send out
     */
    public void handleBankruptcyEvent(BankruptcyEvent e) {
        bankruptPlayers++;
        for (MonopolyView view: gameViews) {
            view.handleBankruptcy(e);
        }
        advanceTurn();
    }

    /**
     * Sends the UpdateMoneyEvent to all registered views.
     * @param e the event to send out
     */
    public void handleUpdateMoneyEvent(UpdateMoneyEvent e) {
        for (MonopolyView view: gameViews) {
            view.handleUpdateMoney(e);
        }
    }

    /**
     * Sends the BuyHouseEvent to all registered views.
     * @param e the event to send out
     */
    public void handleBuyHouseEvent(BuyHouseEvent e) {
        for (MonopolyView view: gameViews) {
            view.handleBuyHouse(e);
        }
    }

    /**
     * Sends the NewTurnEvent to all registered views.
     * @param e the event to send out
     */
    private void handleNewTurnEvent(NewTurnEvent e) {
        for (MonopolyView view: gameViews) {
            view.handleNewTurn(e);
        }
    }

    /**
     * Sends the BuyEvent to all registered views.
     * @param e the event to send out
     */
    public void handleBuyEvent(BuyEvent e) {
        for (MonopolyView view: gameViews) {
            view.handleBuy(e);
        }
    }

    public static Board importBoard(String filepath) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filepath));
        Board board = (Board) in.readObject();
        in.close();
        return board;
    }

    public void exportBoard(String filepath) throws IOException {
        HashSet<MonopolyView> views = this.gameViews;
        this.gameViews = new HashSet<>();
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filepath, false));
        out.writeObject(this);
        out.flush();
        out.close();
        this.gameViews = views;
    }
}
