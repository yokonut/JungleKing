public class Board {
    private static final int WIDTH = 9;
    private static final int HEIGHT = 7;
    private Tile[][] grid;

    public Board() {
        grid = new Tile[HEIGHT][WIDTH];

        // Initialize board tiles
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[i][j] = new Tile(Tile.NORMAL);
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

        // Define traps (near home base)
        setTrap(2, 0, "Player 2");
        setTrap(3, 1, "Player 2");
        setTrap(4, 0, "Player 2");
        setTrap(2, 8, "Player 1");
        setTrap(3, 7, "Player 1");
        setTrap(4, 8, "Player 1");

        // Define home bases
        setHomeBase(3, 0, "Player 1");
        setHomeBase(3, 8, "Player 2");
    }

    private void setLake(int x, int y) {
        grid[x][y] = new Tile(Tile.LAKE);
    }

    private void setTrap(int x, int y, String playerName) {
        grid[x][y] = new Tile(Tile.TRAP);
        grid[x][y].setOwner(new Player(playerName));
    }

    private void setHomeBase(int x, int y, String playerName) {
        grid[x][y] = new Tile(Tile.HOME_BASE);
        grid[x][y].setOwner(new Player(playerName));
    }

    public Tile getTile(int x, int y) {
        return grid[x][y];
    }

   

    public boolean isLake(int x, int y) {
        return grid[x][y].getType() == Tile.LAKE;
    }

    public boolean isTrap(int x, int y) {
        return grid[x][y].getType() == Tile.TRAP;
    }

    public boolean isHomeBase(int x, int y) {
        return grid[x][y].getType() == Tile.HOME_BASE;
    }

    public Piece getPiece(int x, int y) {
        return grid[x][y].getPiece();
    }

    public Piece getPieceByName(String name, Player owner) {
        String lowerCaseName = name.toLowerCase();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Piece piece = grid[i][j].getPiece();
                if (piece != null && piece.getName().toLowerCase().equals(lowerCaseName)
                        && piece.getOwner().equals(owner)) {
                    return piece;
                }
            }
        }
        return null;
    }

    public void placePiece(Piece piece) {
        grid[piece.x][piece.y].setPiece(piece);
    }

    public void movePiece(Piece piece, int newX, int newY) {
        grid[piece.getX()][piece.getY()].setPiece(null);
        piece.move(newX, newY);
        grid[newX][newY].setPiece(piece);
    }

    public void printBoard() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Tile tile = grid[i][j];
                if (tile.isOccupied()) {
                    System.out.print(tile.getPiece().getName().charAt(0) + " ");
                } else if (tile.getType() == Tile.LAKE) {
                    System.out.print("~ ");
                } else if (tile.getType() == Tile.TRAP) {
                    System.out.print("Z ");
                } else if (tile.getType() == Tile.HOME_BASE) {
                    System.out.print("H ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
}