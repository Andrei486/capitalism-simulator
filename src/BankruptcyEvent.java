import java.util.EventObject;

public class BankruptcyEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param player the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public BankruptcyEvent(Player player) {
        super(player);
    }

    /**
     * Gets the player paying the rent.
     * @return the player
     */
    @Override
    public Object getSource() {
        return (Player) super.getSource();
    }
}
