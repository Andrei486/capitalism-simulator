package test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import main.*;

/**
 * Tests the functionality of the EmptySpace Class.
 * Assumes Player class functions.
 * @author Sebastian Lionais 101157892
 */
public class EmptySpaceTest {
    private EmptySpace emptySpace;

    /**
     * Initiates the EmptySpace class for testing.
     */
    @Before
    public void setup() {
        emptySpace = new EmptySpace();
    }

    /**
     * Tests the getName method.
     */
    @Test
    public void testGetName() {
        Assert.assertEquals("", emptySpace.getName());
    }

    /**
     * Tests the getDescription method.
     */
    @Test
    public void testGetDescription() {
        Assert.assertEquals("Empty space", emptySpace.getDescription());
    }

    /**
     * Test that onEndTurn method does nothing.
     */
    @Test
    public void testOnEndTurn() {
        Player player = new Player("player", new Board(4));
        int previousMoney = player.getMoney();
        emptySpace.onEndTurn(player);
        Assert.assertEquals(previousMoney, player.getMoney());
    }
}