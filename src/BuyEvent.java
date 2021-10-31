import java.util.EventObject;

public class BuyEvent extends EventObject {
    private Player player;

    /**
     * Constructs a prototypical Event.
     *
     * @param board the object on which the Event initially occurred
     * @param player
     * @throws IllegalArgumentException if source is null
     */
    public BuyEvent(Board board, Player player) {
        super(board);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public Board getSource() {
        return (Board) super.getSource();
    }
}
