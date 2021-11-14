package test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import main.*;

/**
 * Tests the functionality of the TurnOrder Class.
 * Assumes Board functions.
 * @author Sebastian Lionais 101157892
 */
public class TurnOrderTest {
    private Board board;
    private TurnOrder turnOrder;

    /**
     * Initiates the Board that the TurnOrder needs to be able to initiate,
     * then initiates TurnOrder.
     */
    @Before
    public void setup() {
        board = new Board(3);
        turnOrder = new TurnOrder(board.getPlayers());
    }

    /**
     * Tests the getCurrentPlayer method.
     */
    @Test
    public void testGetCurrentPlayer() {
        Assert.assertEquals(board.getPlayers()[0], turnOrder.getCurrentPlayer());
    }

    /**
     * Tests that advanceTurnOrder method loops through the players properly.
     */
    @Test
    public void testAdvanceTurnOrderNormal() {
        turnOrder.advanceTurnOrder(false);
        Assert.assertEquals(board.getPlayers()[1], turnOrder.getCurrentPlayer());

        turnOrder.advanceTurnOrder(false);
        Assert.assertEquals(board.getPlayers()[2], turnOrder.getCurrentPlayer());

        turnOrder.advanceTurnOrder(false);
        Assert.assertEquals(board.getPlayers()[0], turnOrder.getCurrentPlayer());
    }

    /**
     * Tests that advanceTurnOrder method will not loop when doubles are rolled,
     * but still will return to normal behavior afterwards.
     */
    @Test
    public void testAdvanceTurnOrderDouble() {
        turnOrder.advanceTurnOrder(true);
        Assert.assertEquals(board.getPlayers()[0], turnOrder.getCurrentPlayer());

        turnOrder.advanceTurnOrder(true);
        Assert.assertEquals(board.getPlayers()[0], turnOrder.getCurrentPlayer());

        turnOrder.advanceTurnOrder(false);
        Assert.assertEquals(board.getPlayers()[1], turnOrder.getCurrentPlayer());
    }

    /**
     * Tests that advanceTurnOrder method will remove bankrupt players from the TurnOrder.
     */
    @Test
    public void testAdvanceTurnOrderBankrupt() {
        board.getPlayers()[1].loseMoney(1500);
        turnOrder.advanceTurnOrder(false);
        Assert.assertEquals(board.getPlayers()[2], turnOrder.getCurrentPlayer());
    }

    /**
     * Tests that advanceTurnOrder method will still remove a player from the turn order,
     * even if they roll doubles.
     */
    @Test
    public void testAdvanceTurnOrderBankruptDouble() {
        turnOrder.advanceTurnOrder(false);
        Assert.assertEquals(board.getPlayers()[1], turnOrder.getCurrentPlayer());
        board.getPlayers()[1].loseMoney(1500);
        turnOrder.advanceTurnOrder(true);
        Assert.assertEquals(board.getPlayers()[2], turnOrder.getCurrentPlayer());

    }
}