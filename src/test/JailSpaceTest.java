package test;

import main.Board;
import main.GoToJailSpace;
import main.JailSpace;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the JailSpace class
 */
public class JailSpaceTest {
    private Board board;
    private JailSpace jailSpace;
    private GoToJailSpace goToJailSpace;
    /**
     * Sets up any needed variables once.
     */
    @Before
    public void setUp() {
        board = new Board(2);
        jailSpace = new JailSpace("Jail", 10);
        goToJailSpace = new GoToJailSpace("Go to Jail",jailSpace);
    }

    /**
     * Tests the onEndTurn method
     */
    @Test
    public void onEndTurn() {
        board.getPlayers()[0].setPosition(30);
        board.getPlayers()[1].setPosition(30);
        goToJailSpace.onEndTurn(board.getPlayers()[0]);
        goToJailSpace.onEndTurn(board.getPlayers()[1]);
        jailSpace.onEndTurn(board.getPlayers()[0]);
        assertEquals(2, board.getPlayers()[0].getJailTimer());
        jailSpace.onEndTurn(board.getPlayers()[0]);
        jailSpace.onEndTurn(board.getPlayers()[0]);
        assertEquals(0, board.getPlayers()[0].getJailTimer());
        jailSpace.onEndTurn(board.getPlayers()[0]);
        assertEquals(1450, board.getPlayers()[0].getMoney());
    }


    /**
     * Tests the getDescription method
     */
    @Test
    public void getDescription() {
        assertEquals("Jail is where criminals are sent.",
                board.getSpace(10).getDescription());
    }
}