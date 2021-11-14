package main;

/**
 * Class representing a property that is a railroad
 * @author Sebastian Lionais 101157892
 */
public class Railroad extends Property{
    private static final int RAILROAD_COST = 200;

    /**
     * Creates a new property with a given cost and color.
     * @param name the name of the property
     */
    public Railroad(String name) {
        super(name, RAILROAD_COST);
    }

    /**
     * Loops through the players properties to find out the rent.
     * Assumption: This method will not be called unless a player owns it.
     * @return the rent of the Railroad
     */
    @Override
    public int getRent() {
        int railroadsOwned = 0;
        for (Property property: getOwner().getProperties()) {
            if (property instanceof Railroad) {
                railroadsOwned++;
            }
        }
        return  25 * (int)(Math.pow(2, (railroadsOwned - 1)));
    }

    /**
     * Builds a string to represent the information held in the class
     * @return a string representing this Railroad class
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Property: " + this.name + "\n");
        sb.append("Cost:     $" + this.getCost() + "\n");
        if (this.getOwner() == null) {
            sb.append("Rent(1):  $25\n");
            sb.append("Rent(2):  $50\n");
            sb.append("Rent(3):  $100\n");
            sb.append("Rent(4):  $200\n");
            sb.append("Not owned");
        } else {
            sb.append("Rent:     $" + this.getRent() + "\n");
            sb.append("Owned by " + this.getOwner().getName());
        }
        return sb.toString();
    }
}
