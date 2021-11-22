package main;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Class representing a Monopoly board. Provides methods that allow
 * player turns to be advanced and players to move along the board.
 */
public class Board {
    private Space[] spaces;
    private final Player[] players;
    private Property[] properties;
    private final TurnOrder turnOrder;
    private final DiceRoller diceRoller;
    private int jailPosition;
    public static final int BOARD_SIZE = 40;
    private int bankruptPlayers;
    private int consecutiveDoubles;
    private final HashSet<MonopolyView> gameViews;
    public static final int TURNS_IN_JAIL = 3;
    public static final int EXIT_JAIL_COST = 50;

    /**
     * Constructs a board with the specified number of players.
     * @param totalPlayers integer number of players (including AI)
     */
    public Board(int totalPlayers, int nAI) {
        int nPlayers = totalPlayers - nAI;
        diceRoller = new DiceRoller();
        initializeBoard();
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
        this(nPlayers, 0);
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
                int newPosition = diceRoller.getTotal() + player.getPosition();
                newPosition = newPosition % BOARD_SIZE;
                for (int i = 0; i < getDiceRoller().getTotal(); i++) {
                    this.spaces[(oldPosition + i + 1) % BOARD_SIZE].onPassThrough(getCurrentPlayer());
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
     * Initializes the spaces on the board, creating properties as needed.
     */
    private void initializeBoard() {
        Property property;
        this.spaces = new Space[40];
        int[] emptyIndices = {2, 4, 7, 17, 20, 22, 33, 36, 38};
        for (int i : emptyIndices) {
            this.spaces[i] = new EmptySpace();
        }
        int[] propertyIndices = {1, 3, 6, 8, 9, 11, 13, 14, 16, 18, 19,
                21, 23, 24, 26, 27, 29, 31, 32, 34, 37, 39
        };
        int[] propertyCosts = {60, 60, 100, 100, 120, 140, 140, 160, 180, 180, 200,
                220, 220, 240, 260, 260, 280, 300, 300, 320, 350, 400
        };
        int[] railroadIndices = {5, 15, 25, 35};
        int[] utilityIndices = {12, 28};
        int[] jailIndices = {10, 30};
        jailPosition = 10;
        int[] goIndices = {0};
        this.properties = new Property[propertyIndices.length + railroadIndices.length + utilityIndices.length];
        this.spaces[jailIndices[0]] = new JailSpace("Jail", jailIndices[0]);
        this.spaces[jailIndices[1]] = new GoToJailSpace("Go to Jail", (JailSpace) this.spaces[jailIndices[0]]);
        this.spaces[goIndices[0]] = new GoSpace("Go");

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
        String[] propertyNames = {
                "Mediterranean Avenue", "Baltic Avenue",
                "Oriental Avenue", "Vermont Avenue", "Connecticut Avenue",
                "St. Charles Place", "States Avenue", "Virginia Avenue",
                "St. James Place", "Tennessee Avenue", "New York Avenue",
                "Kentucky Avenue", "Indiana Avenue", "Illinois Avenue",
                "Atlantic Avenue", "Ventnor Avenue", "Marvin Gardens",
                "Pacific Avenue", "North Carolina Avenue", "Pennsylvania Avenue",
                "Park Place", "Boardwalk"
        };
        String[] railroadNames = {"Reading Railroad", "Pennsylvania Railroad", "B.&O. Railroad", "Short Line"};
        String[] utilityNames = {"Electric Company", "Water Works"};
        HashMap<ColorGroup, RealEstateGroup> groups = new HashMap<>();
        for (ColorGroup c: ColorGroup.values()) {
            groups.put(c, new RealEstateGroup(c));
        }
        for (int i = 0; i < propertyIndices.length; i++) {
            property = new RealEstate(propertyNames[i], propertyCosts[i],
                    propertyColors[i], groups.get(propertyColors[i]));
            this.properties[i] = property;
            this.spaces[propertyIndices[i]] = new PropertySpace(property);
        }

        for (int i = 0; i < railroadIndices.length; i++) {
            property = new Railroad(railroadNames[i]);
            this.properties[i + propertyIndices.length] = property;
            this.spaces[railroadIndices[i]] = new PropertySpace(property);
        }

        for (int i = 0; i < utilityIndices.length; i++) {
            property = new Utility(utilityNames[i], this.diceRoller);
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
}
