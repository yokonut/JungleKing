
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * The DataStorage class is responsible for storing the game state,
 * including the current player, players, and pieces.
 */

public class DataStorage implements Serializable {
  private static final long serialVersionUID = 1L;

  public Player currentPlayer;
  public Player player1;
  public Player player2;
  public List<Piece> pieces = new ArrayList<>(); // List to store piece data

}