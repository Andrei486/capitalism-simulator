import org.junit.Test;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;

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
     * Tests the getter for the players currently playing.
     */

    @org.junit.Test
    public void getPlayers() {
        newGame = new Board(7);
        assertEquals(7, newGame.getPlayers().length);

    }

    /**
     * Tests to see if players move based on given factors..
     */

    @Test
    public void movePlayer() {

        newGame = new Board(2);
        newGame.getCurrentPlayer().setPosition(39);
        assertEquals(39, newGame.getCurrentPlayer().getPosition());

        newerGame = new Board(2);
        newerGame.getDiceRoller().forceRoll(1, 2);
        newerGame.getCurrentPlayer().setPosition(39);
        Player P1 = new Player();
        Player P2 = new Player();
        newerGame.getProperties()[21].setOwner(P2);
        newerGame.getCurrentPlayer().loseMoney(800);

        assertEquals(39, newerGame.getCurrentPlayer().getPosition());
        assertEquals(700, newerGame.getCurrentPlayer().getMoney());
    }

    /**
     * Tests if the turn order delegates play order efficiently.
     */

    @org.junit.Test
    public void advanceTurn() {
        newGame = new Board(3);

        TurnOrder turnOrder = new TurnOrder(newGame.getPlayers());
        newGame.advanceTurn();

        assertEquals(1, newGame.getCurrentPlayer().getPlayerNumber());

        newerGame = new Board(5);

        TurnOrder turnOrder2 = new TurnOrder(newerGame.getPlayers());
        newerGame.getDiceRoller().isDouble();
        newerGame.advanceTurn();

        assertEquals(4, newerGame.getCurrentPlayer().getPlayerNumber());


    }
}