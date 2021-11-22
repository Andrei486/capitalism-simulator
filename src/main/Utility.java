package main;

/**
 *  Class representing the utilities properties
 * @author  Waleed Majbour 101144882
 */
public class Utility extends Property{
    private DiceRoller dice;
    private final static int UTILITY_COST = 150;

    /**
     * Creates a new property with a given cost and color.
     *
     * @param name the name of the property
     */
    public Utility(String name, DiceRoller dice) {
        super(name, UTILITY_COST);
        this.dice = dice;
    }

    /**
     * Determines rent multiplier based on how many utilities owned by same player.
     * @return rent of utilities.
     */
    @Override
    public int getRent() {
        int utilitiesOwned = getUtilitiesOwned();
        if(utilitiesOwned == 1){
            return dice.getTotal() * 4;
        }
        if(utilitiesOwned == 2){
            return dice.getTotal() * 10;
        }
        return 0;

    }

    /**
     * get the amount of utilities owned by the owner of this property.
     * @return an int representing the number of utilities owned.
     */
    private int getUtilitiesOwned() {
        int utilitiesOwned = 0;
        for(Property property: getOwner().getProperties()){
            if(property instanceof Utility){
                utilitiesOwned++;
            }
        }
        return utilitiesOwned;
    }


    /**
     * Builds a string to represent the information held in the class.
     * @return a string representing this Utility class.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Property: ").append(this.name).append("\n");
        sb.append("Cost: $").append(this.getCost()).append("\n");
        if(this.getOwner() == null){
            sb.append("If one utility is owned, rent is 4 times what is shown on dice.\n");
            sb.append("If both utilities are owned, rent is 10 times what is shown on dice.");
        }else{
            int utilitiesOwned = getUtilitiesOwned();
            if(utilitiesOwned == 1){
                sb.append("Rent: 4 * Roll\n");
            }
            else if (utilitiesOwned == 2){
                sb.append("Rent: 10 * Roll\n");
            }
            sb.append("Owned by " + this.getOwner().getName());
        }
        return sb.toString();
    }
}
