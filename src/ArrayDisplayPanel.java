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
                Tile tile = board.getGrid()[i][j];

                // Draw the tile image
                Image tileImage = tile.getImage();
                if (tileImage != null) {
                    g.drawImage(tileImage, j * cellWidth, i * cellHeight, this);
                } 
                
                // Draw the piece on top of the tile
                if (tile.isOccupied()) {
                    Piece piece = tile.getPiece();
                    Image pieceImage = piece.getImage();

                    if (pieceImage != null) {
                        g.drawImage(pieceImage, j * cellWidth, i * cellHeight, this);
                    }
                }

                // Draw grid lines
                g.setColor(Color.BLACK);
                g.drawRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
            }
        }
    }

    public void updateBoard() {
        repaint();  // Refresh the GUI when the board updates
    }
}
