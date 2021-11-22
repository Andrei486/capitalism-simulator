package main;

import java.util.EventObject;

/**
 * Event class representing a change in a player's money.
 * @author Sebastian Lionais
 */
public class UpdateMoneyEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param player the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public UpdateMoneyEvent(Player player) {
        super(player);
    }

    /**
     * Gets the player that created this event
     * @return Player that lost or gained money
     */
    @Override
    public Player getSource() {
        return (Player) super.getSource();
    }
}
