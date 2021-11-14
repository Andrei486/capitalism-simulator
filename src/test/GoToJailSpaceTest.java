package test;

import main.Board;
import main.GoToJailSpace;
import main.JailSpace;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *Tests the GoToJailSpace
 */
public class GoToJailSpaceTest {
    private Board board;
    private JailSpace jailSpace;
    private GoToJailSpace goToJailSpace;
    /**
     *deals with set up
     */
    @Before
    public void setUp() {
        board = new Board(2);
        jailSpace = new JailSpace("Jail", 10);
        goToJailSpace = new GoToJailSpace("Go to Jail",jailSpace);
    }

    /**
     *Tests if player goes to jail
     */
    @Test
    public void onEndTurn() {
        board.getPlayers()[0].setPosition(30);
        board.getPlayers()[1].setPosition(30);
        goToJailSpace.onEndTurn(board.getPlayers()[0]);
        goToJailSpace.onEndTurn(board.getPlayers()[1]);
        assertEquals(10, board.getPlayers()[0].getPosition());
        assertEquals(10, board.getPlayers()[1].getPosition());
        assertEquals(3, board.getPlayers()[0].getJailTimer());
        assertEquals(3, board.getPlayers()[1].getJailTimer());


    }

    /**
     *Test to see if the description of the space is correct
     */
    @Test
    public void getDescription() {
        assertEquals("If you land on this space, you go to jail.",
                board.getSpace(30).getDescription());
    }
}