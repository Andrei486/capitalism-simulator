package main;

/**
 * Panel representing a GoSpace in the GUI.
 * @author Sebastian Lionais
 */
public class GoSpacePanel extends SpacePanel{

    /**
     * Constructs a new GoSpacePanel to represent a space.
     * @param space GoSpace to represent
     */
    public GoSpacePanel(GoSpace space) {
        super(space);
        bottomLabel.setText("GO");
    }
}
