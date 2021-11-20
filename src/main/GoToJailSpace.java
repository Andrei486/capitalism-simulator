package main;

/**
 * Creates a GoToJailSpace that sends players to jail when they land on it.
 * Extends Space class
 * @author Mohammad Alkhaledi 101162465
 */
public class GoToJailSpace extends Space{
    private JailSpace jail;
    /**
     * Constructor for an abstract Space with only a name.
     *
     * @param name the name that should be displayed on this space
     */
    public GoToJailSpace(String name, JailSpace jail) {
        super(name);
        this.jail = jail;
    }

    /**
     * Sends the player to jail.
     * @param p the Player landing on this space
     * TO DO Needs to throw JailEvent
     */
    @Override
    public void onEndTurn(Player p) {
        p.setJailTimer(Board.TURNS_IN_JAIL);
        p.setPosition(jail.getPosition());
    }

    /**
     * @return Description of the GoToJailSpace
     */
    @Override
    public String getDescription() {
        return "If you land on this space, you go to jail.";
    }
}
