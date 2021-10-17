import java.util.Random;

/**
 * Using the Random util library to simulate rolling dice with a limit of 1 - 6 per die.
 *
 * @author Waleed Majbour (101144882)
 */

public class DiceRoller {

    private int die1;
    private int die2;

    private int numSides;

    private Random roll;

    public DiceRoller() {
        roll();
    }

    public void roll() {
        die1 = (int) (Math.random() * 6) + 1;
        die2 = (int) (Math.random() * 6) + 1;
    }

    public int getTotal() {
        return die1 + die2;
    }

    public boolean isDouble(){
        return die1 == die2;
    }
}
