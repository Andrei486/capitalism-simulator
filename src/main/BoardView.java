package main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

/**
 * @author Waleed Majbour 101144882
 * @author Mohammed Alkhaledi 101162465
 * @author Sebastian Lionais 101157892
 * @author Andrei Popescu 101143798
 */
public class BoardView extends JFrame implements MonopolyView {

    private final Board board;
    private final SpacePanel[] spacePanels;
    private final JLabel[] playerInfoLabels;
    private final JLabel[] diceRollLabels;
    private final JLabel totalRollLabel;
    private final JButton buyButton;
    private final JButton buyHouseButton;
    private final JButton jailButton;
    private final JButton movePlayerButton;
    private final JButton endTurnButton;
    private final JButton saveButton;
    public static final String[] SAVE_LOCATIONS = {"save_file_1.ser", "save_file_2.ser", "save_file_3.ser"};

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
                    panel = new RealEstateSpacePanel((PropertySpace) space);
                } else {
                    panel = new PropertySpacePanel((PropertySpace) space);
                }
            } else if (space instanceof JailSpace){
                panel = new JailSpacePanel((JailSpace) space);
            }  else if (space instanceof GoToJailSpace){
                panel = new GoToJailSpacePanel((GoToJailSpace) space);
            }
            else if(space instanceof GoSpace){
                panel = new GoSpacePanel((GoSpace) space);
            }
            else {
                panel = new EmptySpacePanel((EmptySpace) space);
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

        //create buttons to buy properties and end turn
        buyButton = new JButton();
        buyButton.setText("Buy");
        buyButton.setMnemonic('B');
        buyButton.addActionListener(new BuyController(this.board));
        centerPanel.add(buyButton);
        buyHouseButton = new JButton();
        buyHouseButton.setText("Buy House");
        buyHouseButton.addActionListener(new BuyHouseController(this.board));
        buyHouseButton.setMnemonic('H'); //button can also be pressed by Alt+H
        centerPanel.add(buyHouseButton);
        updateBuyButtons();
        buyButton.setEnabled(false);

        jailButton = new JButton();
        jailButton.setText("Exit Jail");
        jailButton.setMnemonic('J'); //button can also be pressed by Alt+J
        jailButton.addActionListener(new ExitJailController(this.board));
        jailButton.setEnabled(false);
        centerPanel.add(jailButton);
        updateJailButton();

        movePlayerButton = new JButton();
        movePlayerButton.setText("Roll Dice");
        movePlayerButton.setMnemonic('R'); //button can also be pressed by Alt+J
        movePlayerButton.addActionListener(new MovePlayerController(this.board));
        centerPanel.add(movePlayerButton);
        movePlayerButton.setEnabled(true);

        endTurnButton = new JButton();
        endTurnButton.setText("End Turn");
        endTurnButton.setMnemonic('E');
        endTurnButton.addActionListener(new EndTurnController(this.board));
        centerPanel.add(endTurnButton);
        endTurnButton.setEnabled(false);
        gridCenterPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel saveButtonPanel = new JPanel(new FlowLayout());
        saveButton = new JButton();
        saveButton.setText("Save Game");
        saveButton.setMnemonic('S');
        saveButton.addActionListener(new SaveController(this.board));
        saveButtonPanel.add(saveButton);
        saveButton.setEnabled(true);
        gridCenterPanel.add(saveButtonPanel, BorderLayout.SOUTH);

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
        this.setTitle("g2-monopoly");
        this.setVisible(true);

        //the game needs to be started automatically if there are only AIs
        //if done in the constructor, the GUI can be properly updated as the game goes on due to how AWT works
        if (board.isAllRemainingAI()) {
            JOptionPane.showMessageDialog(this,
                    "All players are AI players. Press OK to begin the game.");
            board.getCurrentPlayer().getPlayerAI().doTurn();
            board.advanceTurn();
        }
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
            label.setText(String.format("%s: %c%d", player.getName(), this.board.getMoneySymbol(), player.getMoney()));
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
        DiceRoller dice = this.board.getDiceRoller();
        diceRollLabels[0].setText(String.valueOf(dice.getDice()[0]));
        diceRollLabels[1].setText(String.valueOf(dice.getDice()[1]));
        totalRollLabel.setText(String.valueOf(dice.getTotal()));
    }

    /**
     * Disables all action buttons so that the user cannot take actions.
     */
    private void disableAllButtons() {
        buyButton.setEnabled(false);
        buyHouseButton.setEnabled(false);
        endTurnButton.setEnabled(false);
        jailButton.setEnabled(false);
        movePlayerButton.setEnabled(false);
        saveButton.setEnabled(false);
    }

    /**
     * Updates the status of the buy button, enabling it or disabling it depending on whether
     * the current player is able to buy a property.
     */
    private void updateBuyButtons() {

        //enable/disable the buy house button
        Player currentPlayer = board.getCurrentPlayer();
        buyHouseButton.setEnabled(!currentPlayer.getBuildableProperties().isEmpty());

        //enable/disable the buy property button
        buyButton.setEnabled(true);
        Space currentSpace = board.getSpace(currentPlayer.getPosition());

        //if space is not a property space
        if (!(currentSpace instanceof PropertySpace)) {
            buyButton.setEnabled(false);
            return;
        }

        PropertySpace currentPropertySpace = (PropertySpace) currentSpace;
        Property currentProperty = currentPropertySpace.getProperty();

        buyButton.setEnabled(currentPlayer.canBuy(currentProperty));
    }

    /**
     * Enables or disables the "Exit Jail" button. The button is disabled if the current player
     * is not in jail or cannot afford to pay their way out.
     */
    private void updateJailButton() {
        Player player = board.getCurrentPlayer();
        jailButton.setEnabled(player.canExitJail());
    }

    /**
     * Updates the board display in response to a player moving.
     * This includes moving the player along the board spaces and updating information.
     * Runs on the EDT because it is a GUI update.
     * @param e MovePlayerEvent representing the movement
     */
    @Override
    public void handleMovePlayer(MovePlayerEvent e) {
        if (!SwingUtilities.isEventDispatchThread()) {
            try {
                SwingUtilities.invokeAndWait(() -> handleMovePlayer(e));
            } catch (InterruptedException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
        Player player = e.getPlayer();
        this.spacePanels[e.getOldPosition()].removePlayer(player);
        this.spacePanels[player.getPosition()].addPlayer(player);
        updateDiceLabels();
        if (player.isAI()) {
            return; //keep buttons disabled
        }
        updateBuyButtons();
        updateJailButton();
        endTurnButton.setEnabled(true);
        movePlayerButton.setEnabled(false);
        saveButton.setEnabled(false);
    }

    /**
     * Updates the board display in response to a player's money changing.
     * Runs on the EDT because it is a GUI update.
     * @param e The UpdateMoneyEvent representing the transaction
     */
    @Override
    public void handleUpdateMoney(UpdateMoneyEvent e) {
        if (!SwingUtilities.isEventDispatchThread()) {
            try {
                SwingUtilities.invokeAndWait(() -> handleUpdateMoney(e));
            } catch (InterruptedException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
        if (board.getCurrentPlayer().isAI()) {
            return; //keep buttons disabled
        }
        updatePlayerLabels();
        updateBuyButtons();
        updateJailButton();
    }

    /**
     * Updates the board display in response to a player going bankrupt.
     * Also ends the game if there is only one player left, showing a message with the winner's name.
     * Runs on the EDT because it is a GUI update.
     * @param e BankruptcyEvent representing the bankruptcy that occurred
     */
    @Override
    public void handleBankruptcy(BankruptcyEvent e) {
        if (!SwingUtilities.isEventDispatchThread()) {
            try {
                SwingUtilities.invokeAndWait(() -> handleBankruptcy(e));
            } catch (InterruptedException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
        updatePlayerLabels();
        for (SpacePanel panel: spacePanels) {
            panel.update();
        }
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

    /**
     * Updates the board display in response to a player buying a house.
     * Runs on the EDT because it is a GUI update.
     * @param e BuyHouseEvent representing the house purchase
     */
    @Override
    public void handleBuyHouse(BuyHouseEvent e) {
        if (!SwingUtilities.isEventDispatchThread()) {
            try {
                SwingUtilities.invokeAndWait(() -> handleBuyHouse(e));
            } catch (InterruptedException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }

        for (SpacePanel panel: spacePanels) {
            panel.update(); //maybe update only specific panels?
        }
        if (!board.getCurrentPlayer().isAI()) {
            updateBuyButtons();
        }
    }

    /**
     * Changes the GUI in response to a change in acting player.
     * Runs on the EDT because it is a GUI update.
     * @param e NewTurnEvent that represents the change in turn
     */
    @Override
    public void handleNewTurn(NewTurnEvent e) {
        if (!SwingUtilities.isEventDispatchThread()) {
            try {
                SwingUtilities.invokeAndWait(() -> handleNewTurn(e));
            } catch (InterruptedException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
        updatePlayerLabels();
        if (board.getCurrentPlayer().isAI()) {
            disableAllButtons(); //players cannot press buttons while the AI is acting
        } else {
            updateBuyButtons();
            buyButton.setEnabled(false);
            endTurnButton.setEnabled(false);
            movePlayerButton.setEnabled(true);
            jailButton.setEnabled(false);
            saveButton.setEnabled(true);
        }
    }

    /**
     * Updates the GUI in response to a player buying a property.
     * Runs on the EDT because it is a GUI update.
     * @param e BuyEvent that represents the change in turn
     */
    @Override
    public void handleBuy(BuyEvent e) {
        if (!SwingUtilities.isEventDispatchThread()) {
            try {
                SwingUtilities.invokeAndWait(() -> handleBuy(e));
            } catch (InterruptedException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
        for (SpacePanel panel: spacePanels) {
            panel.update(); //maybe update only specific panels?
        }
    }

    public static void main(String[] args) {
        boolean isInputValid = false;
        Board board = null;

        int reply = JOptionPane.showConfirmDialog(null, "Load a saved game?",
                "Start Game", JOptionPane.YES_NO_OPTION);

        if (reply == JOptionPane.YES_OPTION) {
            String filepath;
            LinkedList<String> validSlots = new LinkedList<>();
            for (String slot : BoardView.SAVE_LOCATIONS) {
                File saveFile = new File(slot);
                if (saveFile.exists()) {
                    validSlots.add(slot);
                }
            }
            if (validSlots.size() == 0) {
                JOptionPane.showMessageDialog(null, "There are no save files. Exiting.");
                System.exit(0);
            }
            String[] saveSlots = validSlots.toArray(new String[validSlots.size()]);
            filepath = (String) JOptionPane.showInputDialog(null, "Select a save slot",
                    "Save Game", JOptionPane.PLAIN_MESSAGE, null,
                    saveSlots, saveSlots[0]);
            if (filepath == null) {
                JOptionPane.showMessageDialog(null, "No save file was selected. Exiting.");
                System.exit(0);
            }
            try {
                board = Board.importBoard(filepath);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Could not load the game. Exiting.");
                System.exit(1);
            }

        } else {
            //don't load an old game
            //prompt user for the version to use
            InternationalVersion version = null;
            int playerCount = 0;
            int aiCount = 0;

            String[] versionNames = new String[InternationalVersion.values().length];
            for (int i = 0; i < InternationalVersion.values().length; i++) {
                versionNames[i] = InternationalVersion.values()[i].toString();
            }
            String selectedVersionName = (String) JOptionPane.showInputDialog(null,
                    "Select an international version",
                    "Select Version", JOptionPane.PLAIN_MESSAGE,null,
                    versionNames, versionNames[0]);
            if (selectedVersionName == null) {
                System.exit(0);
            }
            for (InternationalVersion internationalVersion : InternationalVersion.values()) {
                if (internationalVersion.toString().equals(selectedVersionName)) {
                    version = internationalVersion;
                }
            }

            //prompt user for number of total players
            isInputValid = false;
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
                                "The number of players must be between 2 and 8 (inclusive).");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,
                            String.format("%s is not a valid number of players.", playerCountString));
                }
            }

            //prompt user for number of AI players
            isInputValid = false;
            while (!isInputValid) {
                String playerCountString = JOptionPane.showInputDialog(
                        String.format("Please enter the number of AI players (up to %d)", playerCount));
                if (playerCountString == null) {
                    System.exit(0);
                }
                try {
                    aiCount = Integer.parseInt(playerCountString);
                    if (0 <= aiCount && aiCount <= playerCount) {
                        isInputValid = true;
                    } else {
                        JOptionPane.showMessageDialog(null,
                                String.format("The number of AIs must be between 0 and %d (inclusive).", playerCount));
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,
                            String.format("%s is not a valid number of AIs.", playerCountString));
                }
            }

            board = new Board(playerCount, aiCount, version);
        }
        new BoardView(board);
    }
}
