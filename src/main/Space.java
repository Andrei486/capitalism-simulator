package main;

import java.io.Serializable;

/**
 * Class representing a space on a Monopoly board.
 * Base class for all different space types.
 * @author Andrei Popescu, 101143798
 */
public abstract class Space implements Serializable {
    private String name;

    /**
     * Constructor for an abstract Space with only a name.
     * @param name the name that should be displayed on this space
     */
    public Space(String name) {
        this.name = name;
    }

    /**
     * Gets the name of this space.
     * @return String containing the name of this space
     */
    public String getName() {
        return this.name;
    }

    /**
     * Resolves forced effects as a result of a given player landing on this space.
     * @param p the Player landing on this space
     */
    public void onEndTurn(Player p) {
        return;
    }

    /**
     * Resolves forced effects as a result of a given player passing over this space.
     * @param p the Player landing on this space
     */
    public void onPassThrough(Player p){return;}

    /**
     * Gets a short description of this space.
     * @return String containing a description of the space
     */
    public abstract String getDescription();
}
