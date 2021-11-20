package main;

import java.util.EventObject;

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

    @Override
    public Player getSource() {
        return (Player) super.getSource();
    }
}
