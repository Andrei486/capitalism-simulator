import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

/**
 * Tests the functionality of the PropertySpace Class.
 * Assumes Board, Player, and Property classes function.
 * @author Sebastian Lionais 101157892
 */
public class PropertySpaceTest {
    private Property property;
    private PropertySpace propertySpace;
    private Board board;
    private Player p1;

    /**
     * Initiates the Property that the PropertySpace needs to be able to initiate,
     * then initiates PropertySpace.
     */
    @Before
    public void setup() {
        property = new Property("Test", 400, ColorGroup.BLUE);
        propertySpace = new PropertySpace(property);
        board = new Board(4);
        p1 = new Player("p1", board);
    }

    /**
     * Tests the getProperty method.
     */
    @Test
    public void testGetProperty() {
        assertEquals(property, propertySpace.getProperty());
    }

    /**
     * Tests the getName method.
     */
    @Test
    public void testGetName() {
        assertEquals("Test", propertySpace.getName());
    }

    /**
     * Tests that onEndTurn method will not make the current player pay rent if the property
     * is unowned.
     */
    @Test
    public void testOnEndTurnNotOwned() {
        int previousMoney = p1.getMoney();
        propertySpace.onEndTurn(p1);
        assertEquals(previousMoney, p1.getMoney());
    }

    /**
     * Tests that onEndTurn method will not make the current player pay rent if the current
     * player owns the property.
     */
    @Test
    public void testOnEndTurnOwnedByCurrentPlayer() {
        int previousMoney = p1.getMoney();
        property.setOwner(p1);
        propertySpace.onEndTurn(p1);
        assertEquals(previousMoney, p1.getMoney());
    }

    /**
     * Tests that onEndTurn method will make the current player pay rent if the property is
     * owned by another player.
     */
    @Test
    public void testOnEndTurnNotOwnedByCurrentPlayer() {
        Player p2 = new Player("p2", board);
        int previousMoney = p1.getMoney();
        int previousMoney2 = p2.getMoney();
        property.setOwner(p1);
        propertySpace.onEndTurn(p2);
        assertEquals(previousMoney + property.getRent(), p1.getMoney());
        assertEquals(previousMoney2 - property.getRent(), p2.getMoney());
    }

    /**
     * Test the getDescription method when the property is unowned.
     */
    @Test
    public void testGetDescriptionNotOwned() {
        StringBuilder sb = new StringBuilder();

        sb.append("Property: Test\n");
        sb.append("Color:    Blue\n");
        sb.append("Cost:     $400\n");
        sb.append("Rent:     $800\n");
        sb.append("Not owned");

        assertEquals(sb.toString(), propertySpace.getDescription());
    }

    /**
     * Test the getDescription method when the property is owned.
     */
    @Test
    public void testGetDescriptionOwned() {
        property.setOwner(p1);
        StringBuilder sb = new StringBuilder();

        sb.append("Property: Test\n");
        sb.append("Color:    Blue\n");
        sb.append("Cost:     $400\n");
        sb.append("Rent:     $800\n");
        sb.append("Owned by p1");

        assertEquals(sb.toString(), propertySpace.getDescription());
    }
}