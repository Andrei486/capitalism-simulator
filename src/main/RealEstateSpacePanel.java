package main;

import javax.swing.*;
import java.awt.*;

/**
 * Class for displaying a property space on the GUI.
 * @author Waleed Majbour
 * @author Andrei Popescu, 101143798
 */
public class RealEstateSpacePanel extends PropertySpacePanel{

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

        realEstate = (RealEstate) this.property;

        JPanel housesPanel = new JPanel();
        housesPanel.setLayout(new GridLayout(2, 2));
        topPanel.add(housesPanel, BorderLayout.WEST);
        housePanels = new JPanel[4];
        for (int i = 0; i < 4; i++) {
            housePanels[i] = new JPanel();
            housesPanel.add(housePanels[i]);
        }

        Color c = getHeaderColor(realEstate.getColorGroup());
        topPanel.setBackground(c);
        housesPanel.setBackground(c);

        update();
    }

    /**
     * Gets the color that the header should use when showing this panel.
     * @param cg the ColorGroup of the RealEstate property to represent
     * @return the Color to use when displaying the panel
     */
    private Color getHeaderColor(ColorGroup cg) {

        switch(cg) {
            case BROWN:
                return new Color(139,69,19);
            case LIGHT_BLUE:
                return Color.CYAN;
            case PINK:
                return Color.PINK;
            case ORANGE:
                return Color.ORANGE;
            case RED:
                return Color.RED;
            case YELLOW:
                return Color.YELLOW;
            case GREEN:
                return Color.GREEN;
            case BLUE:
                return Color.BLUE;
            default:
                return Color.BLACK;
        }
    }

    /**
     * Update this space panel in response to a house being bought
     * on this real estate property.
     */
    @Override
    public void update() {
        super.update();
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
