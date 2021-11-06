import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that interfaces between BoardView and Board to handle
 * buying properties.
 */
public class BuyController implements ActionListener {
    private Board board;

    /**
     * Constructs EndTurnController object.
     * @param board the board the EndTurnController is connecting to
     */
    public BuyController(Board board) {
        this.board = board;
    }

    /**
     * This method when activated this advances the turn order.
     * @param e is the event which activated the method
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Player currentPlayer = board.getCurrentPlayer();
        //if the button is enabled, the player is on a property space
        PropertySpace currentSpace = (PropertySpace) board.getSpace(currentPlayer.getPosition());
        Property currentProperty = currentSpace.getProperty();

        currentPlayer.buy(currentProperty);
    }
}
