package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for displaying a property space on the GUI.
 * @author Waleed Majbour
 * @author Andrei Popescu, 101143798
 */
public class RealEstateSpacePanel extends SpacePanel{

    private RealEstate realEstate;
    private JPanel[] housePanels;
    private static final Color HOTEL_COLOR = new Color(179, 12, 0);
    private static final Color HOUSE_COLOR = new Color(0, 110, 9);

    /**
     * Constructs a panel for a given real estate property space.
     * @param space the property to make a GUI panel for
     */
    public RealEstateSpacePanel(PropertySpace space) {

        super(space);

        realEstate = (RealEstate) (space.getProperty());
        bottomLabel.setText(String.format("%s", realEstate.getName()));

        Color c;
        ColorGroup cg = realEstate.getColorGroup();

        if(cg == ColorGroup.BROWN){
            c = new Color(139,69,19);
            topPanel.setBackground(c);
        }
        else if(cg == ColorGroup.LIGHT_BLUE){
            topPanel.setBackground(Color.CYAN);
        }
        else if(cg == ColorGroup.PINK) {
            topPanel.setBackground(Color.PINK);
        }
        else if(cg == ColorGroup.ORANGE){
            topPanel.setBackground(Color.ORANGE);
        }
        else if(cg == ColorGroup.RED){
            topPanel.setBackground(Color.RED);
        }
        else if(cg == ColorGroup.YELLOW){
            topPanel.setBackground(Color.YELLOW);
        }
        else if(cg == ColorGroup.GREEN){
            topPanel.setBackground(Color.green);
        }
        else if(cg == ColorGroup.BLUE){
            topPanel.setBackground(Color.BLUE);
        }

        JPanel housesPanel = new JPanel();
        housesPanel.setLayout(new GridLayout(2, 2));
        topPanel.add(housesPanel, BorderLayout.WEST);
        housePanels = new JPanel[4];
        for (int i = 0; i < 4; i++) {
            housePanels[i] = new JPanel();
            housesPanel.add(housePanels[i]);
        }
        update();
    }

    /**
     * Update this space panel in response to a house being bought
     * on this real estate property.
     */
    @Override
    public void update() {
        int houses = realEstate.getHouses();
        Color color = (houses == 5) ? HOTEL_COLOR : HOUSE_COLOR;
        for (int i = 0; i < 4; i++) {
            if (houses > i) {
                housePanels[i].setBackground(color);
                housePanels[i].setOpaque(true);
            } else {
                housePanels[i].setOpaque(false);
            }
        }
    }
}
