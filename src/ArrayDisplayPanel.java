import javax.swing.*;
import java.awt.*;

public class ArrayDisplayPanel extends JPanel {

    private final int WIDTH = 9;
    private final int HEIGHT = 7;
    private Board board;

    public ArrayDisplayPanel(Board board) {
        this.board = board;
        setPreferredSize(new Dimension(450, 350)); // Set the preferred size of the panel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellWidth = getWidth() / WIDTH;
        int cellHeight = getHeight() / HEIGHT;

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                g.drawRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                g.drawString(board.getGrid()[i][j].getType(), j * cellWidth + cellWidth / 4, i * cellHeight + cellHeight / 2);
            }
        }
    }
}