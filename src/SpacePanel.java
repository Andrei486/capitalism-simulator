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
    /**
     * Default constructor for Space Panel
     */
    public SpacePanel(){
        super(new BorderLayout());
        JPanel centerPanel = new JPanel(new GridLayout(2,4));

        topPanel = new JPanel();
        topPanel.setSize(new Dimension(200,200));
        topPanel.add(new JLabel(" "));

        bottomPanel = new JPanel();

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel,BorderLayout.CENTER);
        add(bottomPanel,BorderLayout.SOUTH);

        playerLabels = new JLabel[8];

        for(int i = 0; i < playerLabels.length; ++i){
            playerLabels[i] = new JLabel();
            playerLabels[i].setText(" ");
            centerPanel.add(playerLabels[i]);

        }

        setSize(200,200);
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
