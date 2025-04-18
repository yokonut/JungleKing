public class Rat extends Piece {
    public Rat(int x, int y, Player owner, Board board) {
        super("Rat", x, y, owner, board);
    }


    // Special move for Rat
    @Override
    public boolean move(int newX, int newY) {
        if (board.isNormal(newX, newY) || board.isLake(newX, newY) || board.isOpponentHomeBase(newX, newY, owner)|| board.isTrap(newX, newY)) {
            board.movePiece(this, newX, newY);
            return true;
        }
        return false;
    }
}