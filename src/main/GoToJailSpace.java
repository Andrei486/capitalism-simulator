package main;

/**
 * Creates a GoToJailSpace that sends players to jail when they land on it.
 * Extends Space class
 * @author Mohammad Alkhaledi 101162465
 */
public class GoToJailSpace extends Space{
    private JailSpace jail;
    private static final int TURNS_IN_JAIL = 3;
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
     */
    @Override
    public void onEndTurn(Player p) {
        p.setJailTimer(TURNS_IN_JAIL);
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
