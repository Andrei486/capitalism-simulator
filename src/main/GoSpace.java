package main;

/**
 * Class representing a GO space on the board, which gives players money when they pass over it.
 * @author Sebastian Lionais
 */
public class GoSpace extends Space {

    /**
     * Constructor for an abstract Space with only a name.
     *
     * @param name the name that should be displayed on this space
     */
    public GoSpace(String name) {
        super(name);
    }

    /**
     * Gives the player $200 when passing this Space
     * @param p the Player landing on this space
     */
    @Override
    public void onPassThrough(Player p){
        p.gainMoney(100);
    }

    /**
     * Gets a short description of this space.
     * @return String containing a description of the space
     */
    @Override
    public String getDescription() {
        return "GO Space";
    }
}
