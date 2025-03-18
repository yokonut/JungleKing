import javax.swing.*;
import java.awt.*;


public class ArrayDisplayPanel extends JPanel {
    private final int WIDTH = 9;
    private final int HEIGHT = 7;
    private Board board;

    public ArrayDisplayPanel(Board board) {
        this.board = board;
        setPreferredSize(new Dimension(450, 350));
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellWidth = getWidth() / WIDTH;
        int cellHeight = getHeight() / HEIGHT;



        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                g.drawRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);

                Tile tile = board.getGrid()[i][j];

                // If occupied, draw the piece
                if (tile.isOccupied()) {
                    g.drawString(tile.getPiece().getName(), j * cellWidth + 10, i * cellHeight + 20);
                }
                else {
                    g.drawString(tile.getType(), j * cellWidth + 10, i * cellHeight + 20);
                }
            }
        }
    }

    public void updateBoard() {
        repaint();  // Refresh GUI when the board updates
    }
}
