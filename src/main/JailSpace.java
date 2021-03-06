package main;

/**
 * Creates a JailSpace that houses people who end up in jail.
 * Extends the Space class.
 * @author Mohammad Alkhaledi 101162465
 */
public class JailSpace extends Space{
    private final int position;
    /**
     * Constructor for the JailSpace. Initializes variables.
     *
     * @param name the name that should be displayed on this space
     * @param position position of the
     */
    public JailSpace(String name, int position) {
        super(name);
        this.position = position;
    }

    /**
     * If player is in jail:
     * Checks whether or not player should leave jail
     * If player is passing by:
     * do nothing
     * @param p the Player currently on this space
     */
    @Override
    public void onEndTurn(Player p) {
        if(p.getJailTimer() > 0){
            p.setJailTimer(p.getJailTimer()-1);

            if(p.getJailTimer() == 0){
                p.loseMoney(Board.EXIT_JAIL_COST);
            }
        }
    }

    /**
     * Gets the position of this space on the Board.
     * @return the position of the JailSpace
     */
    public int getPosition() {
        return position;
    }

    /**
     * Gets a text description of this JailSpace.
     * @return description of the JailSpace
     */
    @Override
    public String getDescription() {
        return "Jail is where criminals are sent.";
    }
}
