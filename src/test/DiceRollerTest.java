package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import main.*;

/**
 * Test class for testing methods in Diceroller class
 * @author Mohammad Alkhaledi 101162465
 */
public class DiceRollerTest {
    DiceRoller dice;

    /**
     *initializes a DiceRoller variable for testing
     */
    @Before
    public void setUp(){
        dice = new DiceRoller();
    }

    /**
     * test random roll between 2-12. tests the roll method 2000 times to make sure all values from 2-12 get reached
     */
    @Test
    public void roll() {
        for(int i = 0; i < 2000; ++i){
            dice.roll();
            assertTrue(2 <= dice.getTotal() && dice.getTotal() <= 12);
        }
    }

    /**
     *test a forced roll(roll where numbers are inputted for each die)
     */
    @Test
    public void forceRoll() {
        dice.forceRoll(5,6);
        Assert.assertEquals(11, dice.getTotal());
    }


    /**
     * test to see if checks doubles correctly
     */
    @Test
    public void isDouble() {
        dice.forceRoll(6,6);
        assertTrue(dice.isDouble());
    }


    /**
     *test the get dice method
     */
    @Test
    public void getDice() {
        dice.forceRoll(5,5);
        int[] arr = new int[]{5, 5};
        Assert.assertArrayEquals(arr, dice.getDice());
    }

    /**
     * test the get total method
     */
    @Test
    public void getTotal() {
        dice.forceRoll(5,5);
        Assert.assertEquals(10, dice.getTotal());
    }


}