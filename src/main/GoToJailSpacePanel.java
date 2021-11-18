package main;

import java.awt.*;

/**
 * @author Mohammad Alkhaledi 101162465
 * Implements the GoToJailSpace on the monopoly board GUI
 * Extends SpacePanel
 */
public class GoToJailSpacePanel extends SpacePanel{
    /**
     * Adds details to indicate that it is the GoToJailSpace
     */
    public GoToJailSpacePanel(){
        super();
        topLabel.setText("GO TO JAIL");
    }
}
