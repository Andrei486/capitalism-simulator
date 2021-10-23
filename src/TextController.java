import java.util.HashSet;

public class TextController implements GameEventListener {
    private Board board;

    public TextController(Board board){
        this.board = board;
    }

    @Override
    public void handlePayRent(RentEvent e) {
        Player currentPlayer = (Player) e.getSource();
        Property currentProperty = e.getProperty();

        System.out.println(currentPlayer.getName() + " paid $" + e.getRentPaid() +
                 " in rent to " + currentProperty.getOwner() + ".");

    }

    @Override
    public void handleBankruptcy(BankruptcyEvent e) {
        Player currentPlayer = board.getCurrentPlayer();
        System.out.println(currentPlayer.getName() + "went bankrupt.");
    }


    /**
     * @param command a valid command
     * @author Mohammad Alkhaledi
     * Receives a command string, and performs appropriate action
     * list of commands: help, buy, show, show [player name], properties,property [property name], end, quit
     */
    public void parseCommand(String command) {
        String[] splitCommand = command.split("\\s");
        command = splitCommand[0];
        String secondWord = null;
        if (splitCommand.length > 1) {
            secondWord = splitCommand[1];
        }
        switch (command) {
            case "help":
                printHelp();
                break;

            case "buy":
                Player currentPlayer = board.getCurrentPlayer();
                Space currentSpace1 = board.getSpace(currentPlayer.getPosition());

                if (!(currentSpace1 instanceof PropertySpace)) {
                    System.out.println("You are not on a property. You cannot buy right now.");
                    break;
                }

                PropertySpace currentPropertySpace = (PropertySpace) currentSpace1;
                Property currentProperty = currentPropertySpace.getProperty();

                if (!(currentProperty.getOwner() == null)) {
                    System.out.println("This property is owned already. You cannot buy it again.");
                } else {
                    currentPlayer.buy(currentProperty);
                    System.out.println("You pay $" + currentProperty
                            .getCost() + ", and now own " + currentProperty.getName() + "Congratulation!");
                    System.out.println("You now have $" + currentPlayer.getMoney() + " remaining.");
                }

                break;

            case "show":
                //print a player's stats
                if (secondWord != null) {
                    //get the player corresponding to player name, then print stats
                    Player[] players = board.getPlayers();
                    Player player1 = null;
                    for (Player p : players) {
                        if (secondWord.equals(p.getName())) {
                            player1 = p;
                        }
                    }
                    if (player1 == null) {
                        System.out.println("There is no player named " + secondWord);
                        break;
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

                //show: print all player's names
                else {
                    System.out.println("Print names:");
                    Player[] players = board.getPlayers();
                    for (Player p : players) {
                        System.out.println(p.getName());
                    }
                }

                break;

            case "properties":
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

                break;


            case "property":
                Property[] properties1 = board.getProperties();
                Property thisProperty = null;

                if (secondWord != null) {
                    for (Property p : properties1) {
                        if (secondWord.equals(p.getName())) {
                            thisProperty = p;
                        }
                    }
                    if (thisProperty == null) {
                        System.out.println("there is no property named " + secondWord);
                        break;
                    }
                    System.out.println(thisProperty);

                } else {
                    System.out.println("You need to put in a property name.");
                    break;
                }

                break;
            case "end":
                System.out.println("Ending turn.");
                board.advanceTurn();

                Player currentPlayer2 = board.getCurrentPlayer();
                Space currentSpace2 = board.getSpace(currentPlayer2.getPosition());
                board.movePlayer(currentPlayer2);
                System.out.println("- " + currentPlayer2.getName() + "'s turn! -");
                System.out.println("Current money: $" + currentPlayer2.getMoney());
                System.out.println("Rolling dice to move: rolled a total of " + board.getDiceRoller().getTotal());
                if(board.getDiceRoller().isDouble()){
                    System.out.println("Rolled doubles!");
                }
                if(!(currentSpace2 instanceof PropertySpace)){
                    System.out.println("Moved to empty space.");
                }
                else{
                    PropertySpace currentPropertySpace2 = (PropertySpace)currentSpace2;
                    Property currentProperty2 = currentPropertySpace2.getProperty();
                    System.out.println("Moved to space\n" + currentProperty2);
                }
                System.out.println("What do you want to do?\nEnter a command, or help for a command list" +
                        "\n>");
                break;
            case "quit":
                System.out.println("Quiting game.");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid command. Type help for a command list");
                break;
        }
    }

    public static void main(String[] args) {


    }

}