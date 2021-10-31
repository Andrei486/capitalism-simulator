import java.util.EventObject;

public class MovePlayerEvent extends EventObject {
    private Player player;
    private int oldPosition;

    /**
     * Constructs a prototypical Event.
     *
     * @param board the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public MovePlayerEvent(Board board, Player player, int oldPosition) {
        super(board);
        this.player = player;
        this.oldPosition = oldPosition;
    }

    public int getOldPosition() {
        return oldPosition;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public Board getSource() {
        return (Board) super.getSource();
    }
}
