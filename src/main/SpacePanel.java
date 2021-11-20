package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    public SpacePanel(Space space){
        super(new BorderLayout());
        JPanel centerPanel = new JPanel(new GridLayout(2,4));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        topPanel = new JPanel();
        topLabel = new JLabel(" ");
        topLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        topPanel.setLayout(new BorderLayout());
        topPanel.add(topLabel, BorderLayout.CENTER);

        bottomPanel = new JPanel();
        bottomLabel = new JLabel(" ");
        bottomLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9));
        bottomPanel.add(bottomLabel);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel,BorderLayout.CENTER);
        add(bottomPanel,BorderLayout.SOUTH);

        JButton infoButton = new JButton("i");
        infoButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        topPanel.add(infoButton, BorderLayout.EAST);

        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, space.getDescription());
            }
        });

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
        if (p.getJailTimer() == 0) {
            playerLabels[playerNumber].setBackground(Color.BLACK);
        } else {
            playerLabels[playerNumber].setBackground(Color.RED);
        }
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

    /**
     * Update this space panel.
     */
    public void update() {

    }
}
