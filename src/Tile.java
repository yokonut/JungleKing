/**
 * The Tile class represents a tile on the game board in the Jungle King game.
 * Each tile has a type, an optional piece, and an optional owner.
 */
public class Tile {
    public static int NORMAL = 0;
    public static int LAKE = 1;
    public static int TRAP = 2;
    public static int HOME_BASE = 3;

    private int type;
    private Piece piece;
    private Player owner;

    /**
     * Constructs a new Tile with the specified type.
     *
     * @param type the type of the tile (NORMAL, LAKE, TRAP, HOME_BASE)
     */
    public Tile(int type) {
        this.type = type;
        this.piece = null; // No piece on the tile
        this.owner = null; // No owner of the tile
    }


    /**
     * Gets the type of the tile.
     *
     * @return the type of the tile
     */
    public int getType() {
        return type;
    }


    /**
     * Gets the piece on the tile.
     *
     * @return the piece on the tile, or null if the tile is empty
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Places a piece on the tile.
     *
     * @param piece the piece to place on the tile
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }


    /**
     * Gets the owner of the tile.
     *
     * @return the owner of the tile, or null if the tile has no owner
     */
    public Player getOwner() {
        return owner;
    }


    /**
     * Sets the owner of the tile.
     *
     * @param owner the owner of the tile
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }


    /**
     * Checks if the tile is occupied by a piece.
     *
     * @return true if the tile is occupied by a piece, false otherwise
     */
    public boolean isOccupied() {
        return piece != null;
    }
}