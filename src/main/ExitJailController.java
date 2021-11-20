package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitJailController implements ActionListener {

    private Board board;

    public ExitJailController(Board board) {
        this.board = board;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Player currentPlayer = board.getCurrentPlayer();
        currentPlayer.loseMoney(Board.EXIT_JAIL_COST);
        currentPlayer.setJailTimer(0);
        currentPlayer.setPosition(currentPlayer.getPosition());
    }
}
