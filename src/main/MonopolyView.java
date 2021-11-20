package main;

/**
 * Allows a class to be a view for the game.
 * @author Sebastian Lionais 101157892
 */
public interface MonopolyView {
    void handleMovePlayer(MovePlayerEvent e);
    void handleUpdateMoney(UpdateMoneyEvent e);
    void handleBankruptcy(BankruptcyEvent e);
    void handleBuyHouse(BuyHouseEvent e);
}
