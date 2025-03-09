public class Tiger extends Piece {
    public Tiger(int x, int y, Player owner, Board board) {
        super("Tiger", x, y, owner, board);
    }


    // Special move for Tiger
    @Override
    public boolean move(int newX, int newY) {
        if (board.isLake(newX, newY)) {
            if (board.isRatInLakePath(x, y, newX, newY)) {
                System.out.println("Cannot jump over the lake. A Rat is in the way.");
                return false;
            }
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
        } else if (board.isNormal(newX, newY) || board.isOpponentHomeBase(newX, newY, owner)) {
            board.movePiece(this, newX, newY);
            return true;
        }
        return false;
    }
}