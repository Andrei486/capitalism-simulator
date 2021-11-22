package main;

import java.util.EventObject;

/**
 * Event class indicating that a new player turn has begun.
 */
public class NewTurnEvent extends EventObject {

    /**
     * Constructs a new NewTurnEvent.
     * @param board Board for which the turn changed
     */
    public NewTurnEvent(Board board) {
        super(board);
    }

    /**
     * Gets the Board that created this event.
     * @return Board object that created the event
     */
    @Override
    public Board getSource() {
        return (Board) super.getSource();
    }
}
