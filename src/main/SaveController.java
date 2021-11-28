package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SaveController implements ActionListener {

    private Board board;

    public SaveController(Board board) {
        this.board = board;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String filepath = (String) JOptionPane.showInputDialog(null, "Select a save slot",
                "Save Game", JOptionPane.PLAIN_MESSAGE, null,
                BoardView.SAVE_LOCATIONS, BoardView.SAVE_LOCATIONS[0]);
        if (filepath == null) {
            return; //player cancelled or exited out of the dialog without selecting
        }
        try {
            board.exportBoard(filepath);
            JOptionPane.showMessageDialog(null, "Game successfully saved.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Could not save game to file.");
        }
    }
}
