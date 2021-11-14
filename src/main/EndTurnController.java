package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class interfaces between the BoardView and the Board.
 * @author Sebastian Lionais 101157892
 */
public class EndTurnController implements ActionListener {
    private Board board;

    /**
     * Constructs EndTurnController object.
     * @param board the board the EndTurnController is connecting to
     */
    public EndTurnController(Board board) {
        this.board = board;
    }

    /**
     * This method when activated this advances the turn order.
     * @param e is the event which activated the method
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        board.advanceTurn();
    }
}
