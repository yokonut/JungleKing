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

    public boolean isNormal(int x, int y) {
        return grid[x][y].getType() == Tile.NORMAL;
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
        grid[piece.getX()][piece.getY()].setPiece(piece); // set the piece to the tile
    }

    public void movePiece(Piece piece, int newX, int newY) {
        grid[piece.getX()][piece.getY()].setPiece(null); // make the old tile empty
        piece.x = newX; // update piece coordinates
        piece.y = newY; // update piece coordinates
        grid[newX][newY].setPiece(piece); // set new tile to the piece
    }

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

        else if(y < newY || y > newY) {
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

    public void printBoard() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Tile tile = grid[i][j];
                if (tile.isOccupied()) {
                    System.out.print(tile.getPiece().getName().charAt(0) + " ");
                } else if (tile.getType() == Tile.LAKE) {
                    System.out.print("~ ");
                } else if (tile.getType() == Tile.TRAP) {
                    System.out.print(". ");
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