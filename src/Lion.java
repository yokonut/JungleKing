public class Lion extends Piece {
    public Lion(int x, int y, Player owner, Board board) {
        super("Lion", x, y, owner, board);
    }

    @Override
    public boolean move(int newX, int newY) {
        if (board.isLake(newX, newY)) {
            if (x < newX) {
                board.movePiece(this, newX + 2, newY);
            } else if (x > newX) {
                board.movePiece(this, newX - 2, newY);
            } else if (y < newY) {
                board.movePiece(this, newX, newY + 3);
            } else if (y > newY) {
                board.movePiece(this, newX, newY - 3);
            }
            return true;
        } else if (board.isNormal(newX, newY)) {
            board.movePiece(this, newX, newY);
            return true;
        }
        return false;
    }
}