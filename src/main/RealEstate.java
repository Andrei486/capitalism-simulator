package main;

/**
 * Class representing a property on which houses can be built.
 * @author Andrei Popescu, 101143798
 */
public class RealEstate extends Property{

    private final ColorGroup colorGroup;
    private static final float BASE_RENT_MULTIPLIER = 0.75f;
    private static final float HOUSE_RENT_MULTIPLIER = 0.5f;
    private static final float HOUSE_COST_MULTIPLIER = 0.3f;
    private int houses;
    private final RealEstateGroup group;

    /**
     * Constructs a RealEstate object.
     * @param name the name of the RealEstate to create
     * @param cost the cost to buy the RealEstate
     * @param colorGroup the color of the new RealEstate
     * @param group the group to add the new RealEstate to
     */
    public RealEstate(String name, int cost, ColorGroup colorGroup, RealEstateGroup group) {
        super(name, cost);
        this.colorGroup = colorGroup;
        this.group = group;
        this.houses = 0;
        this.group.addRealEstate(this);
    }

    /**
     * Get the cost of rent on this property, including houses and hotels.
     * Costs are always rounded down.
     * @return integer representing the amount of currency to be paid as rent on this property
     */
    @Override
    public int getRent() {
        //rent is a percentage of the base cost plus an additional amount per house
        return (int) (this.cost * (BASE_RENT_MULTIPLIER + houses * HOUSE_RENT_MULTIPLIER));
    }

    /**
     * Gets the color group that this property is part of.
     * @return color of this property as a ColorGroup
     */
    public ColorGroup getColorGroup() {
        return this.colorGroup;
    }

    @Override
    public void setOwner(Player p) {
        super.setOwner(p);
        group.updateOwnership();
        if (p == null) {
            houses = 0; //reset houses when someone goes bankrupt and returns the property
        }
    }

    /**
     * Gets the cost of building one house on this property.
     * @return integer cost to pay to build a house
     */
    public int getHouseCost() {
        return (int) (this.cost * HOUSE_COST_MULTIPLIER);
    }

    /**
     * Gets the number of houses currently on this property. A hotel counts as 5 houses.
     * @return number of houses on the property
     */
    public int getHouses() {
        return this.houses;
    }

    /**
     * Adds one house to this property. Assumes this is a valid operation.
     */
    public void addHouse() {
        this.houses++;
    }

    /**
     * Checks whether a house can be bought on this property.
     * @return true if a house can be bought by the owner, false if there is no owner or a house cannot be bought
     */
    public boolean canAddHouse() {
        return this.group.canAddHouse(this);
    }

    /**
     * Returns a String representation that can be used to display information about this RealEstate.
     * @return display-friendly String containing information about this object
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Property: " + this.name + "\n");
        sb.append("Color: " + this.getColorGroup() + "\n");
        sb.append("Cost: $" + this.getCost() + "\n");
        sb.append("House cost: $" + this.getHouseCost() + "\n");
        sb.append("Rent: $" + this.getRent() + "\n");
        if (this.getHouses() == 5) {
            sb.append("Hotel built\n");
        } else {
            sb.append(this.getHouses() + " houses built\n");
        }
        if (this.getOwner() == null) {
            sb.append("Not owned");
        } else {
            sb.append("Owned by " + this.getOwner().getName());
        }
        return sb.toString();
    }
}

