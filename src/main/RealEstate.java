package main;

public class RealEstate extends Property{

    private ColorGroup colorGroup;
    private static final float RENT_MULTIPLIER = 2.0f;

    public RealEstate(String name, int cost, ColorGroup colorGroup) {
        super(name, cost);
        this.colorGroup = colorGroup;
    }

    /**
     * Get the cost of rent on this property, including houses and hotels.
     * Costs are always rounded down.
     * @return integer representing the amount of currency to be paid as rent on this property
     */
    @Override
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

