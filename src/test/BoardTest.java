package test;

import static org.junit.Assert.*;

import org.junit.Test;
import main.*;

/**
 * @author Waleed Majbour 101144882
 * Test class for testing methods in the Board class.
 */

public class BoardTest {

    Board newGame;


    /**
     * Tests constructor for Board.
     */
    @Test
    public void Board(){
        assertNull(newGame);
        newGame = new Board(2);
        assertNotNull(newGame);
    }

    /**
     * Tests that board initialized properly.
     */
    @Test
    public void testBoard(){
        newGame = new Board(3);
        assertEquals(28, newGame.getProperties().length);
        assertEquals(3, newGame.getPlayers().length);
        assertEquals(1500, newGame.getCurrentPlayer().getMoney());
        RealEstate realEstate = (RealEstate) (newGame.getProperties()[0]);
        assertEquals(18, realEstate.getHouseCost());
        assertEquals(0, realEstate.getHouses());
    }

    /**
     * Tests if the getDiceRoller method is not null.
     */
    @Test
    public void getDiceRoller(){
        newGame = new Board(3);
        newGame.getDiceRoller().roll();
        assertNotNull(newGame.getDiceRoller());
    }

    /**
     * Tests the getter for the properties on the board.
     */
    @Test
    public void getProperties() {
        newGame = new Board(2);
        assertEquals(28, newGame.getProperties().length);
    }

    /**
     * Tests the getter for the spaces on the board.
     */
    @Test
    public void getSpace() {
        newGame = new Board(2);
        assertEquals("Mediterranean Avenue", newGame.getSpace(1).getName());
    }

    /**
     * Tests the getter for player currently playing.
     */
    @Test
    public void getCurrentPlayer(){
        newGame = new Board(3);
        assertEquals(newGame.getPlayers()[0], newGame.getCurrentPlayer());

        newGame.getDiceRoller().forceRoll(1, 2);
        newGame.advanceTurn();
        assertEquals(newGame.getPlayers()[1], newGame.getCurrentPlayer());

        newGame.getDiceRoller().forceRoll(1, 2);
        newGame.advanceTurn();
        assertEquals(newGame.getPlayers()[2], newGame.getCurrentPlayer());

        newGame.getDiceRoller().forceRoll(1, 2);
        newGame.advanceTurn();
        assertEquals(newGame.getPlayers()[0], newGame.getCurrentPlayer());
    }

    /**
     * Tests the getter for the players currently playing.
     */
    @Test
    public void getPlayers() {
        newGame = new Board(7);
        assertEquals(7, newGame.getPlayers().length);
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
        assertEquals(totalRoll - 1, newGame.getCurrentPlayer().getPosition());
    }

    /**
     * Tests that a player will be put in jail for rolling 3 doubles
     */
    @Test
    public void movePlayerLogicConsecutiveDoubles() {
        newGame = new Board(2);
        newGame.getDiceRoller().forceRoll(1,1);


        newGame.movePlayerLogic(newGame.getCurrentPlayer());
        assertEquals(0, newGame.getCurrentPlayer().getJailTimer());

        newGame.movePlayerLogic(newGame.getCurrentPlayer());
        assertEquals(0, newGame.getCurrentPlayer().getJailTimer());

        newGame.movePlayerLogic(newGame.getCurrentPlayer());
        assertEquals(3, newGame.getCurrentPlayer().getJailTimer());
    }

    /**
     * Tests that a player will stay in jail if they do not roll doubles
     */
    @Test
    public void movePlayerLogicJailedPlayerNoDoubles() {
        newGame = new Board(1);
        newGame.getCurrentPlayer().setPosition(10); //puts player in jail space
        newGame.getCurrentPlayer().setJailTimer(3);
        newGame.getDiceRoller().forceRoll(1,2);

        newGame.movePlayerLogic(newGame.getCurrentPlayer());
        assertEquals(10, newGame.getCurrentPlayer().getPosition());

        newGame.movePlayerLogic(newGame.getCurrentPlayer());
        assertEquals(10, newGame.getCurrentPlayer().getPosition());
    }

    /**
     * Tests that a player will leave jail after they roll doubles
     */
    @Test
    public void movePlayerLogicJailedPlayerDoubles() {
        newGame = new Board(1);
        newGame.getCurrentPlayer().setPosition(10); //puts player in jail space
        newGame.getCurrentPlayer().setJailTimer(3);
        newGame.getDiceRoller().forceRoll(1,1);

        newGame.movePlayerLogic(newGame.getCurrentPlayer());
        assertEquals(10, newGame.getCurrentPlayer().getPosition());

        newGame.movePlayerLogic(newGame.getCurrentPlayer());
        assertEquals(12, newGame.getCurrentPlayer().getPosition());
    }


    /**
     * Tests if the turn order delegates play order efficiently.
     */
    @Test
    public void advanceTurn() {
        newGame = new Board(2);
        newGame.getDiceRoller().forceRoll(1,2);
        newGame.advanceTurn();
       assertEquals(newGame.getPlayers()[1], newGame.getCurrentPlayer());

        newGame.getDiceRoller().forceRoll(1,1);
        newGame.advanceTurn();
        assertEquals(newGame.getPlayers()[1], newGame.getCurrentPlayer());
    }

    /**
     * Tests if the game ends after all players but one go bankrupt.
     */
    @Test
    public void isGameOver(){
        newGame = new Board(3);
        newGame.getCurrentPlayer().loseMoney(1500);
        assertFalse(newGame.isGameOver());
        newGame.advanceTurn();
        newGame.getCurrentPlayer().loseMoney(1500);
        assertTrue(newGame.isGameOver());
    }

    @Test
    public void isAllRemainingAI() {
        newGame = new Board(3, 3);
        assertTrue(newGame.isAllRemainingAI());
    }
}