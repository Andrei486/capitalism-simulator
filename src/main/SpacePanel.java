package main;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mohammad ALkhaledi 101162465
 * implements SpacePanel, which is a template for any space on the board
 */
public abstract class SpacePanel extends JPanel {
    private JLabel[] playerLabels;
    protected JPanel topPanel;
    protected JPanel bottomPanel;
    protected JLabel topLabel;
    protected JLabel bottomLabel;
    /**
     * Default constructor for SpacePanel
     */
    public SpacePanel(){
        super(new BorderLayout());
        JPanel centerPanel = new JPanel(new GridLayout(2,4));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        topPanel = new JPanel();
        topLabel = new JLabel(" ");
        topLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        topPanel.add(topLabel);

        bottomPanel = new JPanel();
        bottomLabel = new JLabel(" ");
        bottomLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9));
        bottomPanel.add(bottomLabel);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel,BorderLayout.CENTER);
        add(bottomPanel,BorderLayout.SOUTH);

        playerLabels = new JLabel[8];

        for(int i = 0; i < playerLabels.length; ++i){
            playerLabels[i] = new JLabel();
            playerLabels[i].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 9));
            playerLabels[i].setText(" ");
            playerLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            playerLabels[i].setForeground(Color.WHITE);
            playerLabels[i].setBackground(Color.BLACK);
            centerPanel.add(playerLabels[i]);
        }

        this.setPreferredSize(new Dimension(100, 70));
    }

    /**
     * Adds player ID text to panel
     * @param p player whose ID is to be added
     */
    public void addPlayer(Player p){
        int playerNumber = p.getPlayerNumber();
        playerLabels[playerNumber].setText(p.getName());
        playerLabels[playerNumber].setOpaque(true);
    }

    /**
     * Removes player ID text from panel
     * @param p player whose ID is to be removed
     */
    public void removePlayer(Player p){
        int playerNumber = p.getPlayerNumber();
        playerLabels[playerNumber].setText(" ");
        playerLabels[playerNumber].setOpaque(false);
    }
}
