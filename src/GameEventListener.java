/**
 * Allows a class to listen for events which happen in the game.
 * @author Sebastian Lionais 101157892
 */
public interface GameEventListener {
    void handlePayRent(RentEvent e);
    void handleBankruptcy(BankruptcyEvent e);
}
