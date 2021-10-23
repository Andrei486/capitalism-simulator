import java.util.EventObject;

public class RentEvent extends EventObject {
    private Property property;
    private int rentPaid;

    /**
     * Constructs a prototypical Event.
     *
     * @param player the object on which the Event initially occurred
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
    public Object getSource() {
        return (Player) super.getSource();
    }
}
