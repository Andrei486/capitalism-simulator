import org.junit.Test;

import static org.junit.Assert.*;

public class TurnOrderTest {
    private Board board;
    private TurnOrder turnOrder;

    @Test
    public void testCurrentPlayer() {
        board = new Board(4);
        turnOrder = new TurnOrder(board.getPlayers());
        assertEquals(board.getPlayers()[0], turnOrder.getCurrentPlayer());
    }

    @Test
    public void testAdvanceTurnOrderNormal() {
        board = new Board(3);
        turnOrder = new TurnOrder(board.getPlayers());

        turnOrder.advanceTurnOrder(false);
        assertEquals(board.getPlayers()[1], turnOrder.getCurrentPlayer());

        turnOrder.advanceTurnOrder(false);
        assertEquals(board.getPlayers()[2], turnOrder.getCurrentPlayer());

        turnOrder.advanceTurnOrder(false);
        assertEquals(board.getPlayers()[0], turnOrder.getCurrentPlayer());
    }

    @Test
    public void testAdvanceTurnOrderDouble() {
        board = new Board(3);
        turnOrder = new TurnOrder(board.getPlayers());

        turnOrder.advanceTurnOrder(true);
        assertEquals(board.getPlayers()[0], turnOrder.getCurrentPlayer());

        turnOrder.advanceTurnOrder(true);
        assertEquals(board.getPlayers()[0], turnOrder.getCurrentPlayer());

        turnOrder.advanceTurnOrder(false);
        assertEquals(board.getPlayers()[1], turnOrder.getCurrentPlayer());
    }

    @Test
    public void testAdvanceTurnOrderBankrupt() {
        board = new Board(3);
        turnOrder = new TurnOrder(board.getPlayers());

        board.getPlayers()[1].loseMoney(1500);
        turnOrder.advanceTurnOrder(false);
        assertEquals(board.getPlayers()[2], turnOrder.getCurrentPlayer());
    }

    @Test
    public void testAdvanceTurnOrderBankruptDouble() {
        board = new Board(3);
        turnOrder = new TurnOrder(board.getPlayers());


        turnOrder.advanceTurnOrder(false);
        assertEquals(board.getPlayers()[1], turnOrder.getCurrentPlayer());
        board.getPlayers()[1].loseMoney(1500);
        turnOrder.advanceTurnOrder(true);
        assertEquals(board.getPlayers()[2], turnOrder.getCurrentPlayer());

    }
}