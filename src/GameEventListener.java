/**
 * Allows a class to listen for events which happen in the game.
 * @author Sebastian Lionais 101157892
 */
public interface GameEventListener {
    public void handlePayRent(RentEvent e);
    public void handleBankruptcy(BankruptcyEvent e);
}
