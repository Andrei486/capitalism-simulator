import java.util.EventObject;

/**
 * Event class to represent a player moving along the board.
 */
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

    /**
     * Gets the position where the player was before moving.
     * @return the previous integer position of the player, before the movement
     */
    public int getOldPosition() {
        return oldPosition;
    }

    /**
     * Gets the player who moved.
     * @return Player that moved along the board
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the Board that a player moved on.
     * @return Board that created the event
     */
    @Override
    public Board getSource() {
        return (Board) super.getSource();
    }
}
