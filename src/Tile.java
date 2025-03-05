public class Tile {
    public static  int NORMAL = 0;
    public static  int LAKE = 1;
    public static  int TRAP = 2;
    public static  int HOME_BASE = 3;
    
    private int type;
    private Piece piece;
    private Player owner; // Used for traps and home base ownership

    public Tile(int type) {
        this.type = type;
        this.piece = null;
        this.owner = null;
    }

    public int getType() {
        return type;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public boolean isOccupied() {
        return piece != null;
    }
}