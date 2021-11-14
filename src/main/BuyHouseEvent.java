package main;

import java.util.EventObject;

/**
 * Event class created when a player buys a house on a real estate property.
 * @author Andrei Popescu, 101143798
 */
public class BuyHouseEvent extends EventObject {
    private RealEstate realEstate;
    /**
     * Constructs a prototypical Event.
     * @param player the player buying a house
     * @param realEstate the RealEstate on which the house was bought
     * @throws IllegalArgumentException if source is null
     */
    public BuyHouseEvent(Player player, RealEstate realEstate) {
        super(player);
        this.realEstate = realEstate;
    }

    /**
     * Gets the RealEstate on which the house was bought that caused this event.
     * @return RealEstate on which the house was bought
     */
    public RealEstate getRealEstate() {
        return this.realEstate;
    }

    /**
     * Gets the player who bought a property.
     * @return Player that created the BuyEvent and bought the property
     */
    @Override
    public Player getSource() {
        return (Player) super.getSource();
    }
}
