package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import main.*;
import static org.junit.Assert.*;

/**
 * Test class for testing all methods in the RealEstate class
 * @author Mohammad Alkhaledi 101162465
 */
public class RealEstateTest {
    RealEstate property;

    /**
     * initializes a RealEstate for testing purposes
     */
    @Before
    public void setUp(){
        property = new RealEstate("house",100, ColorGroup.GREEN);
    }
    /**
     * Tests constructor of RealEstate
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
        Assert.assertEquals(100,property.getCost());
    }

    /**
     *tests the getter of name attribute
     */
    @Test
    public void getName() {
        Assert.assertEquals("house", property.getName());
    }

    /**
     *tests the rent value given by getRent
     */
    @Test
    public void getRent() {
        Assert.assertEquals(200, property.getRent());
    }

    /**
     *tests the getter of ColorGroup attribute
     */
    @Test
    public void getColorGroup() {
        Assert.assertEquals(ColorGroup.GREEN, property.getColorGroup());
    }

    /**
     *tests the getter/setter of owner attribute
     */
    @Test
    public void testgetsetOwner() {
        assertNull(property.getOwner());
        Player jim = new Player("jim", new Board(3));
        property.setOwner(jim);
        Assert.assertEquals(jim, property.getOwner());
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

        Assert.assertEquals(sb, property.toString());
        Player jim = new Player("jim", new Board(3));
        property.setOwner(jim);
        String sb2 = "Property: house\n" +
                "Color:    Green\n" +
                "Cost:     $100\n" +
                "Rent:     $200\n" +
                "Owned by jim";
        Assert.assertEquals(sb2, property.toString());
    }
}