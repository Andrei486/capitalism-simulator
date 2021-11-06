import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Waleed Majbour 101144882
 * Test class for testing methods in the Board class.
 */

public class BoardTest {

    Board newGame;
    Board newerGame;


    /**
     * Tests constructor for Board.
     */

    @org.junit.Test
    public void Board(){
        assertNull(newGame);
        newGame = new Board(2);
        assertNotNull(newGame);
    }

    /**
     * Tests that board initialized properly.
     */

    @org.junit.Test
    public void testBoard(){
        newGame = new Board(3);
        assertEquals(22, newGame.getProperties().length);
        assertEquals(3, newGame.getPlayers().length);
        assertEquals(1500, newGame.getCurrentPlayer().getMoney());
    }

    /**
     * Tests if the getDiceRoller method is not null.
     */

    @org.junit.Test
    public void getDiceRoller(){
        newGame = new Board(3);
        newGame.getDiceRoller().roll();
        assertNotNull(newGame.getDiceRoller());
    }

    /**
     * Tests the getter for the properties on the board.
     */

    @org.junit.Test
    public void getProperties() {
        newGame = new Board(2);
        assertEquals(22, newGame.getProperties().length);
    }

    /**
     * Tests the getter for the spaces on the board.
     */

    @org.junit.Test
    public void getSpace() {
        newGame = new Board(2);
        assertEquals("Mediterranean Avenue", newGame.getSpace(1).getName());
    }

    /**
     * Tests the getter for player currently playing.
     */

    @org.junit.Test
    public void getCurrentPlayer(){
        newGame = new Board(3);
        assertEquals(0, newGame.getCurrentPlayer().getPlayerNumber());

        newGame.advanceTurn();
        assertEquals(1, newGame.getCurrentPlayer().getPlayerNumber());

        newGame.advanceTurn();
        assertEquals(2, newGame.getCurrentPlayer().getPlayerNumber());
    }

    /**
     * Tests the getter for the players currently playing.
     */

    @org.junit.Test
    public void getPlayers() {
        newGame = new Board(7);
        assertEquals(7, newGame.getPlayers().length);
    }

    /**
     * Tests to see if players move based on given factors.
     * Comment out diceRoller.roll() under movePlayer() method in Board.class for this test
     */

    @Test
    public void movePlayer() {
        newGame = new Board(1);
        newGame.getCurrentPlayer().setPosition(39);
        newGame.getDiceRoller().forceRoll(1,3);
        newGame.movePlayer(newGame.getCurrentPlayer());
        assertEquals(3, newGame.getCurrentPlayer().getPosition());
    }


    /**
     * Tests if the turn order delegates play order efficiently.
     */

    @org.junit.Test
    public void advanceTurn() {
        newGame = new Board(3);
        newGame.advanceTurn();
        assertEquals(1, newGame.getCurrentPlayer().getPlayerNumber());

        newerGame = new Board(2);
        newerGame.getDiceRoller().isDouble();
        newerGame.advanceTurn();

        assertEquals(4, newerGame.getCurrentPlayer().getPlayerNumber());
    }

    /**
     * Tests if the game ends after all players but one go bankrupt.
     */

    @org.junit.Test
    public void isGameOver(){
        newGame = new Board(2);
        newGame.getCurrentPlayer().loseMoney(1500);
        assertEquals(true, newGame.isGameOver());

    }
}