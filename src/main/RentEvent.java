package main;

import java.util.EventObject;

/**
 * Event which informs other objects about a Player paying rent, how much they paid, and the property they paid from.
 * @author Sebastian Lionais 101157892
 */
public class RentEvent extends EventObject {
    private Property property;
    private int rentPaid;

    /**
     * Constructs an Event corresponding to a player paying rent on a property.
     *
     * @param player the object on which the Event initially occurred
     * @param rentPaid the actual amount of money paid as rent
     * @param property the property on which rent was paid
     * @throws IllegalArgumentException if source is null
     */
    public RentEvent(Player player, int rentPaid, Property property) {
        super(player);
        this.rentPaid = rentPaid;
        this.property = property;
    }

    /**
     * Gets the property that is determining the rent.
     * @return the property
     */
    public Property getProperty() {
        return property;
    }

    /**
     * Gets the amount of rent that was paid.
     * @return an int representing the amount paid
     */
    public int getRentPaid() {
        return rentPaid;
    }

    /**
     * Gets the player paying the rent.
     * @return the player
     */
    @Override
    public Player getSource() {
        return (Player) super.getSource();
    }
}
