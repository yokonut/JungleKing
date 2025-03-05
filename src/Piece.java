public class Piece {
    protected String name;
    protected int x, y;
    protected Player owner;

    public Piece(String name, int x, int y, Player owner) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.owner = owner;
    }

    public boolean isValidMove(int newX, int newY, Board board) {
        // Ensure the position is within board bounds
        if (newX < 0 || newX >= 7 || newY < 0 || newY >= 9) {
            return false;
        }


        // Default movement: One step up/down/left/right
        int dx = Math.abs(newX - x);
        int dy = Math.abs(newY - y);
        return (dx + dy == 1);
    }

    public void move(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    public String getName() {
        return name;
    }

    public Player getOwner() {
        return owner;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
