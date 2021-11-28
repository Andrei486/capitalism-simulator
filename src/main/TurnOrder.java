package main;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * A class that manages the turn order of a Monopoly game.
 */
public class TurnOrder implements Serializable {
    private Player currentPlayer;
    private Queue<Player> nextTurns;

    /**
     * Constructs a turn order given the players that are part of the game.
     * @param players array of all the players that should be included in the turn order
     */
    public TurnOrder(Player[] players) {
        this.nextTurns = new LinkedList<>(List.of(players));
        this.currentPlayer = this.nextTurns.remove();
    }

    /**
     * Gets the current acting player.
     * @return the Player that is currently acting in the turn order
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Advances the turn order to the next player. If the current player rolled doubles, they
     * act again instead.
     * In either case, any bankrupt players are automatically skipped.
     * @param isDouble true if the current player rolled doubles, false otherwise
     * @return true if the same player is moving again, false otherwise
     */
    public boolean advanceTurnOrder(boolean isDouble) {
        boolean samePlayer = true;
        if (currentPlayer.getIsBankrupt()) {
            currentPlayer = nextTurns.remove();
            samePlayer = false;
        }

        if (!isDouble || currentPlayer.getJailTimer() > 0) {
            nextTurns.add(currentPlayer);
            currentPlayer = nextTurns.remove();
            samePlayer = false;
        }

        //remove any bankrupt players from the turn order without giving them a turn
        while (currentPlayer.getIsBankrupt()) {
            currentPlayer = nextTurns.remove();
            samePlayer = false;
        }

        return samePlayer;
    }
}
