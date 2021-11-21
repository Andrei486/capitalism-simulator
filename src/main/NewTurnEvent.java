package main;

import java.util.EventObject;

public class NewTurnEvent extends EventObject {

    public NewTurnEvent(Board board) {
        super(board);
    }

    @Override
    public Board getSource() {
        return (Board) super.getSource();
    }
}
