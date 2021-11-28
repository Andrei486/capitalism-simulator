package main;

import java.io.Serializable;

/**
 * AI class to control a player.
 * @author Andrei Popescu
 * @author Sebastian Lionais
 * @author Mohammed Alkhaledi
 * @author Waleed Majbour
 */
public class PlayerAI implements Serializable {

    private Board board;
    private Player player;

    /**
     * Constructs an AI to control a given Player on a Board.
     * @param player the Player to control
     * @param board the Board that the player is on
     */
    public PlayerAI(Player player, Board board) {
        this.player = player;
        this.board = board;
    }

    /**
     * Controls the player for one turn. Does not advance the board turn at the end.
     */
    public void doTurn() {

        //move the player first
        board.movePlayer(player);
        //exit jail if possible to avoid drawing out the game
        if (player.canExitJail()) {
            player.loseMoney(Board.EXIT_JAIL_COST);
            player.setJailTimer(0);
            player.setPosition(player.getPosition());
        }

        //buy a property if possible
        Space currentSpace = board.getSpace(player.getPosition());
        if (currentSpace instanceof PropertySpace) {
            Property currentProperty = ((PropertySpace) currentSpace).getProperty();
            if (player.canBuy(currentProperty)) {
                player.buy(currentProperty);
            }
        }
        //buy all houses possible
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
