public interface GameEventListener {
    public void handlePayRent(RentEvent e);
    public void handleBankruptcy(BankruptcyEvent e);
}
