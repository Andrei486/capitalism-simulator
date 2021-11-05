import org.junit.Test;

import static org.junit.Assert.*;

public class EmptySpaceTest {
    private EmptySpace emptySpace;

    @Test
    public void testGetName() {
        emptySpace = new EmptySpace();
        assertEquals("", emptySpace.getName());
    }

    @Test
    public void testGetDescription() {
        emptySpace = new EmptySpace();
        assertEquals("Empty space", emptySpace.getDescription());
    }

    @Test
    public void testOnEndTurn() {
        emptySpace = new EmptySpace();
        Player player = new Player("player", new Board(4));
        emptySpace.onEndTurn(player);
        assertEquals(1500, player.getMoney());
    }
}