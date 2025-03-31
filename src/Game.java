
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JOptionPane;

/**
 * The Game class represents the main game logic for the Jungle King game.
 * It handles the initialization of the game, player turns, and game rules.
 */
public class Game implements Serializable {
    private static final long serialVersionUID = 1L; // Added for serialization

    public Board board;
    /*
     * private Board board;
     * private Player player1, player2;
     * private Player currentPlayer;
     */
    public Player player1, player2;
    public Player currentPlayer;
    public transient /* i changed this to public */ ArrayDisplayPanel displayPanel;
    private Map<String, Integer> pieceHierarchy;
    private transient EatingSoundEffect eatingSoundEffect;
    private transient MovingSoundEffect movingSoundEffect;
    private transient ErrorSoundEffect errorSoundEffect;
    private transient WinSoundEffect winSoundEffect;
    private transient JFrame frame;
    private transient JPanel menuPanel;

    /**
     * Constructs a new Game and initializes the game components.
     */
    public Game(Board board, ArrayDisplayPanel displayPanel, EatingSoundEffect eatingSoundEffect,
            MovingSoundEffect movingSoundEffect, ErrorSoundEffect errorSoundEffect, Player player1, Player player2,
            WinSoundEffect winSoundEffect, JFrame frame, JPanel menuPanel, boolean isNewGame) {
        // Initialize Game components
        this.board = board;
        this.displayPanel = displayPanel;
        this.eatingSoundEffect = eatingSoundEffect;
        this.movingSoundEffect = movingSoundEffect;
        this.errorSoundEffect = errorSoundEffect;
        this.winSoundEffect = winSoundEffect;
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.frame = frame;
        this.menuPanel = menuPanel;

        // Define piece hierarchy with HashMap
        pieceHierarchy = new HashMap<>();
        pieceHierarchy.put("Rat", 0);
        pieceHierarchy.put("Cat", 1);
        pieceHierarchy.put("Dog", 2);
        pieceHierarchy.put("Wolf", 3);
        pieceHierarchy.put("Leopard", 4);
        pieceHierarchy.put("Tiger", 5);
        pieceHierarchy.put("Lion", 6);
        pieceHierarchy.put("Elephant", 7);

        // Initialize pieces only if it's a new game
        if (isNewGame) {
            initializePieces();
        }

        /*
         * // Create a list of all pieces while initializing them
         * List<Piece> pieces = new ArrayList<>();
         * pieces.add(new Elephant(0, 2, player1, board));
         * pieces.add(new Lion(6, 0, player1, board));
         * pieces.add(new Tiger(0, 0, player1, board));
         * pieces.add(new Leopard(4, 2, player1, board));
         * pieces.add(new Wolf(2, 2, player1, board));
         * pieces.add(new Dog(5, 1, player1, board));
         * pieces.add(new Cat(1, 1, player1, board));
         * pieces.add(new Rat(6, 2, player1, board));
         * pieces.add(new Elephant(6, 6, player2, board));
         * pieces.add(new Lion(0, 8, player2, board));
         * pieces.add(new Tiger(6, 8, player2, board));
         * pieces.add(new Leopard(2, 6, player2, board));
         * pieces.add(new Wolf(4, 6, player2, board));
         * pieces.add(new Dog(1, 7, player2, board));
         * pieces.add(new Cat(5, 7, player2, board));
         * pieces.add(new Rat(0, 6, player2, board));
         * 
         * // Place Pieces on Board
         * for (Piece piece : pieces) {
         * board.placePiece(piece);
         * }
         */
        // Determine the first player

    }

    private void initializePieces() {
        // Create a list of all pieces while initializing them
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Elephant(0, 2, player1, board));
        pieces.add(new Lion(6, 0, player1, board));
        pieces.add(new Tiger(0, 0, player1, board));
        pieces.add(new Leopard(4, 2, player1, board));
        pieces.add(new Wolf(2, 2, player1, board));
        pieces.add(new Dog(5, 1, player1, board));
        pieces.add(new Cat(1, 1, player1, board));
        pieces.add(new Rat(6, 2, player1, board));
        pieces.add(new Elephant(6, 6, player2, board));
        pieces.add(new Lion(0, 8, player2, board));
        pieces.add(new Tiger(6, 8, player2, board));
        pieces.add(new Leopard(2, 6, player2, board));
        pieces.add(new Wolf(4, 6, player2, board));
        pieces.add(new Dog(1, 7, player2, board));
        pieces.add(new Cat(5, 7, player2, board));
        pieces.add(new Rat(0, 6, player2, board));

        // Place Pieces on Board
        for (Piece piece : pieces) {
            board.placePiece(piece);
        }
    }

    /**
     * Returns the piece hierarchy.
     *
     * @return a map representing the piece hierarchy
     */
    public Map<String, Integer> getPieceHierarchy() {
        return pieceHierarchy;
    }

    /**
     * Returns player 1.
     * 
     * @return
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Returns player 2.
     * 
     * @return
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Sets the current player.
     *
     * @return the current player
     */
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    /**
     * Attempts to move a piece to a new position on the board.
     *
     * @param piece the piece to move
     * @param newX  the new x-coordinate
     * @param newY  the new y-coordinate
     * @return true if the move was successful, false otherwise
     */
    public boolean tryMove(Piece piece, int newX, int newY) {
        if (board.isOutOfBounds(newX, newY)) {
            errorSoundEffect.play();
            return false;
        }

        Piece target = board.getPiece(newX, newY);

        if (target != null && !board.isLake(newX, newY)) {
            if (target.getOwner() == currentPlayer) {
                errorSoundEffect.play();
                return false; // CANNOT EAT ITS OWN PIECE
            }
            if (!canCapture(piece, target, board)) {
                errorSoundEffect.play();
                return false; // CANNOT CAPTURE PIECE THAT IS STRONGER
            }
            if (piece.getName().equals("Rat") && board.isLake(piece.getX(), piece.getY())
                    && target.getName().equals("Elephant")) {
                System.out.println("Rat cannot capture Elephant while in water");
                errorSoundEffect.play();
                return false; // RAT CANT CAPTURE ELEPHANT WHEN IN WATER GOING OUT
            }

            System.out.println(piece.getName() + " captured " + target.getName());
            eatingSoundEffect.play();
            board.movePiece(piece, newX, newY);
        } else {
            if (!piece.move(newX, newY)) {
                errorSoundEffect.play();
                return false;
            }

        }

        displayPanel.updateBoard();

        if (board.isOpponentHomeBase(newX, newY, currentPlayer)) {
            winSoundEffect.play();
            JOptionPane.showMessageDialog(displayPanel, currentPlayer.getName() + " wins!");

            frame.remove(displayPanel);
            frame.add(menuPanel);
            frame.pack();
            frame.setSize(450, 350); // Resize for menu
            frame.setLocationRelativeTo(null);
            frame.setAlwaysOnTop(true);

            return true;
        }

        switchPlayer();
        movingSoundEffect.play();
        return true;
    }

    /**
     * Returns the current player.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Checks if the attacker can capture the defender.
     *
     * @param attacker the attacking piece
     * @param defender the defending piece
     * @return true if the attacker can capture the defender, false otherwise
     */
    private boolean canCapture(Piece attacker, Piece defender, Board board) {
        if (attacker.getName().equals("Rat") && defender.getName().equals("Elephant")) { // Special case: Rat can
                                                                                         // capture Elephant
            return true;
        }
        if (attacker.getName().equals("Elephant") && defender.getName().equals("Rat")) { // Special case: Elephant CAN'T
                                                                                         // capture Rat
            return false; // Special case: Rat can capture Elephant
        }
        if (board.isTrap(defender.getX(), defender.getY())) {
            return true;
        }
        Integer attackerRank = pieceHierarchy.get(attacker.getName());
        Integer defenderRank = pieceHierarchy.get(defender.getName());
        return attackerRank >= defenderRank;
    }

    /**
     * Switches the current player.
     */
    private void switchPlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    public Board getBoard() {
        return board;
    }

}