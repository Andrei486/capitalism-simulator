package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovePlayerController implements ActionListener {

    Board board;

    public MovePlayerController(Board board) {
        this.board = board;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        board.movePlayer(board.getCurrentPlayer());
    }
}
