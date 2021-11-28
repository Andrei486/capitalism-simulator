package main;

import java.io.Serializable;

/**
 * Using the Random util library to simulate rolling dice with a limit of 1 - 6 per die.
 *
 * @author Waleed Majbour (101144882)
 */

public class DiceRoller implements Serializable {

    private int die1;
    private int die2;

    /**
     * Declares die1 and die2.
     */
    public DiceRoller() {
        roll();
    }

    /**
     * Rolls the dice.
     */
    public void roll() {
        die1 = (int) (Math.random() * 6) + 1;
        die2 = (int) (Math.random() * 6) + 1;
    }

    /**
     * Gets the last rolled value of both dice.
     * @return an array of length 2 containing the last rolled value of each die
     */
    public int[] getDice() {
        int[] dice = {this.die1, this.die2};
        return dice;
    }

    /**
     * Sets the value of both dice to specific numbers.
     */
    public void forceRoll(int die1, int die2) {
        this.die1 = die1;
        this.die2 = die2;
    }

    /**
     * Calculate the total roll of both dice.
     * @return sum of die1 and die2
     */
    public int getTotal() {
        return die1 + die2;
    }

    /**
     * Check if both dice rolled the same number.
     * @return true if die1 and die2 are equal.
     */
    public boolean isDouble(){
        return die1 == die2;
    }
}
