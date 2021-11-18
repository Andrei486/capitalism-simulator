package main;

/**
 * @author Mohammad Alkhaledi 101162465
 * Implements a JailSpacePanel
 * Extends SpacePanel
 */
public class JailSpacePanel extends SpacePanel{
    /**
     * Adds text to panel to indicate it's jail
     */
    public JailSpacePanel(){
        super();
        topLabel.setText("JAIL");
    }
}
