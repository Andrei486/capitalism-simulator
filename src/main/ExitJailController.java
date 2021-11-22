package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class to interface between the BoardView and Board.
 * Handles players voluntarily exiting jail.
 * @author Andrei Popescu, 101143798
 */
public class ExitJailController implements ActionListener {

    private Board board;

    /**
     * Constructs an ExitJailController object.
     * @param board Board to connect to
     */
    public ExitJailController(Board board) {
        this.board = board;
    }

    /**
     * Moves the player out of jail, paying the fee.
     * @param e the event that activated this method
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Player currentPlayer = board.getCurrentPlayer();
        currentPlayer.loseMoney(Board.EXIT_JAIL_COST);
        currentPlayer.setJailTimer(0);
        currentPlayer.setPosition(currentPlayer.getPosition());
    }
}
