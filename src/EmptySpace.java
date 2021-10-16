public class EmptySpace extends Space {

    /**
     * Creates a new empty space.
     * @param name
     */
    public EmptySpace(String name) {
        super(name);
    }

    /**
     * Resolves forced effects as a result of a given player landing on this space.
     * For empty spaces, there are no such effects.
     * @param p the Player landing on this space
     */
    @Override
    public void onEndTurn(Player p) {
        return;
    }

    /**
     * Gets a short description of this space.
     * @return String containing a description of the space
     */
    @Override
    public String getDescription() {
        return this.getName() + " (no property)";
    }
}
