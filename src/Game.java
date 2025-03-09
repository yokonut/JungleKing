import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Game {
    private Board board;
    private Player player1, player2;
    private Player currentPlayer;
    private Scanner scanner;
    private Map<String, Integer> pieceHierarchy;

    public Game() {

        // Inititialize Game components

        board = new Board();
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        currentPlayer = player1;
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

        // Player 1 Pieces
        Piece elephant1 = new Elephant(0, 2, player1, board);
        Piece lion1 = new Lion(6, 0, player1, board);
        Piece tiger1 = new Tiger(0, 0, player1, board);
        Piece leopard1 = new Leopard(4, 2, player1, board);
        Piece wolf1 = new Wolf(2, 2, player1, board);
        Piece dog1 = new Dog(5, 1, player1, board);
        Piece cat1 = new Cat(1, 1, player1, board);
        Piece rat1 = new Rat(6, 2, player1, board);

        // Player 2 Pieces
        Piece elephant2 = new Elephant(6, 6, player2, board);
        Piece lion2 = new Lion(0, 8, player2, board);
        Piece tiger2 = new Tiger(6, 8, player2, board);
        Piece leopard2 = new Leopard(2, 6, player2, board);
        Piece wolf2 = new Wolf(4, 6, player2, board);
        Piece dog2 = new Dog(1, 7, player2, board);
        Piece cat2 = new Cat(5, 7, player2, board);
        Piece rat2 = new Rat(0, 6, player2, board);

        // Place Pieces on Board
        board.placePiece(elephant1);
        board.placePiece(lion1);
        board.placePiece(tiger1);
        board.placePiece(leopard1);
        board.placePiece(wolf1);
        board.placePiece(dog1);
        board.placePiece(cat1);
        board.placePiece(rat1);

        board.placePiece(elephant2);
        board.placePiece(lion2);
        board.placePiece(tiger2);
        board.placePiece(leopard2);
        board.placePiece(wolf2);
        board.placePiece(dog2);
        board.placePiece(cat2);
        board.placePiece(rat2);
    }

    // Start of the game
    public void start() {
        while (true) {
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
            if (targetPiece != null) {
                if (targetPiece.getOwner() == currentPlayer) {
                    System.out.println("You cannot capture your own piece. Try again.");
                    continue;
                } else if (canCapture(piece, targetPiece)) {
                    System.out.println(piece.getName() + " captured " + targetPiece.getName() + "!");
                    board.movePiece(piece, newX, newY);
                    switchPlayer();
                } else {
                    System.out.println("Cannot capture this piece. Try again.");
                }
            }

            // check if the target tile is lake or normal
            else {
                if (piece.move(newX, newY)) {
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
        Integer attackerRank = pieceHierarchy.get(attacker.getName());
        Integer defenderRank = pieceHierarchy.get(defender.getName());
        return attackerRank != null && defenderRank != null && attackerRank >= defenderRank;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
}
