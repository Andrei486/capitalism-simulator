package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import main.*;

/**
 * Tests the functionality of the Railroad Class.
 * Assumes Player, Board, and RealEstate class functions.
 * @author Sebastian Lionais 101157892
 */
public class RailroadTest {
    Railroad railroad;

    @Before
    public void setUp() {
        railroad = new Railroad("Line1");
    }

    /**
     * Tests constructor of Railroad
     */
    @Test
    public void testConstructor() {
        assertNotNull(railroad);
    }

    /**
     * Tests the getter of cost attribute
     */
    @Test
    public void testGetCost() {
        assertEquals(200,railroad.getCost());
    }

    /**
     * Tests the getter of name attribute
     */
    @Test
    public void testGetName() {
        assertEquals("Line1", railroad.getName());
    }

    /**
     * Tests the rent value given by getRent() when a player owns 1 Railroad
     */
    @Test
    public void testGetRentOneOwned() {
        Player player = new Player("Bill", new Board(3));
        player.gainMoney(1000);
        player.buy(railroad);
        assertEquals(25, railroad.getRent());
    }

    /**
     * Tests the rent value given by getRent() when a player owns 2 Railroad
     */
    @Test
    public void testGetRentTwoOwned() {
        Player player = new Player("Bill", new Board(3));
        player.gainMoney(1000);
        player.buy(railroad);
        player.buy(new Railroad("Line2"));
        assertEquals(50, railroad.getRent());
    }

    /**
     * Tests the rent value given by getRent() when a player owns 3 Railroad
     */
    @Test
    public void testGetRentThreeOwned() {
        Player player = new Player("Bill", new Board(3));
        player.gainMoney(1000);
        player.buy(railroad);
        player.buy(new Railroad("Line2"));
        player.buy(new Railroad("Line3"));
        assertEquals(100, railroad.getRent());
    }

    /**
     * Tests the rent value given by getRent() when a player owns 4 Railroad
     */
    @Test
    public void testGetRentFourOwned() {
        Player player = new Player("Bill", new Board(3));
        player.gainMoney(1000);
        player.buy(railroad);
        player.buy(new Railroad("Line2"));
        player.buy(new Railroad("Line3"));
        player.buy(new Railroad("Line4"));
        assertEquals(200, railroad.getRent());
    }

    /**
     * Tests the rent value given by getRent() is unaffected by the ownership of none Railroads
     */
    @Test
    public void testGetRentOtherPropertyOwned() {
        Player player = new Player("Bill", new Board(3));
        player.gainMoney(1000);
        player.buy(railroad);
        player.buy(new RealEstate("place",300, ColorGroup.BLUE, new RealEstateGroup(ColorGroup.BLUE)));
        assertEquals(25, railroad.getRent());
    }

    /**
     * Tests the toString() method call when the Railroad is unowned
     */
    @Test
    public void testToStringNotOwned() {
        String sb = "Property: Line1\n" +
                "Cost: $200\n" +
                "Rent(1): $25\n" +
                "Rent(2): $50\n" +
                "Rent(3): $100\n" +
                "Rent(4): $200\n" +
                "Not owned";

        assertEquals(sb, railroad.toString());
    }

    /**
     * Tests the toString() method call when the Railroad is owned
     */
    @Test
    public void testToStringOwned() {
        String sb = "Property: Line1\n" +
                "Cost: $200\n" +
                "Rent: $25\n" +
                "Owned by Bill";

        Player player = new Player("Bill", new Board(3));
        player.gainMoney(1000);
        player.buy(railroad);

        assertEquals(sb, railroad.toString());
    }
}
