package main;

import javax.swing.*;
import java.awt.*;

/**
 * @author Waleed Majbour 101144882
 * @author Mohammed Alkhaledi 101162465
 * @author Sebastian Lionais 101157892
 * @author Andrei Popescu 101143798
 */
public class BoardView extends JFrame implements MonopolyView {

    private Board board;
    private SpacePanel[] spacePanels;
    private JLabel[] playerInfoLabels;
    private JLabel[] diceRollLabels;
    private JLabel totalRollLabel;
    private JButton buyButton;

    /**
     * Construct a BoardView to represent a Board, initializing all UI elements.
     * @param board the Board that should be displayed
     */
    public BoardView(Board board) {
        super();
        this.board = board;
        this.board.addGameView(this);
        this.setLayout(new BorderLayout());
        spacePanels = new SpacePanel[Board.BOARD_SIZE];

        //set up the grid
        JPanel gridPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;

        //create the spaces on the board and position them
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            int[] position = getGridPosition(i);
            SpacePanel panel;
            Space space = this.board.getSpace(i);
            if (space instanceof PropertySpace) {
                Property property = ((PropertySpace) space).getProperty();
                if (property instanceof RealEstate) {
                    panel = new RealEstateSpacePanel((RealEstate) property);
                } else {
                    panel = null; //handle other types of properties here
                }
            }else if(space instanceof JailSpace){
                panel = new JailSpacePanel();
            }
            else if(space instanceof GoToJailSpace){
                panel = new GoToJailSpacePanel();
            }
            else if(space instanceof GoSpace){
                panel = new GoSpacePanel();
            }
            else {
                panel = new EmptySpacePanel();
            }
            c.gridx = position[0];
            c.gridy = position[1];
            gridPanel.add(panel, c);
            spacePanels[i] = panel;
        }

        //place the players at their current positions
        for (Player player : this.board.getPlayers()) {
            spacePanels[player.getPosition()].addPlayer(player);
        }

        //create player information labels for names & money
        JPanel gridCenterPanel = new JPanel(new BorderLayout());
        JPanel playerInfoPanel = new JPanel(new FlowLayout());
        playerInfoLabels = new JLabel[this.board.getPlayers().length];
        for (int i = 0; i < this.board.getPlayers().length; i++) {
            playerInfoLabels[i] = new JLabel();
            playerInfoPanel.add(playerInfoLabels[i]);
        }
        updatePlayerLabels();
        gridCenterPanel.add(playerInfoPanel, BorderLayout.NORTH);

        //create labels for dice rolls and total
        JPanel centerPanel = new JPanel(new FlowLayout());
        diceRollLabels = new JLabel[2];
        diceRollLabels[0] = new JLabel();
        diceRollLabels[1] = new JLabel();
        totalRollLabel = new JLabel();
        centerPanel.add(new JLabel("Dice rolls: "));
        centerPanel.add(diceRollLabels[0]);
        centerPanel.add(diceRollLabels[1]);
        centerPanel.add(new JLabel("Dice total: "));
        centerPanel.add(totalRollLabel);
        updateDiceLabels();

        //create buttons to buy properties and end turn
        buyButton = new JButton();
        buyButton.setText("Buy");
        buyButton.addActionListener(new BuyController(this.board));
        centerPanel.add(buyButton);
        updateBuyButton();

        JButton endTurnButton = new JButton();
        endTurnButton.setText("End Turn");
        endTurnButton.addActionListener(new EndTurnController(this.board));
        centerPanel.add(endTurnButton);
        gridCenterPanel.add(centerPanel, BorderLayout.CENTER);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 9;
        c.gridheight = 9;
        c.fill = GridBagConstraints.BOTH;
        gridPanel.add(gridCenterPanel, c);

        //fit the entire board in a scroll pane in case the board is too large for the screen
        JScrollPane gridScrollPane = new JScrollPane(gridPanel);
        gridScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        gridScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(gridScrollPane, BorderLayout.CENTER);

        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Gets the grid coordinates (on a 10x10 GridBagLayout) of a board space by index position.
     * @param position the position of the space on the board (0 is GO, increases clockwise)
     * @return array of 2 integers, respectively the x and y coordinates to use on a GridBagLayout
     */
    private int[] getGridPosition(int position) {
        int x;
        int y;
        if (0 <= position && position < 10) {
            x = 10 - (position % 10);
            y = 10;
        } else if (10 <= position && position < 20) {
            x = 0;
            y = 10 - (position % 10);
        } else if (20 <= position && position < 30) {
            x = (position % 10);
            y = 0;
        } else {
            x = 10;
            y = (position % 10);
        }
        int[] coordinates = {x, y};
        return coordinates;
    }

    /**
     * Updates the player labels, including player money values and player status (acting/bankrupt).
     */
    private void updatePlayerLabels() {
        for (int i = 0; i < playerInfoLabels.length; i++) {
            Player player = board.getPlayers()[i];
            JLabel label = playerInfoLabels[i];
            label.setText(String.format("%s: $%d", player.getName(), player.getMoney()));
            if (player == board.getCurrentPlayer()) {
                label.setForeground(Color.BLUE);
            } else if (player.getIsBankrupt()) {
                label.setForeground(Color.RED);
            } else {
                label.setForeground(Color.BLACK);
            }
        }
    }

    /**
     * Updates the dice roll labels to show the most recent numbers rolled and the total.
     */
    private void updateDiceLabels() {
        diceRollLabels[0].setText(String.valueOf(this.board.getDiceRoller().getDice()[0]));
        diceRollLabels[1].setText(String.valueOf(this.board.getDiceRoller().getDice()[1]));
        totalRollLabel.setText(String.valueOf(this.board.getDiceRoller().getTotal()));
    }

    /**
     * Updates the status of the buy button, enabling it or disabling it depending on whether
     * the current player is able to buy a property.
     */
    private void updateBuyButton() {
        buyButton.setEnabled(true);

        Player currentPlayer = board.getCurrentPlayer();
        Space currentSpace = board.getSpace(currentPlayer.getPosition());

        //if space is not a property space
        if (!(currentSpace instanceof PropertySpace)) {
            buyButton.setEnabled(false);
            return;
        }

        PropertySpace currentPropertySpace = (PropertySpace) currentSpace;
        Property currentProperty = currentPropertySpace.getProperty();

        if (currentProperty.getOwner() != null) {
            buyButton.setEnabled(false);
            return;
        }
        //if player does not have more than the cost of the property
        else if (currentPlayer.getMoney() <= currentProperty.getCost()) {
            buyButton.setEnabled(false);
            return;
        }
    }

    /**
     * Updates the board display in response to a player moving.
     * This includes moving the player along the board spaces and updating information.
     * @param e MovePlayerEvent representing the movement
     */
    @Override
    public void handleMovePlayer(MovePlayerEvent e) {
        Player player = e.getPlayer();
        this.spacePanels[e.getOldPosition()].removePlayer(player);
        this.spacePanels[player.getPosition()].addPlayer(player);
        updatePlayerLabels();
        updateDiceLabels();
        updateBuyButton();
    }

    /**
     * Updates the board display in response to a player buying a property.
     * @param e BuyEvent representing the transaction
     */
    @Override
    public void handleBuy(BuyEvent e) {
        updatePlayerLabels();
        updateBuyButton();
    }

    /**
     * Updates the board display in response to a player paying rent on a property.
     * @param e RentEvent representing the transaction
     */
    @Override
    public void handlePayRent(RentEvent e) {
        updatePlayerLabels();
    }

    /**
     * Updates the board display in response to a player going bankrupt.
     * Also ends the game if there is only one player left, showing a message with the winner's name.
     * @param e BankruptcyEvent representing the bankruptcy that occurred
     */
    @Override
    public void handleBankruptcy(BankruptcyEvent e) {
        updatePlayerLabels();
        if (this.board.isGameOver()) {
            Player winner = null;
            for (Player player: this.board.getPlayers()) {
                if (!player.getIsBankrupt()) {
                    winner = player;
                    break;
                }
            }
            JOptionPane.showMessageDialog(this, String.format("%s won!", winner.getName()));
            System.exit(0);
        }
    }

    @Override
    public void handleBuyHouse(BuyHouseEvent e) {
        //TODO implement
    }

    public static void main(String[] args) {
        boolean isInputValid = false;
        int playerCount = 0;
        while (!isInputValid) {
            String playerCountString = JOptionPane.showInputDialog("Please enter the number of players (2-8).");
            if (playerCountString == null) {
                System.exit(0);
            }
            try {
                playerCount = Integer.parseInt(playerCountString);
                if (2 <= playerCount && playerCount <= 8) {
                    isInputValid = true;
                } else {
                    JOptionPane.showMessageDialog(null,
                            String.format("The number of players must be between 2 and 8 (inclusive)."));
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        String.format("%s is not a valid number of players.", playerCountString));
            }
        }
        Board board = new Board(playerCount);
        new BoardView(board);
    }
}
