import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ArrayDisplayPanel extends JPanel {
    private final int WIDTH = 9;
    private final int HEIGHT = 7;
    private final Board board;

    private Piece selectedPiece; // Track selected piece
    private Game game; // Let the panel communicate with the Game

    public ArrayDisplayPanel(Board board) {
        this.board = board;
        setPreferredSize(new Dimension(450, 350));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });

        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (selectedPiece == null)
                    return;

                int x = selectedPiece.getX();
                int y = selectedPiece.getY();
                int newX = x;
                int newY = y;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        newX--;
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        newX++;
                        break;
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        newY--;
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        newY++;
                        break;
                    default:
                        return;
                }

                boolean moved = game.tryMove(selectedPiece, newX, newY);
                if (moved) {
                    selectedPiece = null;
                    updateBoard();
                } else {
                    System.out.println("Invalid move.");
                }
            }
        });

    }

    public void setGame(Game game) {
        this.game = game;
    }

    private void handleClick(int mouseX, int mouseY) {
        int cellWidth = getWidth() / WIDTH;
        int cellHeight = getHeight() / HEIGHT;

        int col = mouseX / cellWidth;
        int row = mouseY / cellHeight;

        Tile clickedTile = board.getGrid()[row][col];
        Piece clickedPiece = clickedTile.getPiece();

        if (clickedPiece != null && clickedPiece.getOwner() == game.getCurrentPlayer()) {
            selectedPiece = clickedPiece;
            System.out.println("Selected: " + selectedPiece.getName());
            repaint();
            requestFocusInWindow(); // make sure keyboard input stays focused
        } else {
            System.out.println("Invalid selection. Click one of your pieces.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellWidth = getWidth() / WIDTH;
        int cellHeight = getHeight() / HEIGHT;

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Tile tile = board.getGrid()[i][j];
                Image tileImage = tile.getImage();
                if (tileImage != null) {
                    g.drawImage(tileImage, j * cellWidth, i * cellHeight, this);
                }

                if (tile.isOccupied()) {
                    Piece piece = tile.getPiece();
                    Image pieceImage = piece.getImage();
                    if (pieceImage != null) {
                        g.drawImage(pieceImage, j * cellWidth, i * cellHeight, this);
                    }
                }

                g.setColor(Color.BLACK);
                g.drawRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
            }
        }
        String playerInfo = "Current Player: " + game.getCurrentPlayer().getName();
        String selectionInfo = (selectedPiece != null)
                ? "Selected: " + selectedPiece.getName()
                : "No piece selected";

        // Coordinates
        int playerInfoX = 10;
        int playerInfoY = getHeight() - 25;

        int selectionInfoX = 10;
        int selectionInfoY = getHeight() - 10;

        // Font
        Font font = new Font("Arial", Font.BOLD, 14);
        g.setFont(font);

        // Draw outline by drawing text slightly offset in all directions
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);

        // Outline for playerInfo
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx != 0 || dy != 0) {
                    g2.drawString(playerInfo, playerInfoX + dx, playerInfoY + dy);
                    g2.drawString(selectionInfo, selectionInfoX + dx, selectionInfoY + dy);
                }
            }
        }

        // Main (filled) text in white or black
        g2.setColor(Color.WHITE);
        g2.drawString(playerInfo, playerInfoX, playerInfoY);
        g2.drawString(selectionInfo, selectionInfoX, selectionInfoY);
    }

    public void updateBoard() {
        repaint();
    }
}
