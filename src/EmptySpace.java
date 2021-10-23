/**
 * Class representing an empty space on the board, such as Free Parking.
 * When the player lands on one of these spaces, nothing happens.
 * @author Andrei Popescu, 101143798
 */
public class EmptySpace extends Space {

    /**
     * Creates a new empty space.
     */
    public EmptySpace() {
        super("");
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
        return "Empty space";
    }
}
