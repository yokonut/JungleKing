/**
 * The Piece class represents a game piece in the Jungle King game.
 * Each piece has a name, coordinates (x, y), an owner (Player), and a reference to the game board.
 */
public abstract class Piece {
    public String name;
    public int x, y;
    public Player owner;
    public Board board;

    /**
     * Constructs a new Piece with the specified name, coordinates, owner, and board.
     *
     * @param name  the name of the piece
     * @param x     the x-coordinate of the piece
     * @param y     the y-coordinate of the piece
     * @param owner the owner of the piece
     * @param board the game board the piece is on
     */

    public Piece(String name, int x, int y, Player owner, Board board) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.owner = owner;
        this.board = board;
    }

    /**
     * Moves the piece to a new location if the move is valid.
     *
     * @param newX the new x-coordinate
     * @param newY the new y-coordinate
     * @return true if the move is successful, false otherwise
     */
    public boolean move(int newX, int newY) {
        if (board.isNormal(newX, newY) || board.isOpponentHomeBase(newX, newY, owner)) {
            board.movePiece(this, newX, newY);
            return true;
        }
        return false;
    }


    /**
     * Gets the name of the piece.
     *
     * @return the name of the piece
     */
    public String getName() {
        return name;
    }


    /**
     * Gets the owner of the piece.
     *
     * @return the owner of the piece
     */
    public Player getOwner() {
        return owner;
    }


    /**
     * Gets the x-coordinate of the piece.
     *
     * @return the x-coordinate of the piece
     */
    public int getX() {
        return x;
    }


    /**
     * Gets the y-coordinate of the piece.
     *
     * @return the y-coordinate of the piece
     */
    public int getY() {
        return y;
    }
}