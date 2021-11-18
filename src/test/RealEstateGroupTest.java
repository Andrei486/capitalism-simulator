package test;

import main.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for RealEstateGroup.
 * Assumes that RealEstate and Player are correctly implemented.
 * @author Andrei Popescu, 101143798
 */
public class RealEstateGroupTest {

    RealEstate property;
    RealEstateGroup group;
    RealEstate property2;
    Player p;
    Player p2;

    /**
     * Create objects used in testing.
     */
    @Before
    public void setup() {
        group = new RealEstateGroup(ColorGroup.GREEN);
        p = new Player("P1", new Board(1));
        p2 = new Player("P2", new Board(1));
        property = new RealEstate("property1",100,
                ColorGroup.GREEN, group);
        property2 = new RealEstate("property2",200,
                ColorGroup.GREEN, group);
    }

    /**
     * Tests whether RealEstates are properly added via their constructor and manually.
     * Also tests whether getRealEstates correctly retrieves RealEstates.
     */
    @Test
    public void addRealEstate() {
        //these were added in the constructor for RealEstate
        assertTrue(group.getRealEstates().contains(property));
        assertTrue(group.getRealEstates().contains(property2));
        //add a RealEstate manually
        RealEstate r = new RealEstate("test", 40, ColorGroup.GREEN, new RealEstateGroup(ColorGroup.BLUE));
        group.addRealEstate(r);
        assertTrue(group.getRealEstates().contains(r));
    }

    /**
     * Tests whether canAddHouse returns the correct value in different scenarios.
     */
    @Test
    public void canAddHouse() {
        property.setOwner(p);
        Assert.assertFalse(group.canAddHouse(property)); //not all properties are owned
        Assert.assertFalse(group.canAddHouse(property2)); //not owned
        property2.setOwner(p2);
        Assert.assertFalse(group.canAddHouse(property)); //not all properties are owned by the same player
        Assert.assertFalse(group.canAddHouse(property2)); //not all properties are owned by the same player
        property2.setOwner(p);
        Assert.assertTrue(group.canAddHouse(property)); //0 houses on all properties
        Assert.assertTrue(group.canAddHouse(property2)); //0 houses on all properties
        property.addHouse();
        Assert.assertFalse(group.canAddHouse(property)); //can't add more houses yet, need to balance houses
        Assert.assertTrue(group.canAddHouse(property2)); //can add house
        p.loseMoney(p.getMoney() - 1);
        Assert.assertFalse(group.canAddHouse(property)); //can't afford
        Assert.assertFalse(group.canAddHouse(property2)); //can't afford
        p.gainMoney(1500);
        for (int i = 0; i < 4; i++) {
            property.addHouse();
        }
        for (int i = 0; i < 4; i++) {
            property2.addHouse();
        }
        Assert.assertFalse(group.canAddHouse(property)); //max houses already
        Assert.assertTrue(group.canAddHouse(property2)); //one more house left
    }
}