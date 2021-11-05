import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Mohammad Alkhaledi 101162465
 * Test class for testing methods in diceroller class
 */
public class DiceRollerTest {
    DiceRoller dice;


    /**
     * test random roll between 2-12
     */
    @Test
    public void roll() {
        dice = new DiceRoller();
        dice.roll();
        assertTrue(2 <= dice.getTotal() && dice.getTotal() <= 12);
    }

    /**
     *test a forced roll(roll where numbers are inputted for each die)
     */
    @Test
    public void forceRoll() {
        dice = new DiceRoller();
        dice.forceRoll(5,6);
        assertEquals(11, dice.getTotal());
    }


    /**
     * test to see if checks doubles correctly
     */
    @Test
    public void isDouble() {
        dice = new DiceRoller();
        dice.forceRoll(6,6);
        assertTrue(dice.isDouble());
    }


}