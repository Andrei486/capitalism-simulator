import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

/**
 * Tests the functionality of the EmptySpace Class.
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
        assertEquals("", emptySpace.getName());
    }

    /**
     * Tests the getDescription method.
     */
    @Test
    public void testGetDescription() {
        assertEquals("Empty space", emptySpace.getDescription());
    }

    /**
     * Test that onEndTurn method does nothing.
     */
    @Test
    public void testOnEndTurn() {
        Player player = new Player("player", new Board(4));
        emptySpace.onEndTurn(player);
        assertEquals(1500, player.getMoney());
    }
}