package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import main.*;

/**
 * Tests the functionality of the EmptySpace Class.
 * Assumes Player class functions.
 * @author Sebastian Lionais 101157892
 */
public class GoSpaceTest {
    GoSpace goSpace;

    @Before
    public void setUp() {
        goSpace = new GoSpace("Go Space");
    }

    /**
     * Tests the getName method.
     */
    @Test
    public void testGetName() {
        assertEquals("Go Space", goSpace.getName());
    }

    /**
     * Tests the getDescription method.
     */
    @Test
    public void testGetDescription() { assertEquals("Go Space", goSpace.getDescription()); }

    /**
     * Tests the onPassThrough method gives money to the player.
     */
    @Test
    public void testOnPassThrough() {
        Player player = new Player("p1", new Board(3));
        int money = player.getMoney();
        goSpace.onPassThrough(player);
        assertEquals(money + 100, player.getMoney());
    }

}



