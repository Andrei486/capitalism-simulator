package main;

public class PlayerAI {

    private Board board;
    private Player player;

    public PlayerAI(Player player, Board board) {
        this.player = player;
        this.board = board;
    }

    public void doTurn() {

        board.movePlayer(player);
        if (player.canExitJail()) {
            player.loseMoney(Board.EXIT_JAIL_COST);
            player.setJailTimer(0);
            player.setPosition(player.getPosition());
        }

        Space currentSpace = board.getSpace(player.getPosition());
        if (currentSpace instanceof PropertySpace) {
            Property currentProperty = ((PropertySpace) currentSpace).getProperty();
            if (player.canBuy(currentProperty)) {
                player.buy(currentProperty);
            }
        }
        while (!player.getBuildableProperties().isEmpty()) {
            int highestHouseCost = -1;
            RealEstate mostValuable = null;
            for (RealEstate realEstate : player.getBuildableProperties()) {
                if (realEstate.getHouseCost() > highestHouseCost) {
                    highestHouseCost = realEstate.getHouseCost();
                    mostValuable = realEstate;
                }
            }
            player.buyHouse(mostValuable); //will never be null because all house costs are greater than -1
        }
    }

}
