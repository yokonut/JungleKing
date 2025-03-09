import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Game {
    private Board board;
    private Player player1, player2;
    private Player currentPlayer;
    private Scanner scanner;
    private Map<String, Integer> pieceHierarchy;

    public Game() {
        // Initialize Game components
        board = new Board();
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        scanner = new Scanner(System.in);

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
        determineFirstPlayer();

    }


    /* Method to clear the screen
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    */


    // Determines the first player
    private void determineFirstPlayer() {

        // Create a list of all pieces while initializing them
        List<String> pieces = new ArrayList<>(pieceHierarchy.keySet());
        Collections.shuffle(pieces);

        for (int i = 0; i < pieces.size(); i++) {
            System.out.print(i + "  ");
        }
        System.out.println();
        for (int i = 0; i < pieces.size(); i++) {
            System.out.print("X  ");
        }
        System.out.println();

        // Player 1 selects a piece
        System.out.println("Player 1, select an index:");
        int player1Index = scanner.nextInt();

        // Player 2 selects a piece
        System.out.println("Player 2, select an index:");
        int player2Index = scanner.nextInt();

        // Reveal the selected pieces
        String player1Piece = pieces.get(player1Index);
        String player2Piece = pieces.get(player2Index);
        System.out.println("Player 1 selected: " + player1Piece);
        System.out.println("Player 2 selected: " + player2Piece);

        int player1Rank = pieceHierarchy.get(player1Piece);
        int player2Rank = pieceHierarchy.get(player2Piece);

        // Determine the first player
        if (player1Rank > player2Rank) {
            currentPlayer = player1;
        } else {
            currentPlayer = player2;
        }

        System.out.println(currentPlayer.getName() + " goes first!!");
    }

    // Start of the game
    public void start() {
        Boolean isGameOver = false;
        while (!isGameOver) {
            
            // print board
            board.printBoard();
            if (currentPlayer == null) {
                System.out.println("Error: Current player is null.");
                return;
            }
            System.out.println(currentPlayer.getName() + "'s turn.");
            System.out.print("Select piece by name: ");
            String pieceName = scanner.next();
            Piece piece = board.getPieceByName(pieceName, currentPlayer);

            if (piece == null || piece.getOwner() != currentPlayer) {
                System.out.println("Invalid selection. Try again.");
                continue;
            }

            // get coordinates of the piece
            int x = piece.getX();
            int y = piece.getY();

            // get the move direction
            System.out.print("Move direction (WASD): ");
            char move = scanner.next().toUpperCase().charAt(0);
            int newX = x, newY = y;

            switch (move) {
                case 'W':
                    newX--;
                    break;
                case 'S':
                    newX++;
                    break;
                case 'A':
                    newY--;
                    break;
                case 'D':
                    newY++;
                    break;
                default:
                    System.out.println("Invalid move. Use WASD.");
                    continue;
            }

            // Check if the target tile is out of bounds
            if (newX < 0 || newX >= 7 || newY < 0 || newY >= 9) {
                System.out.println("Invalid move. Out of bounds.");
                continue;
            }

            // Check if the target tile is occupied
            Piece targetPiece = board.getPiece(newX, newY);
            if (targetPiece != null) {                                                                              // target tile is occupied by a piece
                if (targetPiece.getOwner() == currentPlayer) {                                                      // target piece is owned by the current player
                    System.out.println("You cannot capture your own piece. Try again.");
                    continue;
                } else if (canCapture(piece, targetPiece)) {                                                        // target piece can be captured                     
                    System.out.println(piece.getName() + " captured " + targetPiece.getName() + "!");
                    board.movePiece(piece, newX, newY);
                    if (board.isOpponentHomeBase(newX, newY, currentPlayer)) {
                        System.out.println(currentPlayer.getName() + " wins!");
                        isGameOver = true;
                    }
                    switchPlayer();
                } else {
                    System.out.println("Cannot capture this piece. Try again.");
                }
            }

            // if the target tile is not occupied
            else {
                if (piece.move(newX, newY)) {
                    if (board.isOpponentHomeBase(newX, newY, currentPlayer)) {
                        System.out.println(currentPlayer.getName() + " wins!");
                        isGameOver = true;
                    }
                    switchPlayer();
                } else {
                    System.out.println("Invalid move. Try again.");
                }
            }
            

        }

    }

    private boolean canCapture(Piece attacker, Piece defender) {
        if (attacker.getName().equals("Rat") && defender.getName().equals("Elephant")) {
            return true; // Special case: Rat can capture Elephant
        }
        if (attacker.getName().equals("Elephant") && defender.getName().equals("Rat")) {
            return false; // Special case: Rat can capture Elephant
        }
        Integer attackerRank = pieceHierarchy.get(attacker.getName());
        Integer defenderRank = pieceHierarchy.get(defender.getName());
        return attackerRank >= defenderRank;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
}