package main;

import java.util.EventObject;

/**
 * An event which informs objects of Player class running out of money.
 * @author Sebastian Lionais 101157892
 */
public class BankruptcyEvent extends EventObject {
    /**
     * Constructs an Event corresponding to a Player going bankrupt.
     *
     * @param player the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public BankruptcyEvent(Player player) {
        super(player);
    }

    /**
     * Gets the player who has gone bankrupt.
     * @return the player
     */
    @Override
    public Player getSource() {
        return (Player) super.getSource();
    }
}
