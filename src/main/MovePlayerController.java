package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovePlayerController implements ActionListener {

    Board board;

    public MovePlayerController(Board board) {
        this.board = board;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //this can become incredibly long if a player goes bankrupt, leaving only AIs, so don't run it on the EDT
        SwingWorker<Integer, Integer> worker = new SwingWorker<Integer, Integer>() {
            @Override
            protected Integer doInBackground() throws Exception {
                board.movePlayer(board.getCurrentPlayer());
                return 0;
            }
        };
        worker.execute();
    }
}
