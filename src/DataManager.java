import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class DataManager implements Serializable {
  private static final long serialVersionUID = 1L; // Added for serialization

  private Game gp; // Reference to the Game object
  private Piece piece; // Reference to the Piece object

  /*
   * private final int WIDTH = 9;
   * private final int HEIGHT = 7;
   * private final Board board;
   */

  private ErrorSoundEffect errorSoundEffect;
  private SelectSoundEffect selectSoundEffect;
  private WaterSplashEffect waterSplashEffect;
  private EatingSoundEffect eatingSoundEffect;
  private MovingSoundEffect movingSoundEffect;
  private WinSoundEffect winSoundEffect;

  private JFrame frame;
  private JMenuBar menuBar;
  private JPanel menuPanel;

  public void saveLoad(Game gp) {
    this.gp = gp; // make it equal to the game
  }

  public void save() {
    try {
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("savegame.dat")));
      DataStorage ds = new DataStorage();

      // so we pass serializable data
      ds.currentPlayer = gp.currentPlayer; // Pass currentPlayer
      ds.player1 = gp.player1; // Pass player1
      ds.player2 = gp.player2; // Pass player2

      // Save pieces
      Tile[][] grid = gp.getBoard().getGrid(); // Access the grid through the Board instance
      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[i].length; j++) {
          Piece piece = grid[i][j].getPiece(); // Get the piece on the tile
          if (piece != null) {
            ds.pieces.add(piece); // Add the piece to DataStorage
          }
        }
      }
      /*
       * // Save pieces - CHECK THIS FIRST
       * for (int i = 0; i < 7; i++) {
       * for (int j = 0; j < 9; j++) {
       * Piece piece = Board.grid[i][j].getPiece(); // Get the piece on the tile
       * if (piece != null) {
       * // Add piece data to DataStorage
       * ds.pieces.add(piece);
       * }
       * }
       * }
       */

      // write theDataStoage Object
      oos.writeObject(ds);
      oos.close();
      System.out.println("Game saved successfully.");
    } catch (Exception e) {
      System.out.println("Error saving game: " + e.getMessage());
      e.printStackTrace(); // trace error path
    }
  }

  public void setDependencies(JFrame frame, JMenuBar menuBar, JPanel menuPanel) {
    this.frame = frame;
    this.menuBar = menuBar;
    this.menuPanel = menuPanel;
  }

  /* load data function */

  public Game load() {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("savegame.dat")))) {
      // Read data storage object
      DataStorage ds = (DataStorage) ois.readObject();
      gp.player1 = ds.player1;
      gp.player2 = ds.player2;
      gp.currentPlayer = ds.currentPlayer;

      // Create a fresh board
      Board newBoard = new Board(gp.player1, gp.player2);

      // Clear the board before placing loaded pieces
      newBoard.clearBoard();

      // Restore pieces on the board
      for (Piece piece : ds.pieces) {
        newBoard.placePiece(piece); // Place the piece on the board
      }

      // Create a new game instance
      ArrayDisplayPanel newDisplayPanel = new ArrayDisplayPanel(newBoard, errorSoundEffect, waterSplashEffect,
          selectSoundEffect);
      Game newGame = new Game(newBoard, newDisplayPanel, eatingSoundEffect, movingSoundEffect,
          errorSoundEffect, gp.player1, gp.player2, winSoundEffect, null, null, false);

      newDisplayPanel.setGame(newGame);

      System.out.println("Game loaded successfully.");
      return newGame; // Return the new game instance
    } catch (Exception e) {
      System.out.println("Error loading game: " + e.getMessage());
      e.printStackTrace(); // Trace error path
      return null; // Return null if an error occurs
    }
  }
}
