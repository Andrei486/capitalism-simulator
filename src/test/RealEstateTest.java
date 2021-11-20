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
    RealEstateGroup group;
    RealEstate property2;
    Player p;
    Player p2;

    /**
     * initializes a RealEstate for testing purposes
     */
    @Before
    public void setUp(){
        group = new RealEstateGroup(ColorGroup.GREEN);
        p = new Player("P1", new Board(1));
        p2 = new Player("P2", new Board(1));
        property = new RealEstate("property1",100,
                ColorGroup.GREEN, group);
        property2 = new RealEstate("property2",200,
                ColorGroup.GREEN, group);
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
        Assert.assertEquals("property1", property.getName());
    }

    /**
     *tests the rent value given by getRent
     */
    @Test
    public void getRent() {
        Assert.assertEquals(25, property.getRent());
        property.addHouse();
        Assert.assertEquals(50, property.getRent());
        property.addHouse();
        Assert.assertEquals(75, property.getRent());
    }

    /**
     * Tests that the number of houses is correctly updated and retrieved.
     */
    @Test
    public void testGetAddHouse() {
        Assert.assertEquals(0, property.getHouses());
        property.addHouse();
        Assert.assertEquals(1, property.getHouses());
        property.addHouse();
        Assert.assertEquals(2, property.getHouses());
    }

    /**
     * Tests that house cost is correctly retrieved.
     */
    @Test
    public void getHouseCost() {
        Assert.assertEquals(30, property.getHouseCost());
        property.addHouse();
        Assert.assertEquals(30, property.getHouseCost());
    }

    /**
     * Tests whether canAddHouse returns the correct value in different scenarios.
     */
    @Test
    public void canAddHouse() {
        property.setOwner(p);
        Assert.assertFalse(property.canAddHouse()); //not all properties are owned
        Assert.assertFalse(property2.canAddHouse()); //not owned
        property2.setOwner(p2);
        Assert.assertFalse(property.canAddHouse()); //not all properties are owned by the same player
        Assert.assertFalse(property2.canAddHouse()); //not all properties are owned by the same player
        property2.setOwner(p);
        Assert.assertTrue(property.canAddHouse()); //0 houses on all properties
        Assert.assertTrue(property2.canAddHouse()); //0 houses on all properties
        property.addHouse();
        Assert.assertFalse(property.canAddHouse()); //can't add more houses yet, need to balance houses
        Assert.assertTrue(property2.canAddHouse()); //can add house
        p.loseMoney(p.getMoney() - 1);
        Assert.assertFalse(property.canAddHouse()); //can't afford
        Assert.assertFalse(property2.canAddHouse()); //can't afford
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
        property.setOwner(p);
        Assert.assertEquals(p, property.getOwner());
    }

    /**
     *tests the toString() method call
     */
    @Test
    public void testToString() {
        String sb = "Property: property1\n" +
                "Color: Green\n" +
                "Cost: $100\n" +
                "House cost: $30\n" +
                "Rent: $25\n" +
                "0 houses built\n" +
                "Not owned";

        Assert.assertEquals(sb, property.toString());
        property.setOwner(p);
        for (int i = 0; i < 5; i++) {
            property.addHouse();
        }
        String sb2 = "Property: property1\n" +
                "Color: Green\n" +
                "Cost: $100\n" +
                "House cost: $30\n" +
                "Rent: $150\n" +
                "Hotel built\n" +
                "Owned by P1";
        Assert.assertEquals(sb2, property.toString());
    }
}