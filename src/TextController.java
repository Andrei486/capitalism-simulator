import java.util.HashSet;

public class TextController implements GameEventListener {
    private Board board;

    /**Default constructor for TextController
     * @param board the board to be used for the monopoly game
     * */
    public TextController(Board board){
        this.board = board;
    }

    /**Prints statement that displays rent being paid
     * @param e the rent event that occurred
     * */
    @Override
    public void handlePayRent(RentEvent e) {
        Player currentPlayer = (Player) e.getSource();
        Property currentProperty = e.getProperty();

        System.out.println(currentPlayer.getName() + " paid $" + e.getRentPaid() +
                 " in rent to " + currentProperty.getOwner() + ".");

    }

    /**Prints statement that displays player being bankrupted
     * @param e the bankruptcy event that occurred
     * */
    @Override
    public void handleBankruptcy(BankruptcyEvent e) {
        Player currentPlayer = (Player) e.getSource();
        System.out.println(currentPlayer.getName() + "went bankrupt.");
    }

    /**Prints list of valid commands
     */
    public void printHelp(){
        System.out.println("help: shows list of commands");
        System.out.println("end: advances turn & moves next player");
        System.out.println("buy: buys property on current space if possible");
        System.out.println("show: prints all player names");
        System.out.println("show playerName: shows what space the player is on, how much money they have" +
                ", and all their properties");
        System.out.println("properties: shows current player's properties");
        System.out.println("property propertyName: shows detailed description of property");
        System.out.println("quit: quits the game and application");
    }

    /**On success: if on a property space, property is not owned, and player has enough money: Buys property and
     * prints success statement
     * fails: space is not a property space, player is not rich enough, or property has an owner: prints appropriate
     * error statement
     */
    public void buy(){
        Player currentPlayer = board.getCurrentPlayer();
        Space currentSpace1 = board.getSpace(currentPlayer.getPosition());

        //if space is not a property space
        if (!(currentSpace1 instanceof PropertySpace)) {
            System.out.println("You are not on a property. You cannot buy right now.");
            return;
        }

        PropertySpace currentPropertySpace = (PropertySpace) currentSpace1;
        Property currentProperty = currentPropertySpace.getProperty();

        // if property is owned
        if (!(currentProperty.getOwner() == null)) {
            System.out.println("This property is owned already. You cannot buy it again.");
        }
        //if player does not have more than the cost of the property
        else if(currentPlayer.getMoney() <= currentProperty.getCost()) {
            System.out.println("You can't afford " + currentPlayer.getName() +
                    ", it costs $" + currentProperty.getCost() + ", but you only have" +
                    "$" + currentPlayer.getMoney());
        }
        //player has enough money to buy property and the property is not owned
        else {
            currentPlayer.buy(currentProperty);
            System.out.println("You pay $" + currentProperty
                    .getCost() + ", and now own " + currentProperty.getName() + "Congratulation!");
            System.out.println("You now have $" + currentPlayer.getMoney() + " remaining.");
        }
    }

    /**Prints name of current players
     */
    public void showPlayers(){
        System.out.println("Print names:");
        Player[] players = board.getPlayers();
        for (Player p : players) {
            System.out.println(p.getName());
        }
    }

    /**On success: if player name exists. prints the statistics of a player given their name string
     * fails: if player name does not exist. Prints error statement
     * @param playerName name of player inputted
     */
    public void showPlayerStats(String playerName){
        Player[] players = board.getPlayers();
        Player player1 = null;
        for (Player p : players) {
            if (playerName.equals(p.getName())) {
                player1 = p;
            }
        }
        if (player1 == null) {
            System.out.println("There is no player named " + playerName);
            return;
        }
        System.out.println("Player name: " + player1.getName());
        System.out.println("Money: $" + player1.getMoney());
        System.out.print("Properties: ");
        HashSet<Property> properties = player1.getProperties();
        int propertySize = properties.size();
        int counter = 0;

        for (Property p : properties) {
            if (counter < propertySize) {
                System.out.print(p.getName() + ", ");
            } else {
                System.out.print(p.getName() + "\n");
            }
            ++counter;
        }
    }

    /**Prints properties of the current player. Names only.
     */
    public void showProperties(){
        System.out.print("Properties: ");
        HashSet<Property> properties = board.getCurrentPlayer().getProperties();
        int propertySize = properties.size();
        int counter = 0;

        for (Property p : properties) {
            if (counter < propertySize) {
                System.out.print(p.getName() + ", ");
            } else {
                System.out.print(p.getName() + "\n");
            }
            ++counter;
        }
    }

    /**On success: if property name exists. Prints the statistics of a property given a string for its name
     * fails: if the name of the property does not exist. Prints error statement.
     * @param propertyName name of property
     */
    public void showPropertyStats(String propertyName){
        Property[] properties1 = board.getProperties();
        Property thisProperty = null;

        if (propertyName != null) {
            for (Property p : properties1) {
                if (propertyName.equals(p.getName())) {
                    thisProperty = p;
                }
            }
            if (thisProperty == null) {
                System.out.println("there is no property named " + propertyName);
                return;
            }
            System.out.println(thisProperty);

        } else {
            System.out.println("You need to put in a property name.");

        }
    }

    /**
     * Starts the next turn for the monopoly game.
     * This function is called at the start of monopoly game, and at the end of a turn.
     */
    public void startTurn(){
        Player currentPlayer2 = board.getCurrentPlayer();
        board.movePlayer(currentPlayer2);
        Space currentSpace2 = board.getSpace(currentPlayer2.getPosition());

        System.out.println("- " + currentPlayer2.getName() + "'s turn! -");
        System.out.println("Current money: $" + currentPlayer2.getMoney());
        System.out.println("Rolling dice to move: rolled a total of " + board.getDiceRoller().getTotal());
        if(board.getDiceRoller().isDouble()){
            System.out.println("Rolled doubles!");
        }
        System.out.println("Moved to ");
        System.out.println("\n" + currentSpace2.getDescription());

        System.out.println("What do you want to do?\nEnter a command, or help for a command list" +
                "\n>");
    }

    /**
     * @param command a valid command
     * @author Mohammad Alkhaledi
     * Receives a command string, and performs appropriate action
     * list of commands: help, buy, show, show [player name], properties,property [property name], end, quit
     *
     */
    public boolean parseCommand(String command) {
        String[] splitCommand = command.split("\\s");
        command = splitCommand[0];
        String secondWord = null;
        if (splitCommand.length > 1) {
            secondWord = splitCommand[1];
        }
        switch (command) {
            case "help":
                printHelp();
                return false;

            case "buy":
                buy();
                return false;

            case "show":
                //print a player's stats
                if (secondWord != null) {
                    //get the player corresponding to player name, then print stats
                    showPlayerStats(secondWord);
                }
                //show: print all player's names
                else {
                    showPlayers();
                }
                return false;

            case "properties":
                showProperties();
                return false;

            case "property":
                showPropertyStats(secondWord);
                return false;

            case "end":
                System.out.println("Ending turn.");
                board.advanceTurn();
                startTurn();
                return true;

            case "quit":
                System.out.println("Quiting game.");
                System.exit(0);
                return false;
            default:
                System.out.println("Invalid command. Type help for a command list");
                return false;
        }

    }

    public static void main(String[] args) {


    }

}