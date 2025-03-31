
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataStorage implements Serializable {
  private static final long serialVersionUID = 1L;

  public Player currentPlayer; // Add currentPlayer field
  public Player player1; // Add player1 field - is this needed
  public Player player2; // Add player2 field - is this needed
  public List<Piece> pieces = new ArrayList<>(); // List to store piece data

  /* PIECE CLASS - placements of pieces and owner */
  /*
   * public Piece(String name, int x, int y, Player owner, Board board) {
   * this.name = name;
   * this.x = x;
   * this.y = y;
   * this.owner = owner;
   * this.board = board;
   * loadImage(this.name);
   * }
   */
}