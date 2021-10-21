import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class representing a Monopoly board. Provides methods that allow
 * player turns to be advanced and players to move along the board.
 */
public class Board {
    private Space[] spaces;
    private Player[] players;
    private Player currentPlayer;
    private Queue<Player> nextTurns;
    private DiceRoller diceRoller;

    /**
     * Constructs a board with the specified number of players.
     *
     * @param nPlayers integer number of players
     */
    public Board(int nPlayers) {
        initializeBoard();
        this.players = new Player[nPlayers];
        nextTurns = new LinkedList<>();
        diceRoller = new DiceRoller();
        for (int i = 0; i < nPlayers; i++) {
            Player player = new Player("P" + (i + 1));
            this.players[i] = player;
            nextTurns.add(player);
        }
        this.currentPlayer = nextTurns.peek();
    }

    /**
     * Gets all players that are playing on this board.
     *
     * @return array of all the Players
     */
    public Player[] getPlayers() {
        return this.players;
    }

    /**
     * Gets the player who is currently moving. This player is the one who
     * should act (buy, etc) and can end their turn.
     *
     * @return Player who is currently moving
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Gets the space on this board at a specific position.
     * Positions start at 0 for GO, and increase clockwise up to 39.
     *
     * @param position integer position of the space to get
     * @return the Space at the given position
     */
    public Space getSpace(int position) {
        return this.spaces[position];
    }

    /**
     * Gets the total roll of both dice.
     * Value must be between 1 and 12, inclusive.
     *
     * @return the total value of the given dice roll.
     */
    public DiceRoller getDiceRoller() {
        return this.diceRoller;
    }

    /**
     * Moves the player by the amount rolled on the dice.
     *
     * @param player integer of how many spaces were covered by the player.
     */
    public void movePlayer(int player) {
        int position = 0;
        position += player;
        int BOARD_SIZE = 40;
        if (position >= BOARD_SIZE) {
            position -= BOARD_SIZE;
        }
    }

    /**
     * Replaces current player's position in queue depending on dice roll.
     * If player rolls a double, players plays again.
     */

    public void advanceTurn() {
        if (!getDiceRoller().isDouble()) {
            currentPlayer = nextTurns.remove();
            nextTurns.add(currentPlayer);
        }
        else System.out.println(currentPlayer.getName() + " rolled a double, they play again!");
    }

    /**
     * Initializes the spaces on the board, creating properties as needed.
     */
    private void initializeBoard() {
        Property property;
        this.spaces = new Space[40];
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
            this.spaces[propertyIndices[i]] = new PropertySpace(property);
        }
    }

    public static void main(String[] args) {
        Board newGame = new Board(3);
        for (int i = 0; i < 100; i++) {
            System.out.println(newGame.currentPlayer.getName() + " playing his turn!");
            newGame.diceRoller.roll();
            int roll = newGame.diceRoller.getTotal();
            newGame.movePlayer(roll);
            System.out.println(newGame.currentPlayer.getName() + " rolled a " + roll);
            newGame.advanceTurn();
        }
    }
}
