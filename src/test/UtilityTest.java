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
    Board board = new Board(2);

    @Before
    public void setVars(){
        utility = new Utility("Water Works", 150);
        dice = new DiceRoller();

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
        utility.setOwner(P1);
        board.advanceTurn();
        dice.forceRoll(1,3);
        assertEquals(16, utility.getRent());
    }

    @Test
    public void getRentTwo() {
        Player P1 = new Player("P1", board);
        Player P2 = new Player("P2", board);
        utility.setOwner(P1);
        utility2 = new Utility("Electricity", 150);
        utility2.setOwner(P2);
        board.advanceTurn();
        dice.forceRoll(1,3);
        assertEquals(40, utility.getRent());
    }

    @Test
    public void testToStringNotOwned() {
        String sb = "Property: Water Works\n" +
                    "Cost: 150\n" +
                    "If one utility is owned, rent is 4 times what is shown on dice.\n" +
                    "If both utilities are owned, rent is 10 times what is shown on dice.";

        assertEquals(sb, utility.toString());
    }

    @Test
    public void testToStringOwned() {
        String sb = "Property: Water Works\n" +
                "Cost: 150\n" +
                "Owned by P1";

        Player p1 = new Player("P1", new Board(3));
        p1.gainMoney(1000);
        p1.buy(utility);

        assertEquals(sb, utility.toString());
    }
}

