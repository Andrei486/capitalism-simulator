import java.util.EventObject;

public class BuyEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     * @param player the player buying a property
     * @throws IllegalArgumentException if source is null
     */
    public BuyEvent(Player player) {
        super(player);
    }

    @Override
    public Player getSource() {
        return (Player) super.getSource();
    }
}
