import static org.junit.Assert.*;

/**
 * @author Mohammad Alkhaledi 101162465
 * Test class for testing all methods in the Property class
 */
public class PropertyTest {
    Property property;

    /**
     * tests constructor of property
     */
    @org.junit.Test
    public void testConstructor() {
        assertNull(property);
        property = new Property("house", 100, ColorGroup.GREEN);
        assertNotNull(property);
    }

    /**
     *tests the getter of cost attribute
     */
    @org.junit.Test
    public void getCost() {
        property = new Property("house",100,ColorGroup.GREEN);
        assertEquals(100,property.getCost());
    }

    /**
     *tests the getter of name attribute
     */
    @org.junit.Test
    public void getName() {
        property = new Property("house",100,ColorGroup.GREEN);
        assertEquals("house", property.getName());
    }

    /**
     *tests the rent value given by getrent
     */
    @org.junit.Test
    public void getRent() {
        property = new Property("house",100,ColorGroup.GREEN);
        assertEquals(200, property.getRent());
    }

    /**
     *tests the getter of ColourGroup attribute
     */
    @org.junit.Test
    public void getColorGroup() {
        property = new Property("house",100,ColorGroup.GREEN);
        assertEquals(ColorGroup.GREEN, property.getColorGroup());
    }

    /**
     *tests the getter/setter of owner attribute
     */
    @org.junit.Test
    public void testgetsetOwner() {
        property = new Property("house",100,ColorGroup.GREEN);
        assertNull(property.getOwner());
        Player jim = new Player("jim", new Board(3));
        property.setOwner(jim);
        assertEquals(jim, property.getOwner());
    }

    /**
     *tests the toString() method call
     */
    @org.junit.Test
    public void testToString() {
        property = new Property("house",100,ColorGroup.GREEN);
        String sb = "Property: house\n" +
                "Color:    Green\n" +
                "Cost:     $100\n" +
                "Rent:     $200\n" +
                "Not owned";
        assertEquals(sb, property.toString());
        Player jim = new Player("jim", new Board(3));
        property.setOwner(jim);
        String sb2 = "Property: house\n" +
                "Color:    Green\n" +
                "Cost:     $100\n" +
                "Rent:     $200\n" +
                "Owned by jim";
        assertEquals(sb2, property.toString());
    }
}