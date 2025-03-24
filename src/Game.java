import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.swing.JOptionPane;


/**
 * The Game class represents the main game logic for the Jungle King game.
 * It handles the initialization of the game, player turns, and game rules.
 */
public class Game {
    private Board board;
    private Player player1, player2;
    private Player currentPlayer;

    private ArrayDisplayPanel displayPanel;
    private Map<String, Integer> pieceHierarchy;


    /**
     * Constructs a new Game and initializes the game components.
     */
    public Game(Board board, ArrayDisplayPanel displayPanel) {
        // Initialize Game components
        this.board = board;
        this.displayPanel = displayPanel;

        this.player1 = new Player("Player 1");
        this.player2 = new Player("Player 2");
        this.currentPlayer = player1;
     
      
        

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

        // Determine the first player
      

    }


  

    /**
     * Determines the first player by having each player select a piece.
     * The player who selects the stronger piece goes first.
     */
  
    /**
     * Starts the game and handles the main game loop.
     */
    

    public boolean tryMove(Piece piece, int newX, int newY) {
    if (board.isOutOfBounds(newX, newY)) return false;

    Piece target = board.getPiece(newX, newY);

    
    if (target != null && !board.isLake(newX, newY)) {
        if (target.getOwner() == currentPlayer) return false; // CANNOT EAT ITS OWN PIECE
        if (!canCapture(piece, target)) return false; // CANNOT CAPTURE PIECE THAT IS STRONGER

        System.out.println(piece.getName() + " captured " + target.getName()); 
        board.movePiece(piece, newX, newY);
    } else {
        if (!piece.move(newX, newY)) return false;
    }

    displayPanel.updateBoard();

    if (board.isOpponentHomeBase(newX, newY, currentPlayer)) {
        JOptionPane.showMessageDialog(displayPanel, currentPlayer.getName() + " wins!");
        return true;
    }

    switchPlayer();
    return true;
}

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
    private boolean canCapture(Piece attacker, Piece defender) {
        if (attacker.getName().equals("Rat") && defender.getName().equals("Elephant")) {       // Special case: Rat can capture Elephant
            return true; 
        }
        if (attacker.getName().equals("Elephant") && defender.getName().equals("Rat")) {      // Special case: Elephant CAN'T capture Rat
            return false; // Special case: Rat can capture Elephant
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
}