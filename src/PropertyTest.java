import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for testing all methods in the Property class
 * @author Mohammad Alkhaledi 101162465
 */
public class PropertyTest {
    Property property;

    /**
     * initializes a Property for testing purposes
     */
    @Before
    public void setUp(){
        property = new Property("house",100,ColorGroup.GREEN);
    }
    /**
     * tests constructor of property
     */
    @Test
    public void testConstructor() {
        assertNotNull(property);
    }

    /**
     *tests the getter of cost attribute
     */
    @Test
    public void getCost() {
        assertEquals(100,property.getCost());
    }

    /**
     *tests the getter of name attribute
     */
    @Test
    public void getName() {
        assertEquals("house", property.getName());
    }

    /**
     *tests the rent value given by getrent
     */
    @Test
    public void getRent() {
        assertEquals(200, property.getRent());
    }

    /**
     *tests the getter of ColourGroup attribute
     */
    @Test
    public void getColorGroup() {
        assertEquals(ColorGroup.GREEN, property.getColorGroup());
    }

    /**
     *tests the getter/setter of owner attribute
     */
    @Test
    public void testgetsetOwner() {
        assertNull(property.getOwner());
        Player jim = new Player("jim", new Board(3));
        property.setOwner(jim);
        assertEquals(jim, property.getOwner());
    }

    /**
     *tests the toString() method call
     */
    @Test
    public void testToString() {
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