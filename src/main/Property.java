package main;

import java.io.Serializable;

/**
 * Class representing a Monopoly property that can be bought and owned by a player.
 * @author Andrei Popescu, 101143798
 */
public abstract class Property implements Serializable {

    protected String name;
    protected int cost;
    private Player owner = null;

    /**
     * Creates a new property with a given cost and color.
     * @param name the name of the property
     * @param cost the cost to buy the new property
     */
    public Property(String name, int cost) {
        this.name = name;
        this.cost = cost;
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
     * Gets the rent of this property.
     * @return integer representing the amount to pay for landing on this property
     */
    public abstract int getRent();

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

    public abstract String toString();
}
