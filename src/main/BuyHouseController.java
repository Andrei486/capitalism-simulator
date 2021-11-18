package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class that interfaces between the view and model classes (BoardView and Board)
 * to allow players to buy houses on real estate properties in a Monopoly game.
 * @author Andrei Popescu, 101143798
 */
public class BuyHouseController implements ActionListener {

    private Board board;

    /**
     * Constructs HouseBuyController object.
     * @param board the board the HouseBuyController is connecting to
     */
    public BuyHouseController(Board board) {
        this.board = board;
    }

    /**
     * Prompts a player to select a property to buy a house on from a drop-down. If the player selects a property,
     * buys one house on that property; if the player cancels or closes the input dialog, does not buy any house.
     * Called when the buy house button is pressed.
     * @param e ActionEvent that caused the method to be called
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Player currentPlayer = board.getCurrentPlayer();
        RealEstate[] buyHouseProperties = currentPlayer.getHouseBuyProperties()
                .toArray(new RealEstate[currentPlayer.getHouseBuyProperties().size()]);
        if (buyHouseProperties.length == 0) { //should not be allowed to happen, but if it does, do nothing
            return;
        }
        String[] propertyNames = new String[buyHouseProperties.length];
        for (int i = 0; i < buyHouseProperties.length; i++) {
            propertyNames[i] = buyHouseProperties[i].getName();
        }
        String selectedName = (String) JOptionPane.showInputDialog(null, "Select a property",
                "Buy House", JOptionPane.PLAIN_MESSAGE, null, propertyNames, propertyNames[0]);
        if (selectedName == null) {
            return; //player cancelled or exited out of the dialog without selecting
        }
        for (RealEstate realEstate : buyHouseProperties) {
            //assumes that no two real estates have the same name
            if (realEstate.getName() == selectedName) {
                currentPlayer.buyHouse(realEstate);
                return;
            }
        }
    }
}
