import java.util.HashSet;

/**
 * @author Sebastian Lionais s#101157892
 */
public class Player {

    private String name;
    private int money;
    private HashSet<Property> properties;
    private int position;
    private boolean isBankrupt;
    private static int STARTING_MONEY = 1500;

    /**
     * Constructs a player class object
     * @param name the name of the player
     */
    public Player (String name) {
        this.name = name;
        this.money = STARTING_MONEY;
        this.position = 0;
        this.isBankrupt = false;
        this.properties = new HashSet<>();
    }

    /**
     * Sets the owner of the property to the current player after subtracting the cost of the property from the player.
     * Assumption: The method is only called if the player can buy the property when called
     * @param property the property that is being bought
     */
    public void buy (Property property) {
        this.loseMoney(property.getRent());
        property.setOwner(this);
        this.properties.add(property);
    }

    /**
     * Pays the rent of the property to the player the owns it, if this player lacks the money to do so
     * @param property the property that determines the rent and owner
     */
    public void payRent(Property property) {
        int rent = property.getRent();
        if (this.loseMoney(rent)) {
            property.getOwner().gainMoney(rent);
        }
        else {
            property.getOwner().gainMoney(this.money);
        }
    }

    /**
     * Takes money from the player, bankrupts the player if they do not have the money to pay.
     * @param money the money to be paid
     * @return true if the player could pay, false if they bankrupted
     */
    public boolean loseMoney(int money) {
        if (money < this.money) {
            this.money -= money;
            return true;
        }
        else{
            this.bankrupt();
            return false;
        }
    }

    /**
     * Gives money to the player.
     * @param money the money to be gained
     */
    public void gainMoney(int money) {
        this.money += money;
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
        this.position = position;
    }

    /**
     * Returns all properties to the bank and sets isBankrupt to true
     */
    public void bankrupt() {
        for (Property property: this.properties) {
            property.setOwner(null);
        }
        this.properties.clear();
        this.isBankrupt = true;
    }

    /**
     * Gets if a player is bankrupt.
     * @return true if the player is bankrupt and false otherwise
     */
    public boolean getIsBankrupt() {
        return this.isBankrupt;
    }
}
