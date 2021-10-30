import javax.swing.*;
import java.awt.*;

public class BoardView extends JFrame implements MonopolyView {

    private Board board;
    private SpacePanel[] spacePanels;

    public BoardView(Board board) {
        super();
        this.board = board;
        this.setLayout(new BorderLayout());
        spacePanels = new SpacePanel[Board.BOARD_SIZE];

        //set up the grid
        JPanel gridPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;

        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            int[] position = getGridPosition(i);
            SpacePanel panel;
            Space space = this.board.getSpace(i);
            if (space instanceof PropertySpace) {
                Property property = ((PropertySpace) space).getProperty();
                panel = new PropertySpacePanel(property);
            } else {
                panel = new EmptySpacePanel();
            }
            c.gridx = position[0];
            c.gridy = position[1];
            gridPanel.add(panel, c);
            spacePanels[i] = panel;
        }

        for (Player player : board.getPlayers()) {
            spacePanels[0].addPlayer(player);
        }

        JScrollPane gridScrollPane = new JScrollPane(gridPanel);
        gridScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        gridScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(gridScrollPane, BorderLayout.CENTER);

        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     *
     * @param position
     * @return
     */
    private int[] getGridPosition(int position) {
        int x;
        int y;
        if (0 <= position && position < 10) {
            x = 10 - (position % 10);
            y = 10;
        } else if (10 <= position && position < 20) {
            x = 0;
            y = 10 - (position % 10);
        } else if (20 <= position && position < 30) {
            x = (position % 10);
            y = 0;
        } else {
            x = 10;
            y = (position % 10);
        }
        int[] coordinates = {x, y};
        return coordinates;
    }

    @Override
    public void handleMovePlayer(MovePlayerEvent e) {

    }

    @Override
    public void handleBuy(BuyEvent e) {

    }

    @Override
    public void handlePayRent(RentEvent e) {

    }

    @Override
    public void handleBankruptcy(BankruptcyEvent e) {

    }

    public static void main(String[] args) {
        Board board = new Board(8);
        new BoardView(board);
    }
}
