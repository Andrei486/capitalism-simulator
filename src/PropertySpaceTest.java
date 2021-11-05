import org.junit.Test;

import static org.junit.Assert.*;

public class PropertySpaceTest {
    private Property property;
    private PropertySpace propertySpace;

    @Test
    public void testGetProperty() {
        property = new Property("Test", 400, ColorGroup.BLUE);
        propertySpace = new PropertySpace(property);
        assertEquals(property, propertySpace.getProperty());
    }

    @Test
    public void testGetName() {
        property = new Property("Test", 400, ColorGroup.BLUE);
        propertySpace = new PropertySpace(property);
        assertEquals("Test", propertySpace.getName());
    }

    @Test
    public void testOnEndTurn() {
        property = new Property("Test", 400, ColorGroup.BLUE);
        propertySpace = new PropertySpace(property);
        Player p1 = new Player("p1", new Board(4));
        p1.buy(property);
        Player p2 = new Player("p2", new Board(4));
        propertySpace.onEndTurn(p1);
        assertEquals(1100, p1.getMoney());
        assertEquals(1500, p2.getMoney());
        propertySpace.onEndTurn(p2);
        assertEquals(700, p2.getMoney());
        assertEquals(1900, p1.getMoney());
    }

    @Test
    public void testGetDescriptionNotOwned() {
        property = new Property("Test", 400, ColorGroup.BLUE);
        propertySpace = new PropertySpace(property);
        StringBuilder sb = new StringBuilder();

        sb.append("Property: Test\n");
        sb.append("Color:    Blue\n");
        sb.append("Cost:     $400\n");
        sb.append("Rent:     $800\n");
        sb.append("Not owned");

        assertEquals(sb.toString(), propertySpace.getDescription());
    }

    @Test
    public void testGetDescriptionOwned() {
        property = new Property("Test", 400, ColorGroup.BLUE);
        propertySpace = new PropertySpace(property);
        Player player = new Player("p1", new Board(4));
        player.buy(property);
        StringBuilder sb = new StringBuilder();

        sb.append("Property: Test\n");
        sb.append("Color:    Blue\n");
        sb.append("Cost:     $400\n");
        sb.append("Rent:     $800\n");
        sb.append("Owned by p1");

        assertEquals(sb.toString(), propertySpace.getDescription());
    }
}