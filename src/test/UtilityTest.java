package test;

import main.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for testing methods in Utility class
 * @author Waleed Majbour 101144882
 */

public class UtilityTest {
    private Utility utility, utility2;
    private DiceRoller dice;
    Board board;

    @Before
    public void setVars(){
        board = new Board(2);
        dice = board.getDiceRoller();
        utility = new Utility("Water Works", dice);
    }

    @Test
    public void testConst(){
        assertNotNull(utility);
    }

    @Test
    public void getName(){
        assertEquals("Water Works", utility.getName());
    }

    @Test
    public void getCost(){
        assertEquals(150, utility.getCost());
    }

    @Test
    public void getRentOne() {
        Player P1 = new Player("P1", board);
        Player P2 = new Player("P2", board);
        P1.gainMoney(400);
        P1.buy(utility);
        board.advanceTurn();
        board.getDiceRoller().forceRoll(1,3);
        assertEquals(16, utility.getRent());
    }

    @Test
    public void getRentTwo() {
        Player P1 = new Player("P1", board);
        Player P2 = new Player("P2", board);
        utility2 = new Utility("Electricity", dice);
        P1.gainMoney(5000);
        P1.buy(utility);
        P1.buy(utility2);
        board.advanceTurn();
        dice.forceRoll(1,3);
        assertEquals(40, utility.getRent());
    }

    @Test
    public void testToStringNotOwned() {
        String sb = "Property: Water Works\n" +
                    "Cost: $150\n" +
                    "If one utility is owned, rent is 4 times what is shown on dice.\n" +
                    "If both utilities are owned, rent is 10 times what is shown on dice.";

        assertEquals(sb, utility.toString());
    }

    @Test
    public void testToStringOwnedOne() {
        String sb = "Property: Water Works\n" +
                "Cost: $150\n" +
                "Rent: 4 * Roll\n" +
                "Owned by P1";

        Player p1 = new Player("P1", new Board(3));
        p1.gainMoney(1000);
        p1.buy(utility);

        assertEquals(sb, utility.toString());
    }

    @Test
    public void testToStringOwnedTwo() {
        String sb = "Property: Water Works\n" +
                "Cost: $150\n" +
                "Rent: 10 * Roll\n" +
                "Owned by P1";

        Player p1 = new Player("P1", new Board(3));
        utility2 = new Utility("Electric Company", dice);
        p1.gainMoney(1000);
        p1.buy(utility);
        p1.buy(utility2);

        assertEquals(sb, utility.toString());
    }
}

