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

    private void roll() {
        die1 = (int) (Math.random() * 6) + 1;
        die2 = (int) (Math.random() * 6) + 1;
    }

    public int getDie1() {
        return die1;
    }

    public int getDie2() {
        return die2;
    }

    public int getTotal() {
        return die1 + die2;
    }

    public void isDouble(){
        if(die1 == die2){
            System.out.print("Player plays again.");
        }
    }

    public String toString() {
        return "Player Moves " + getTotal() + " spaces.";
    }

    public static void main(String[] args) {

        DiceRoller dice;
        int rollCount;

        dice = new DiceRoller();
        dice.roll();

        System.out.println(dice.getDie1() + " and " + dice.getDie2());

        dice.isDouble();
    }
}
