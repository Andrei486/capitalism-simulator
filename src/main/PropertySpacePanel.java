package main;

import java.awt.*;

/**
 * Panel representing a space with a Property on it in the GUI.
 * Provides functionality related to owning the property.
 */
public class PropertySpacePanel extends SpacePanel {

    protected Property property;

    /**
     * Default constructor for PropertySpacePanel
     * @param space
     */
    public PropertySpacePanel(PropertySpace space) {
        super(space);

        this.property = space.getProperty();
        this.bottomLabel.setText(String.format("%s", this.property.getName()));
        this.topPanel.setBackground(Color.DARK_GRAY);
    }

    /**
     * Update this panel, potentially changing the owner name displayed.
     */
    @Override
    public void update() {
        super.update();
        //show the owner's name in a color that stands out against the background
        Color bg = this.topPanel.getBackground();
        int brightness = (bg.getBlue() + bg.getGreen() + bg.getRed()) / 3;
        Color fg = (brightness > 127) ? Color.BLACK : Color.WHITE;
        if (property.getOwner() != null) {
            this.topLabel.setForeground(fg);
            this.topLabel.setText(property.getOwner().getName());
        } else {
            this.topLabel.setText(" ");
        }
    }
}
