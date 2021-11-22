package main;

import java.util.HashSet;

/**
 * The Player class represents a player on the board, storing all information such as name and money.
 * @author Sebastian Lionais s#101157892
 */
public class Player {

    private String name;
    private int money;
    private int jailTimer;
    private HashSet<Property> properties;
    private int position;
    private boolean isBankrupt;
    private Board board;
    private int playerNumber;
    private boolean isAI;
    private PlayerAI playerAI;
    private static int nextPlayerNumber = 0;
    private static final int STARTING_MONEY = 1500;

    /**
     * Constructs a player class object
     * @param name the name of the player
     * @param board the board the player is playing on
     */
    public Player(String name, Board board, boolean isAI) {
        this.name = name;
        this.board = board;
        this.money = STARTING_MONEY;
        this.position = 0;
        this.isBankrupt = false;
        this.properties = new HashSet<>();
        this.playerNumber = nextPlayerNumber;
        this.jailTimer = 0;
        nextPlayerNumber++;
        this.isAI = isAI;
        if (this.isAI) {
            this.playerAI = new PlayerAI(this, this.board);
        } else {
            this.playerAI = null;
        }
    }

    public Player(String name, Board board) {
        this(name, board, false);
    }

    /**
     * Sets the owner of the property to the current player after subtracting the cost of the property from the player.
     * Assumption: The method is only called if the player can buy the property when called
     * @param property the property that is being bought
     */
    public void buy (Property property) {
        property.setOwner(this);
        this.properties.add(property);
        this.loseMoney(property.getCost());
        board.handleBuyEvent(new BuyEvent(this, property));
    }

    public boolean canBuy(Property property) {
        return (property.getOwner() == null && money > property.getCost());
    }

    public boolean canExitJail() {
        return (jailTimer > 0 && jailTimer < Board.TURNS_IN_JAIL && money > Board.EXIT_JAIL_COST);
    }

    /**
     * Buys a house on the given RealEstate, subtracting the cost from the player's money.
     * Assumes that this is a valid operation: that is, the player owns the property and can afford the house.
     * @param realEstate the RealEstate to build a house on
     */
    public void buyHouse(RealEstate realEstate) {
        this.loseMoney(realEstate.getHouseCost());
        realEstate.addHouse();
        board.handleBuyHouseEvent(new BuyHouseEvent(this, realEstate));
    }

    /**
     * Pays the rent of the property to the player the owns it, if this player lacks the money to do so it gives
     * the other player the rest of their money. In both cases a rent event is created and handled by board.
     * @param property the property that determines the rent and owner
     */
    public void payRent(Property property) {
        int amountPaid = this.loseMoney(property.getRent());
        property.getOwner().gainMoney(amountPaid);
    }

    /**
     * Gets the name of the player.
     * @return a string of its name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the amount of money a player has.
     * @return an integer value equal to the money the player owns
     */
    public int getMoney() {
        return this.money;
    }

    /**
     * Gets a unique value that identifies the player.
     * @return the integer value which identifies the player
     */
    public int getPlayerNumber() {return this.playerNumber;}

    /**
     * Gets the set of properties the player owns.
     * @return a hashset of the properties the player owns
     */
    public HashSet<Property> getProperties() {
        return properties;
    }

    /**
     * Takes money from the player, bankrupts the player if they do not have the money to pay.
     * The player cannot pay more money than would cause them to go below 0$.
     * @param money the money to be paid
     * @return integer value representing the actual money paid
     */
    public int loseMoney(int money) {
        if (money < this.money) {
            this.money -= money;
            board.handleUpdateMoneyEvent(new UpdateMoneyEvent(this));
            return money; //the full amount was paid
        }
        else{
            int amountPaid = this.money;
            this.money = 0;
            board.handleUpdateMoneyEvent(new UpdateMoneyEvent(this));
            this.bankrupt();
            return amountPaid; //all the player's remaining money was paid
        }
    }

    /**
     * Gives money to the player.
     * @param money the money to be gained
     */
    public void gainMoney(int money) {
        this.money += money;
        board.handleUpdateMoneyEvent(new UpdateMoneyEvent(this));
    }

    /**
     * Gets the position of the player.
     * @return an integer that represents the position of the player
     */
    public int getPosition() {
        return position;
    }

    /**
     * Set the position of the player.
     * @param position an integer that represents the position of the player
     */
    public void setPosition(int position) {
        int oldPosition = this.position;
        this.position = position;
        board.handleMovePlayerEvent(new MovePlayerEvent(board,this,oldPosition));
    }

    /**
     * Returns all properties to the bank and sets isBankrupt to true and gets board to handle
     * a BankruptcyEvent.
     */
    public void bankrupt() {
        for (Property property: this.properties) {
            property.setOwner(null);
        }
        this.properties.clear();
        this.isBankrupt = true;
        board.handleBankruptcyEvent(new BankruptcyEvent(this));
    }

    /**
     * Gets if a player is bankrupt.
     * @return true if the player is bankrupt and false otherwise
     */
    public boolean getIsBankrupt() {
        return this.isBankrupt;
    }

    /**
     * @return returns turns left in prison
     */
    public int getJailTimer() {
        return jailTimer;
    }

    /**
     * @param jailTimer number of turns to be spent in jail
     */
    public void setJailTimer(int jailTimer) {
        this.jailTimer = jailTimer;
    }

    /**
     * Gets all the player's real estate properties on which buying a house is currently allowed.
     * @return HashSet of RealEstates on which a house can be bought.
     */
    public HashSet<RealEstate> getBuildableProperties() {
        HashSet<RealEstate> houseBuyProperties = new HashSet<>();
        for (Property property : this.properties) {
            if (property instanceof RealEstate) {
                RealEstate realEstate = (RealEstate) property;
                if (realEstate.canAddHouse()) {
                    houseBuyProperties.add(realEstate);
                }
            }
        }
        return houseBuyProperties;
    }

    public boolean isAI() {
        return isAI;
    }

    public PlayerAI getPlayerAI() {
        return playerAI;
    }
}
