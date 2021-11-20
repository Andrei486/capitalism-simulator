package main;
import main.*;

/**
 *  Class representing the utilities properties
 * @author  Waleed Majbour 101144882
 */
public class Utility extends Property{
    private static final int UTILITY_COST = 150;

    /**
     * Creates a new property with a given cost and color.
     *
     * @param name the name of the property
     * @param cost the cost to buy the new property
     */
    public Utility(String name, int cost) {
        super(name, UTILITY_COST);
    }

    /**
     * Determines rent multiplier based on how many utilities owned by same player.
     * @return rent of utilities.
     */
    @Override
    public int getRent() {
       int utilitiesOwned = 0;
       for(Property property: getOwner().getProperties()){
           if(property instanceof Utility){
               utilitiesOwned++;

               if(utilitiesOwned == 1){
                   return getOwner().getLastSpacesMoved() * 4;
               }
               if(utilitiesOwned == 2){
                   return getOwner().getLastSpacesMoved() * 10;
               }
           }
       }
        return 0;

    }


    /**
     * Builds a string to represent the information held in the class.
     * @return a string representing this Utility class.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Property: " + this.name + "\n");
        sb.append("Cost: " + this.getCost() + "\n");
        if(this.getOwner() == null){
            sb.append("If one utility is owned, rent is 4 times what is shown on dice.\n");
            sb.append("If both utilities are owned, rent is 10 times what is shown on dice.");
        }else{
            sb.append("Owned by " + this.getOwner().getName());
        }
        return sb.toString();
    }
}
