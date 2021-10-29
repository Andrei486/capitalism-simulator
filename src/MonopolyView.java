/**
 * Allows a class to be a view for the game.
 * @author Sebastian Lionais 101157892
 */
public interface MonopolyView extends GameEventListener{
    void handleMovePlayer(Board board, Player player, int oldPosition);
    void handleBuyProperty(Board board, Player player);
}
