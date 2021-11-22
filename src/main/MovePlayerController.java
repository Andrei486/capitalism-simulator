package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class to interface between the BoardView and Board.
 * Handles rolling the dice for movement and moving the player.
 * @author Andrei Popescu, 101143798
 */
public class MovePlayerController implements ActionListener {

    Board board;

    /**
     * Constructs a new MovePlayerController.
     * @param board Board that this controller is attached to
     */
    public MovePlayerController(Board board) {
        this.board = board;
    }

    /**
     * Rolls the dice and moves the Board's current player along the Board.
     * @param e event that triggered this method
     */
    @Override
    public void actionPerformed(ActionEvent e) {
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
