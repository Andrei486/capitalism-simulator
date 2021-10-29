public interface MonopolyView extends GameEventListener{
    void handleMovePlayer(Board board, Player player, int oldPosition);
    void handleBuyProperty(Board board, Player player);
}
