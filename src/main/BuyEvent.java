package main;

import java.util.EventObject;

public class BuyEvent extends EventObject {

    Property property;

    /**
     * Constructs a prototypical Event.
     *
     * @param player the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public BuyEvent(Player player, Property property) {
        super(player);
        this.property = property;
    }

    @Override
    public Player getSource() {
        return (Player) super.getSource();
    }

    public Property getProperty() {
        return this.property;
    }
}
