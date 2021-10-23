import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class representing a Monopoly board. Provides methods that allow
 * player turns to be advanced and players to move along the board.
 */
public class Board implements GameEventListener{
    private Space[] spaces;
    private Player[] players;
    private Property[] properties;
    private Player currentPlayer;
    private Queue<Player> nextTurns;
    private DiceRoller diceRoller;
    private int BOARD_SIZE;
    private int bankruptPlayers;
    private HashSet<GameEventListener> gameListeners;

    /**
     * Constructs a board with the specified number of players.
     * @param nPlayers integer number of players
     */
    public Board(int nPlayers) {
        initializeBoard();
        bankruptPlayers = 0;
        this.players = new Player[nPlayers];
        nextTurns = new LinkedList<>();
        diceRoller = new DiceRoller();
        for (int i = 0; i < nPlayers; i++) {
            Player player = new Player("P" + (i + 1), this);
            this.players[i] = player;
            nextTurns.add(player);
        }
        this.currentPlayer = nextTurns.remove();
        this.gameListeners = new HashSet<>();
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
        return this.currentPlayer;
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
        int newPosition = diceRoller.getTotal() + player.getPosition();
        newPosition = newPosition % BOARD_SIZE;
        player.setPosition(newPosition);
        this.getSpace(newPosition).onEndTurn(player);
    }

    /**
     * Replaces current player's position in queue depending on dice roll.
     * If player rolls a double, player gets another turn.
     */
    public void advanceTurn() {
        if (!getDiceRoller().isDouble()) {
            nextTurns.add(currentPlayer);
            currentPlayer = nextTurns.remove();
            //remove any bankrupt players from the turn order without giving them a turn
            while (currentPlayer.getIsBankrupt()) {
                currentPlayer = nextTurns.remove();
                bankruptPlayers++;
            }
        }
    }

    /**
     * Checks whether the game is over. The game is over when all but one player is bankrupt.
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return bankruptPlayers >= (players.length - 1);
    }

    /**
     * Initializes the spaces on the board, creating properties as needed.
     */
    private void initializeBoard() {
        BOARD_SIZE = 40;
        Property property;
        this.spaces = new Space[40];
        this.properties = new Property[22];
        int[] emptyIndices = {0, 2, 4, 5, 7, 10, 12, 15, 17, 20, 22, 25, 28, 30, 33, 35, 36, 38};
        for (int i : emptyIndices) {
            this.spaces[i] = new EmptySpace();
        }
        int[] propertyIndices = {1, 3, 6, 8, 9, 11, 13, 14, 16, 18, 19,
                21, 23, 24, 26, 27, 29, 31, 32, 34, 37, 39
        };
        int[] propertyCosts = {60, 60, 100, 100, 120, 140, 140, 160, 180, 180, 200,
                220, 220, 240, 260, 260, 280, 300, 300, 320, 350, 400
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
        for (int i = 0; i < propertyIndices.length; i++) {
            property = new Property(propertyNames[i], propertyCosts[i], propertyColors[i]);
            this.properties[i] = property;
            this.spaces[propertyIndices[i]] = new PropertySpace(property);
        }
    }

    /**
     * Adds a GameEventListener to the gameListener Hashset.
     * @param l the GameEventListener to be added
     */
    public void addGameListener(GameEventListener l) {
        gameListeners.add(l);
    }

    /**
     * Remove a GameEventListener from the gameListener Hashset.
     * @param l the GameEventListener to be removed
     */
    public void removeGameListener(GameEventListener l) {
        gameListeners.remove(l);
    }

    /**
     * Sends the RentEvent to all the GameEventListener in the gameListener Hashset.
     * @param e the event to be sent out
     */
    @Override
    public void handlePayRent(RentEvent e) {
        for (GameEventListener l: gameListeners) {
            l.handlePayRent(e);
        }
    }

    /**
     * Sends the BankruptcyEvent to all the GameEventListener in the gameListener Hashset.
     * @param e the event to be sent out
     */
    @Override
    public void handleBankruptcy(BankruptcyEvent e) {
        for (GameEventListener l: gameListeners) {
            l.handleBankruptcy(e);
        }
    }
}
