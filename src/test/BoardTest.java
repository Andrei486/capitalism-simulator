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

    /**
     * Tests if the board can check if all the remaining players are AI.
     */
    @Test
    public void isAllRemainingAI() {
        newGame = new Board(3, 3, InternationalVersion.NORTH_AMERICA);
        assertTrue(newGame.isAllRemainingAI());
    }

    /**
     * Tests the France board loads.
     */
    @Test
    public void doesFranceBoardLoad() {
        newGame = new Board(3, 1, InternationalVersion.FRANCE);
        assertEquals("Boul. de Belleville", newGame.getSpace(1).getName());
        assertEquals("Av. des Champs-\u00C9lys\u00E9es", newGame.getSpace(37).getName());
        assertEquals(400,((PropertySpace) newGame.getSpace(37)).getProperty().getCost());
        String sb = "Property: Av. des Champs-\u00C9lys\u00E9es\n" +
                "Color: Blue\n" +
                "Cost: \u20AC400\n" +
                "House cost: \u20AC120\n" +
                "Rent: \u20AC300\n" +
                "0 houses built\n" +
                "Not owned";
        assertEquals(sb, newGame.getSpace(37).getDescription());
        sb = "Property: Gare Montparnasse\n" +
                "Cost: \u20AC200\n" +
                "Rent(1): \u20AC25\n" +
                "Rent(2): \u20AC50\n" +
                "Rent(3): \u20AC100\n" +
                "Rent(4): \u20AC200\n" +
                "Not owned";
        assertEquals(sb, newGame.getSpace(5).getDescription());
        sb = "Property: Compagnie d'\u00E9lectricit\u00E9\n" +
                "Cost: \u20AC150\n" +
                "If one utility is owned, rent is 4 times what is shown on dice.\n" +
                "If both utilities are owned, rent is 10 times what is shown on dice.";
        assertEquals(sb, newGame.getSpace(12).getDescription());
    }

    /**
     * Tests the UK board loads.
     */
    @Test
    public void doesUKBoardLoad() {
        newGame = new Board(3, 1, InternationalVersion.UNITED_KINGDOM);
        assertEquals("Old Kent Road", newGame.getSpace(1).getName());
        assertEquals("Park Lane", newGame.getSpace(37).getName());
        assertEquals(350,((PropertySpace) newGame.getSpace(37)).getProperty().getCost());
        String sb = "Property: Park Lane\n" +
                "Color: Blue\n" +
                "Cost: \u00A3350\n" +
                "House cost: \u00A3105\n" +
                "Rent: \u00A3262\n" +
                "0 houses built\n" +
                "Not owned";
        assertEquals(sb, newGame.getSpace(37).getDescription());
        sb = "Property: King's Cross Station\n" +
                "Cost: \u00A3200\n" +
                "Rent(1): \u00A325\n" +
                "Rent(2): \u00A350\n" +
                "Rent(3): \u00A3100\n" +
                "Rent(4): \u00A3200\n" +
                "Not owned";
        assertEquals(sb, newGame.getSpace(5).getDescription());
        sb = "Property: Electric Company\n" +
                "Cost: \u00A3150\n" +
                "If one utility is owned, rent is 4 times what is shown on dice.\n" +
                "If both utilities are owned, rent is 10 times what is shown on dice.";
        assertEquals(sb, newGame.getSpace(12).getDescription());
    }

    /**
     * Tests the NA board loads.
     */
    @Test
    public void doesNABoardLoad() {
        newGame = new Board(3, 1, InternationalVersion.NORTH_AMERICA);
        assertEquals("Mediterranean Avenue", newGame.getSpace(1).getName());
        assertEquals("Park Place", newGame.getSpace(37).getName());
        assertEquals(350,((PropertySpace) newGame.getSpace(37)).getProperty().getCost());
        String sb = "Property: Park Place\n" +
                "Color: Blue\n" +
                "Cost: \u0024350\n" +
                "House cost: \u0024105\n" +
                "Rent: \u0024262\n" +
                "0 houses built\n" +
                "Not owned";
        assertEquals(sb, newGame.getSpace(37).getDescription());
        sb = "Property: Reading Railroad\n" +
                "Cost: \u0024200\n" +
                "Rent(1): \u002425\n" +
                "Rent(2): \u002450\n" +
                "Rent(3): \u0024100\n" +
                "Rent(4): \u0024200\n" +
                "Not owned";
        assertEquals(sb, newGame.getSpace(5).getDescription());
        sb = "Property: Electric Company\n" +
                "Cost: \u0024150\n" +
                "If one utility is owned, rent is 4 times what is shown on dice.\n" +
                "If both utilities are owned, rent is 10 times what is shown on dice.";
        assertEquals(sb, newGame.getSpace(12).getDescription());
    }
}