public class Elephant extends Piece {
    public Elephant(int x, int y, Player owner, Board board) {
        super("Elephant", x, y, owner, board);
    }

    @Override
    public boolean move(int newX, int newY) {
        if (board.isNormal(newX, newY)) {
            board.movePiece(this, newX, newY);
            return true;
        }
        return false;
    }
}