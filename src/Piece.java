public abstract class Piece {
    protected String name;
    protected int x, y;
    protected Player owner;
    protected Board board;

    public Piece(String name, int x, int y, Player owner, Board board) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.owner = owner;
        this.board = board;
    }

    public boolean move(int newX, int newY) {
        if (board.isNormal(newX, newY)) {
            board.movePiece(this, newX, newY);
            return true;
        }
        return false;
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