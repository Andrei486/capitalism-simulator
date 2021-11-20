package test;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import main.*;

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
        Assert.assertEquals(26, newGame.getProperties().length);
        Assert.assertEquals(3, newGame.getPlayers().length);
        Assert.assertEquals(1500, newGame.getCurrentPlayer().getMoney());
        RealEstate realEstate = (RealEstate) (newGame.getProperties()[0]);
        Assert.assertEquals(18, realEstate.getHouseCost());
        Assert.assertEquals(0, realEstate.getHouses());
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
        Assert.assertEquals(26, newGame.getProperties().length);
    }

    /**
     * Tests the getter for the spaces on the board.
     */

    @org.junit.Test
    public void getSpace() {
        newGame = new Board(2);
        Assert.assertEquals("Mediterranean Avenue", newGame.getSpace(1).getName());
    }

    /**
     * Tests the getter for player currently playing.
     */

    @org.junit.Test
    public void getCurrentPlayer(){
        newGame = new Board(3);
        Assert.assertEquals(newGame.getPlayers()[0], newGame.getCurrentPlayer());

        newGame.getDiceRoller().forceRoll(1, 2);
        newGame.advanceTurn();
        Assert.assertEquals(newGame.getPlayers()[1], newGame.getCurrentPlayer());

        newGame.getDiceRoller().forceRoll(1, 2);
        newGame.advanceTurn();
        Assert.assertEquals(newGame.getPlayers()[2], newGame.getCurrentPlayer());

        newGame.getDiceRoller().forceRoll(1, 2);
        newGame.advanceTurn();
        Assert.assertEquals(newGame.getPlayers()[0], newGame.getCurrentPlayer());
    }

    /**
     * Tests the getter for the players currently playing.
     */

    @org.junit.Test
    public void getPlayers() {
        newGame = new Board(7);
        Assert.assertEquals(7, newGame.getPlayers().length);
    }

    /**
     * Tests to see if players move based on given factors.
     */

    @Test
    public void movePlayer() {
        newGame = new Board(1);
        newGame.getCurrentPlayer().setPosition(39);
        newGame.movePlayer(newGame.getCurrentPlayer());
        int totalRoll = newGame.getDiceRoller().getTotal();
        Assert.assertEquals(totalRoll - 1, newGame.getCurrentPlayer().getPosition());
    }


    /**
     * Tests if the turn order delegates play order efficiently.
     */

    @org.junit.Test
    public void advanceTurn() {
        newGame = new Board(2);
        newGame.getDiceRoller().forceRoll(1,2);
        newGame.advanceTurn();
        Assert.assertEquals(newGame.getPlayers()[1], newGame.getCurrentPlayer());

        newGame.getDiceRoller().forceRoll(1,1);
        newGame.advanceTurn();
        Assert.assertEquals(newGame.getPlayers()[1], newGame.getCurrentPlayer());
    }

    /**
     * Tests if the game ends after all players but one go bankrupt.
     */

    @org.junit.Test
    public void isGameOver(){
        newGame = new Board(3);
        newGame.getCurrentPlayer().loseMoney(1500);
        assertFalse(newGame.isGameOver());
        newGame.advanceTurn();
        newGame.getCurrentPlayer().loseMoney(1500);
        assertTrue(newGame.isGameOver());


    }
}