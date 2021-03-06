package main;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that manages a color group of RealEstates and building houses on them.
 * @author Andrei Popescu, 101143798
 */
public class RealEstateGroup implements Serializable {
    private final Set<RealEstate> realEstates;
    private final ColorGroup color;
    private boolean ownedBySamePlayer; //true if all properties are owned by the same (non-null) player
    public final static int MAX_HOUSES = 5; //max number of houses on any given property

    /**
     * Creates a RealEstateGroup for properties of a given color, to which properties can be added.
     * @param color the color of property that will be added to the group
     */
    public RealEstateGroup(ColorGroup color) {
        this.color = color;
        this.realEstates = new HashSet<>();
        this.ownedBySamePlayer = false;
    }

    /**
     * Adds a RealEstate property to this RealEstateGroup.
     * @param r the RealEstate to add
     */
    public void addRealEstate(RealEstate r) {
        realEstates.add(r);
    }

    /**
     * Gets all RealEstates in this group. Used for testing.
     * @return Set containing all the RealEstates in this group
     */
    public Set<RealEstate> getRealEstates() {
        return this.realEstates;
    }

    /**
     * Checks whether one player owns all the properties on this group. Used for testing.
     */
    public void updateOwnership() {
        HashSet<Player> owners = new HashSet<>();
        for (RealEstate r : realEstates) {
            owners.add(r.getOwner());
        }
        //check that only one player owns properties in the group, and that all properties in the group are owned
        ownedBySamePlayer = (owners.size() == 1 && !owners.contains(null));
    }

    /**
     * Checks whether a house can be bought on the given real estate property.
     * @param r the RealEstate to check
     * @return true if a house can be bought by the owner, false if there is no owner or a house cannot be bought
     */
    public boolean canAddHouse(RealEstate r) {
        if (r.getOwner() == null
                || !ownedBySamePlayer
                || r.getOwner().getMoney() <= r.getHouseCost()
                || r.getHouses() == MAX_HOUSES) {
            return false;
        }
        int maxHousesInGroup = 0;
        int minHousesInGroup = MAX_HOUSES;
        for (RealEstate realEstate : realEstates) {
            if (realEstate.getHouses() > maxHousesInGroup) {
                maxHousesInGroup = realEstate.getHouses();
            }
            if (realEstate.getHouses() < minHousesInGroup) {
                minHousesInGroup = realEstate.getHouses();
            }
        }
        //can only buy if all properties have equal houses, or if this property has the least houses
        return (maxHousesInGroup == minHousesInGroup) || (r.getHouses() == minHousesInGroup);
    }
}
