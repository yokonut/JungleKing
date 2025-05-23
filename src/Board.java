import java.io.Serializable;

/**
 * The Board class represents the game board in the Jungle King game.
 * It manages the tiles, pieces, and game rules related to the board.
 */

public class Board implements Serializable {
    private static final long serialVersionUID = 1L; // Added for serialization

    private static int WIDTH = 9;
    private static int HEIGHT = 7;
    public static Tile[][] grid;

    /**
     * Constructs a new Board and initializes the tiles.
     */
    public Board(Player player1, Player player2) {
        grid = new Tile[HEIGHT][WIDTH];

        // Initialize board tiles
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[i][j] = new Normal();
            }
        }

        // Define lakes
        setLake(1, 3);
        setLake(4, 3);
        setLake(1, 4);
        setLake(4, 4);
        setLake(1, 5);
        setLake(4, 5);
        setLake(2, 3);
        setLake(5, 3);
        setLake(2, 4);
        setLake(5, 4);
        setLake(2, 5);
        setLake(5, 5);

        setTrap(2, 0, player2);
        setTrap(3, 1, player2);
        setTrap(4, 0, player2);
        setTrap(2, 8, player1);
        setTrap(3, 7, player1);
        setTrap(4, 8, player1);

        // Define home bases
        setHomeBase(3, 0, player1);
        setHomeBase(3, 8, player2);
    }

    /**
     * Gets the grid of tiles on the board.
     *
     * @return the grid of tiles
     */

    public Tile[][] getGrid() {
        return grid;
    }

    /**
     * Sets a tile as a lake.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     */
    private void setLake(int x, int y) {
        grid[x][y] = new Lake();
    }

    /**
     * Sets a tile as a trap.
     *
     * @param x          the x-coordinate of the tile
     * @param y          the y-coordinate of the tile
     * @param playerName the name of the player who owns the trap
     */
    private void setTrap(int x, int y, Player player) {
        grid[x][y] = new Trap();
        grid[x][y].setOwner(player);
    }

    /**
     * Sets a tile as a home base.
     *
     * @param x          the x-coordinate of the tile
     * @param y          the y-coordinate of the tile
     * @param playerName the name of the player who owns the home base
     */
    private void setHomeBase(int x, int y, Player player) {
        grid[x][y] = new Home();
        grid[x][y].setOwner(player);
    }

    /**
     * Checks if a tile is a normal tile.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     * @return true if the tile is a normal tile, false otherwise
     */
    public boolean isNormal(int x, int y) {
        return grid[x][y].getType() == "Normal";
    }

    /**
     * Checks if a tile is a lake.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     * @return true if the tile is a lake, false otherwise
     */
    public boolean isLake(int x, int y) {
        return grid[x][y].getType() == "Lake";
    }

    /**
     * Checks if a tile is a trap.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     * @return true if the tile is a trap, false otherwise
     */
    public boolean isTrap(int x, int y) {
        return grid[x][y].getType() == "Trap";
    }

    /**
     * Checks if a tile is occupied by a piece.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     * @return true if the tile is occupied, false otherwise
     */
    public boolean isOccupied(int x, int y) {
        return grid[x][y].getPiece() != null;
    }

    /**
     * Checks if a tile is the opponent's home base.
     *
     * @param x      the x-coordinate of the tile
     * @param y      the y-coordinate of the tile
     * @param player the player to check against
     * @return true if the tile is the opponent's home base, false otherwise
     */
    public boolean isOpponentHomeBase(int x, int y, Player playerName) {
        Tile tile = grid[x][y];
        return tile.getType() == "Home" && !tile.getOwner().equals(playerName);
    }

    /**
     * Gets the piece on a tile.
     *
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     * @return the piece on the tile, or null if the tile is empty
     */
    public Piece getPiece(int x, int y) {
        return grid[x][y].getPiece();
    }

    /**
     * Places a piece on the board.
     *
     * @param piece the piece to place on the board
     */
    public void placePiece(Piece piece) {
        grid[piece.getX()][piece.getY()].setPiece(piece); // set the piece to the tile
    }

    /**
     * Moves a piece to a new location on the board.
     *
     * @param piece the piece to move
     * @param newX  the new x-coordinate of the piece
     * @param newY  the new y-coordinate of the piece
     */
    public void movePiece(Piece piece, int newX, int newY) {
        grid[piece.getX()][piece.getY()].setPiece(null); // make the old tile empty
        piece.x = newX; // update piece coordinates
        piece.y = newY; // update piece coordinates
        grid[newX][newY].setPiece(piece); // set new tile to the piece
    }

    /**
     * Checks if there is a rat in the lake path between two coordinates.
     *
     * @param x    the starting x-coordinate
     * @param y    the starting y-coordinate
     * @param newX the ending x-coordinate
     * @param newY the ending y-coordinate
     * @return true if there is a rat in the lake path, false otherwise
     */
    public boolean isRatInLakePath(int x, int y, int newX, int newY) {
        if (x < newX || x > newX) {
            int n = 2;

            if (x < newX) {

                for (int i = newX; i < newX + n; i++) {
                    if (grid[i][y].getPiece() != null && grid[i][y].getPiece().getName().equals("Rat")) {
                        return true;
                    }
                }
            } else if (x > newX) {
                for (int i = newX; i > newX - n; i--) {
                    if (grid[i][y].getPiece() != null && grid[i][y].getPiece().getName().equals("Rat")) {
                        return true;
                    }
                }
            }
        }

        else if (y < newY || y > newY) {
            int n = 3;

            if (y < newY) {
                for (int i = newY; i < newY + n; i++) {
                    if (grid[x][i].getPiece() != null && grid[x][i].getPiece().getName().equals("Rat")) {
                        return true;
                    }
                }
            } else if (y > newY) {
                for (int i = newY; i > newY - n; i--) {
                    if (grid[x][i].getPiece() != null && grid[x][i].getPiece().getName().equals("Rat")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if the coordinates are out of bounds.
     *
     * @param x the x-coordinate to check
     * @param y the y-coordinate to check
     * @return true if the coordinates are out of bounds, false otherwise
     */
    public boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= HEIGHT || y < 0 || y >= WIDTH;
    }

    /*
     * * Clears the board by removing all pieces from the tiles.
     */

    public void clearBoard() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j].setPiece(null); // Remove the piece from each tile
            }
        }
    }

}