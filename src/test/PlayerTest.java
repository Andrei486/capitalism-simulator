package test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import main.*;

/**
 * JUnit test class for the Player model class.
 * Assumes that the Board constructor and Property getters work correctly.
 * @author Andrei Popescu, 101143798
 */
public class PlayerTest {

    public Player p1;
    public Player p2;
    public Property property1;
    public Property property2;
    public RealEstate realEstate1;
    public RealEstate realEstate2;
    public Board board;

    /**
     * Creates objects for use in tests.
     */
    @Before
    public void setup() {
        board = new Board(1); //need a board to create players, but the board will not be tested here
        //a non-null board is needed to send events
        //create test objects
        p1 = new Player("P1", board); //p1 will own properties
        p2 = new Player("P2", board); //p2 will never own properties
        property1 = new RealEstate("Property 1", 300,
                ColorGroup.BLUE, new RealEstateGroup(ColorGroup.BLUE));
        property2 = new RealEstate("Property 2", 1000,
                ColorGroup.BROWN, new RealEstateGroup(ColorGroup.BROWN));
        realEstate1 = (RealEstate) property1;
        realEstate2 = (RealEstate) property2;
    }

    /**
     * Tests that all model information is updated when a player buys a property.
     */
    @Test
    public void buy() {
        int previousMoney = p1.getMoney();
        p1.buy(property1);
        assertTrue(p1.getProperties().contains(property1));
        assertEquals(previousMoney - 300, p1.getMoney());
        assertEquals(property1.getOwner(), p1);
        //event send is not tested here
    }

    /**
     * Tests that model information is updated when a player buys a house.
     */
    @Test
    public void buyHouse() {
        int previousMoney = p1.getMoney();
        p1.buyHouse(realEstate1);
        assertEquals(1, realEstate1.getHouses());
        assertEquals(previousMoney - realEstate1.getHouseCost(), p1.getMoney());
    }

    /**
     * Tests that rent payments are correctly transferred from player to player,
     * and that resulting bankruptcies occur when needed.
     */
    @Test
    public void payRent() {
        p1.buy(property1);
        int initialMoney1 = p1.getMoney();
        int initialMoney2 = p2.getMoney();
        p2.payRent(property1);
        //this first payment should not bankrupt player 2
        assertFalse(p2.getIsBankrupt());
        assertEquals(initialMoney1 + property1.getRent(), p1.getMoney());
        assertEquals(initialMoney2 - property1.getRent(), p2.getMoney());

        p2.loseMoney(p2.getMoney() - 1); //leave P2 with 1$
        p1.buy(property2);
        initialMoney1 = p1.getMoney();
        initialMoney2 = p2.getMoney();
        p2.payRent(property2);
        //this payment should bankrupt player 2
        assertTrue(p2.getIsBankrupt());
        assertEquals(initialMoney1 + initialMoney2, p1.getMoney());
        assertEquals(0, p2.getMoney());
    }

    /**
     * Tests that the player name getter functions correctly.
     */
    @Test
    public void getName() {
        assertEquals("P1", p1.getName());
        assertEquals("P2", p2.getName());
    }

    /**
     * Tests that the player money getter functions correctly, and that starting money is equal and nonzero.
     * A specific value is not tested because the starting money may change.
     */
    @Test
    public void getMoney() {
        assertNotEquals(0, p1.getMoney());
        assertTrue(p1.getMoney() == p2.getMoney());
    }

    /**
     * Tests whether player numbers are correctly assigned on creation and retrieved.
     * Player numbers should be assigned in ascending order.
     */
    @Test
    public void getPlayerNumber() {
        assertEquals(p2.getPlayerNumber(), p1.getPlayerNumber() + 1);
    }

    /**
     * Tests that player properties are properly retrieved.
     */
    @Test
    public void getProperties() {
        assertEquals(0, p1.getProperties().size());
        p1.buy(property1);
        p1.buy(property2);
        assertEquals(2, p1.getProperties().size());
    }

    /**
     * Tests that players properly lose money and that loseMoney's return value accurately reflects whether
     * the player went bankrupt.
     */
    @Test
    public void loseMoney() {
        int initialMoney = p1.getMoney();
        int lostMoney = p1.loseMoney(initialMoney - 1);
        assertEquals(initialMoney - 1, lostMoney);
        assertEquals(1, p1.getMoney());
        lostMoney = p1.loseMoney(3); //players go bankrupt if they are at 0$ or less
        assertEquals(1, lostMoney);
        assertEquals(0, p1.getMoney());
        lostMoney = p1.loseMoney(2); //player money should not be reduced beyond 0
        assertEquals(0, lostMoney);
        assertEquals(0, p1.getMoney());
    }

    /**
     * Tests that players gain money properly.
     */
    @Test
    public void gainMoney() {
        int initialMoney = p1.getMoney();
        p1.gainMoney(100);
        assertEquals(initialMoney + 100, p1.getMoney());
    }

    /**
     * Tests that player position is correctly initialized, set and retrieved.
     */
    @Test
    public void setPosition() {
        //test initial positions
        assertEquals(0, p1.getPosition());
        assertEquals(0, p2.getPosition());
        p1.setPosition(3);
        p2.setPosition(45);
        assertEquals(3, p1.getPosition());
        assertEquals(45, p2.getPosition());
    }

    /**
     * Tests that players are properly marked as bankrupt, and that all necessary information is updated,
     * including money, status and properties.
     */
    @Test
    public void bankrupt() {
        assertFalse(p1.getIsBankrupt());
        p1.buy(property1);
        p1.bankrupt();
        assertEquals(0, p1.getProperties().size());
        assertEquals(null, property1.getOwner());
        assertTrue(p1.getIsBankrupt());
    }

    /**
     * Tests canBuy returns a correct value.
     */
    @Test
    public void canBuy() {
        assertTrue(p1.canBuy(property1));
        p1.gainMoney(1000);
        p1.buy(property1);
        assertFalse(p2.canBuy(property1));
        p1.loseMoney(p1.getMoney() - 1);
        assertFalse(p1.canBuy(property2));
    }

    /**
     * Tests canExitJail returns a correct value.
     */
    @Test
    public void canExitJail() {
        p1.setJailTimer(3);
        assertFalse(p1.canExitJail());
        p1.setJailTimer(2);
        p1.loseMoney(p1.getMoney() - 1);
        assertFalse(p1.canExitJail());
        p1.gainMoney(1000);
        assertTrue(p1.canExitJail());
    }

    /**
     * Test that isAI and getPlayerAI return the correct values.
     */
    @Test
    public void isAI() {
        Player ai1 = new Player("AI1", board, true);
        assertTrue(ai1.isAI());
        assertNotNull(ai1.getPlayerAI());
    }
}