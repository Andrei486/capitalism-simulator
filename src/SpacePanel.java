import javax.swing.*;
import java.awt.*;

/**
 * @author Mohammad ALkhaledi 101162465
 * implements Space panel, which is a template for any space on the board
 */
public abstract class SpacePanel extends JPanel {
    private JLabel[] playerLabels;
    protected JPanel topPanel;
    protected JPanel bottomPanel;
    protected JLabel topLabel;
    protected JLabel bottomLabel;
    /**
     * Default constructor for Space Panel
     */
    public SpacePanel(){
        super(new BorderLayout());
        JPanel centerPanel = new JPanel(new GridLayout(2,4));
        centerPanel.setBackground(Color.LIGHT_GRAY);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        topPanel = new JPanel();
        topLabel = new JLabel(" ");
        topLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
        topPanel.add(topLabel);

        bottomPanel = new JPanel();
        bottomLabel = new JLabel(" ");
        bottomLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        bottomPanel.add(bottomLabel);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel,BorderLayout.CENTER);
        add(bottomPanel,BorderLayout.SOUTH);

        playerLabels = new JLabel[8];

        for(int i = 0; i < playerLabels.length; ++i){
            playerLabels[i] = new JLabel();
            playerLabels[i].setText(" ");
            centerPanel.add(playerLabels[i]);
        }

        this.setPreferredSize(new Dimension(110, 110));
    }

    /**
     * Adds player ID text to panel
     * @param p player whose ID is to be added
     */
    public void addPlayer(Player p){
        int playerNumber = p.getPlayerNumber();
        playerLabels[playerNumber].setText(p.getName());
    }

    /**
     * Removes player ID text from panel
     * @param p player whose ID is to be removed
     */
    public void removePlayer(Player p){
        int playerNumber = p.getPlayerNumber();
        playerLabels[playerNumber].setText(" ");
    }
}
