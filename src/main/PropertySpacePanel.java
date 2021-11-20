package main;

import java.awt.*;

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
}
