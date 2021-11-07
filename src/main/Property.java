package main;

/**
 * Class representing a Monopoly property that can be bought and owned by a player.
 * @author Andrei Popescu, 101143798
 */
public class Property {

    private String name;
    private int cost;
    private ColorGroup colorGroup;
    private Player owner = null;
    private static final float RENT_MULTIPLIER = 2.0f;

    /**
     * Creates a new property with a given cost and color.
     * @param cost the cost to buy the new property
     * @param colorGroup the color group that the new property should be part of
     */
    public Property(String name, int cost, ColorGroup colorGroup) {
        this.name = name;
        this.cost = cost;
        this.colorGroup = colorGroup;
    }

    /**
     * Get the cost to buy this property.
     * @return integer representing the amount of currency needed to buy this property
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Gets the name of this property.
     * @return String containing the property's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the cost of rent on this property, including houses and hotels.
     * Costs are always rounded down.
     * @return integer representing the amount of currency to be paid as rent on this property
     */
    public int getRent() {
        return (int) (this.cost * RENT_MULTIPLIER); //include houses and hotels here later
    }

    /**
     * Gets the color group that this property is part of.
     * @return color of this property as a ColorGroup
     */
    public ColorGroup getColorGroup() {
        return this.colorGroup;
    }

    /**
     * Gets the current owner of this property.
     * @return the Player that owns this property
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * Sets the current owner of this property.
     * @param p the Player who should own this property
     */
    public void setOwner(Player p) {
        this.owner = p;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Property: " + this.name + "\n");
        sb.append("Color:    " + this.getColorGroup() + "\n");
        sb.append("Cost:     $" + this.getCost() + "\n");
        sb.append("Rent:     $" + this.getRent() + "\n");
        if (this.getOwner() == null) {
            sb.append("Not owned");
        } else {
            sb.append("Owned by " + this.getOwner().getName());
        }
        return sb.toString();
    }
}
