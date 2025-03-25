public class Lion extends Piece {
    public Lion(int x, int y, Player owner, Board board) {
        super("Lion", x, y, owner, board);
    }



    // Special move for Lion
    @Override
    public boolean move(int newX, int newY) {
        if (board.isLake(newX, newY)) {
            if (board.isRatInLakePath(x, y, newX, newY)) {
                System.out.println("Cannot jump over the lake. A Rat is in the way.");
                return false;
            }
            if (x < newX) {
                if(board.isOccupied(newX + 2, newY) && board.getPiece(newX + 2, newY).owner == owner) {
                    System.out.println("Cannot jump over your own piece.");
                    return false;
                }
                else
                board.movePiece(this, newX + 2, newY);
            } else if (x > newX) {
                if(board.isOccupied(newX - 2, newY) && board.getPiece(newX - 2, newY).owner == owner) {
                    System.out.println("Cannot jump over your own piece.");
                    return false;
                }
                else
                board.movePiece(this, newX - 2, newY);
            } else if (y < newY) {
                if(board.isOccupied(newX, newY + 3) && board.getPiece(newX, newY + 3).owner == owner) {
                    System.out.println("Cannot jump over your own piece.");
                    return false;
                }
                else
                board.movePiece(this, newX, newY + 3);
            } else if (y > newY) {
                if(board.isOccupied(newX, newY - 3) && board.getPiece(newX, newY - 3).owner == owner) {
                    System.out.println("Cannot jump over your own piece.");
                    return false;
                }
                else
                board.movePiece(this, newX, newY - 3);
            }
            return true;
        } else if (board.isNormal(newX, newY) || board.isOpponentHomeBase(newX, newY, owner) || board.isTrap(newX, newY)) {
            board.movePiece(this, newX, newY);
            return true;
        }
        return false;
    }
}