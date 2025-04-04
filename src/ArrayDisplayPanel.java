import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
 * The ArrayDisplayPanel class is responsible for displaying the game board and
 * handling user interactions.
 */

public class ArrayDisplayPanel extends JPanel {
    private final int WIDTH = 9;
    private final int HEIGHT = 7;
    private final Board board;
    private final ErrorSoundEffect errorSoundEffect;
    private final SelectSoundEffect selectSoundEffect;
    private final WaterSplashEffect waterSplashEffect;

    private Piece selectedPiece; // Track selected piece
    private Game game; // Let the panel communicate with the Game

    /**
     * Constructs a new ArrayDisplayPanel and initializes the board and sound
     * effects.
     *
     * @param board             The game board to be displayed.
     * @param errorSoundEffect  The sound effect for errors.
     * @param waterSplashEffect The sound effect for water splash.
     * @param selectSoundEffect The sound effect for selection.
     */

    public ArrayDisplayPanel(Board board, ErrorSoundEffect errorSoundEffect, WaterSplashEffect waterSplashEffect,
            SelectSoundEffect selectSoundEffect) {
        this.errorSoundEffect = errorSoundEffect;
        this.selectSoundEffect = selectSoundEffect;
        this.waterSplashEffect = waterSplashEffect;

        this.board = board;
        setPreferredSize(new Dimension(450, 350));

        // handle mouse clicks

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());

            }
        });

        setFocusable(true);
        requestFocusInWindow();

        // handle keyboard input

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

                boolean moved = game.tryMove(selectedPiece, newX, newY); // Check if the move is valid
                if (moved) {
                    if (board.isLake(newX, newY) && selectedPiece.getName().equals("Rat")) {
                        selectedPiece.loadImage("ratwater");
                        waterSplashEffect.play();
                    } // Inserted trap logic
                    else if (board.isTrap(newX, newY) && selectedPiece.getName().equals("Rat")) {
                        if (selectedPiece.getOwner() == game.getPlayer1())
                            selectedPiece.loadImage("rat1trap");
                        else
                            selectedPiece.loadImage("rat2trap");
                    } else if (board.isTrap(newX, newY) && selectedPiece.getName().equals("Cat")) {
                        if (selectedPiece.getOwner() == game.getPlayer1())
                            selectedPiece.loadImage("cat1trap");
                        else
                            selectedPiece.loadImage("cat2trap");
                    } else if (board.isTrap(newX, newY) && selectedPiece.getName().equals("Dog")) {
                        if (selectedPiece.getOwner() == game.getPlayer1())
                            selectedPiece.loadImage("dog1trap");
                        else
                            selectedPiece.loadImage("dog2trap");
                    } else if (board.isTrap(newX, newY) && selectedPiece.getName().equals("Wolf")) {
                        if (selectedPiece.getOwner() == game.getPlayer1())
                            selectedPiece.loadImage("wolf1trap");
                        else
                            selectedPiece.loadImage("wolf2trap");
                    } else if (board.isTrap(newX, newY) && selectedPiece.getName().equals("Leopard")) {
                        if (selectedPiece.getOwner() == game.getPlayer1())
                            selectedPiece.loadImage("leopard1trap");
                        else
                            selectedPiece.loadImage("leopard2trap");
                    } else if (board.isTrap(newX, newY) && selectedPiece.getName().equals("Tiger")) {
                        if (selectedPiece.getOwner() == game.getPlayer1())
                            selectedPiece.loadImage("tiger1trap");
                        else
                            selectedPiece.loadImage("tiger2trap");
                    } else if (board.isTrap(newX, newY) && selectedPiece.getName().equals("Lion")) {
                        if (selectedPiece.getOwner() == game.getPlayer1())
                            selectedPiece.loadImage("lion1trap");
                        else
                            selectedPiece.loadImage("lion2trap");
                    } else if (board.isTrap(newX, newY) && selectedPiece.getName().equals("Elephant")) {
                        if (selectedPiece.getOwner() == game.getPlayer1())
                            selectedPiece.loadImage("elephant1trap");
                        else
                            selectedPiece.loadImage("elephant2trap");
                    } else {
                        selectedPiece.loadImage(selectedPiece.getName());
                    }
                    selectedPiece = null;
                    updateBoard();
                } else {
                    System.out.println("Invalid move.");
                }
            }
        });

    }

    /**
     * Sets the game instance for the panel.
     *
     * @param game The game instance to be set.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Handles mouse click events to select or move pieces on the board.
     * 
     * @param mouseX
     * @param mouseY
     */
    private void handleClick(int mouseX, int mouseY) {
        int cellWidth = getWidth() / WIDTH;
        int cellHeight = getHeight() / HEIGHT;

        int col = mouseX / cellWidth;
        int row = mouseY / cellHeight;

        Tile clickedTile = board.getGrid()[row][col];
        Piece clickedPiece = clickedTile.getPiece();

        if (selectedPiece == null) {

            if (clickedPiece != null && clickedPiece.getOwner() == game.getCurrentPlayer()) {
                selectedPiece = clickedPiece;
                selectSoundEffect.play();
                System.out.println("Selected: " + selectedPiece.getName());
                repaint();
                requestFocusInWindow(); // make sure keyboard input stays focused
            } else {
                System.out.println("Invalid selection. Click one of your pieces.");
                errorSoundEffect.play();
            }
        } else if (clickedPiece != null && clickedPiece.getOwner() == game.getCurrentPlayer()) {
            selectedPiece = clickedPiece;
            selectSoundEffect.play();
            System.out.println("Selected" + selectedPiece.getName());
            repaint();
        } else {
            // Trying to move selected piece
            int newX = row;
            int newY = col;

            int dx = Math.abs(newX - selectedPiece.getX());
            int dy = Math.abs(newY - selectedPiece.getY());

            if ((dx + dy) == 1) {

                boolean moved = game.tryMove(selectedPiece, newX, newY);
                if (moved) {
                    if (board.isLake(newX, newY) && selectedPiece.getName().equals("Rat")) {
                        selectedPiece.loadImage("ratwater");
                        waterSplashEffect.play();
                    }
                    // for traps
                    else if (board.isTrap(newX, newY) && selectedPiece.getName().equals("Rat")) {
                        if (selectedPiece.getOwner() == game.getPlayer1())
                            selectedPiece.loadImage("rat1trap");
                        else
                            selectedPiece.loadImage("rat2trap");
                    } else if (board.isTrap(newX, newY) && selectedPiece.getName().equals("Cat")) {
                        if (selectedPiece.getOwner() == game.getPlayer1())
                            selectedPiece.loadImage("cat1trap");
                        else
                            selectedPiece.loadImage("cat2trap");
                    } else if (board.isTrap(newX, newY) && selectedPiece.getName().equals("Dog")) {
                        if (selectedPiece.getOwner() == game.getPlayer1())
                            selectedPiece.loadImage("dog1trap");
                        else
                            selectedPiece.loadImage("dog2trap");
                    } else if (board.isTrap(newX, newY) && selectedPiece.getName().equals("Wolf")) {
                        if (selectedPiece.getOwner() == game.getPlayer1())
                            selectedPiece.loadImage("wolf1trap");
                        else
                            selectedPiece.loadImage("wolf2trap");
                    } else if (board.isTrap(newX, newY) && selectedPiece.getName().equals("Leopard")) {
                        if (selectedPiece.getOwner() == game.getPlayer1())
                            selectedPiece.loadImage("leopard1trap");
                        else
                            selectedPiece.loadImage("leopard2trap");
                    } else if (board.isTrap(newX, newY) && selectedPiece.getName().equals("Tiger")) {
                        if (selectedPiece.getOwner() == game.getPlayer1())
                            selectedPiece.loadImage("tiger1trap");
                        else
                            selectedPiece.loadImage("tiger2trap");
                    } else if (board.isTrap(newX, newY) && selectedPiece.getName().equals("Lion")) {
                        if (selectedPiece.getOwner() == game.getPlayer1())
                            selectedPiece.loadImage("lion1trap");
                        else
                            selectedPiece.loadImage("lion2trap");
                    } else if (board.isTrap(newX, newY) && selectedPiece.getName().equals("Elephant")) {
                        if (selectedPiece.getOwner() == game.getPlayer1())
                            selectedPiece.loadImage("elephant1trap");
                        else
                            selectedPiece.loadImage("elephant2trap");
                    } else {
                        selectedPiece.loadImage(selectedPiece.getName());
                    }
                    selectedPiece = null;
                    updateBoard();
                } else {
                    System.out.println("Invalid move.");
                    errorSoundEffect.play();
                }
            } else {
                System.out.println("You can only move one step.");
                errorSoundEffect.play();
            }
        }

    }

    /**
     * Paints the components of the panel, including the board and pieces.
     *
     * @param g The Graphics object used for painting.
     */
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

        // Calculate positions for text
        int playerInfoX = 10;
        int playerInfoY = getHeight() - 25;

        int selectionInfoX = 10;
        int selectionInfoY = getHeight() - 10;

        // Set font and color for text
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

    /**
     * Updates the board by repainting the panel.
     */
    public void updateBoard() {
        repaint();
    }
}
