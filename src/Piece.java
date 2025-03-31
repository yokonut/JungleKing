import java.awt.Image;
//import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

/**
 * The Piece class represents a game piece in the Jungle King game.
 * Each piece has a name, coordinates (x, y), an owner (Player), and a reference
 * to the game board.
 */
public class Piece implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String name;
    protected int x, y;
    protected Player owner;
    protected Board board; // should this be transient
    protected transient Image image;

    /**
     * Constructs a new Piece with the specified name, coordinates, owner, and
     * board.
     *
     * @param name  the name of the piece
     * @param x     the x-coordinate of the piece
     * @param y     the y-coordinate of the piece
     * @param owner the owner of the piece
     * @param board the game board the piece is on
     * 
     */

    public Piece(String name, int x, int y, Player owner, Board board) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.owner = owner;
        this.board = board;
        loadImage(this.name);
    }

    /**
     * Loads the image for the piece based on its name and owner.
     *
     * @param name the name of the piece
     */
    void loadImage(String name) {
        String filename = name.toLowerCase();

        // If the piece belongs to Player 2, use the "piece2.png" version
        if (owner.getName().equals("Player 2")) {
            filename += "2"; // Example: "cat2.png", "elephant2.png"
        }

        try {
            /*
             * this.image = ImageIO
             * .read(new File("C:/Users/silus/Desktop/CCPROG3/MC02/JUNGLE-KING-IMAGES/" +
             * filename + ".png"))
             * .getScaledInstance(50, 50, Image.SCALE_SMOOTH);
             */

            this.image = ImageIO
                    .read(getClass().getResource("/images/" + filename + ".png"))
                    .getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            System.out.println("Error loading image for " + filename);
        }
    }

    /**
     * Gets the image of the piece.
     *
     * @return the image of the piece
     */
    public Image getImage() {
        return image;
    }

    /**
     * Moves the piece to a new location if the move is valid.
     *
     * @param newX the new x-coordinate
     * @param newY the new y-coordinate
     * @return true if the move is successful, false otherwise
     */
    public boolean move(int newX, int newY) {
        if (board.isNormal(newX, newY) || board.isOpponentHomeBase(newX, newY, owner) || board.isTrap(newX, newY)) {
            board.movePiece(this, newX, newY);
            return true;
        }
        return false;
    }

    /**
     * Gets the name of the piece.
     *
     * @return the name of the piece
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the owner of the piece.
     *
     * @return the owner of the piece
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Gets the x-coordinate of the piece.
     *
     * @return the x-coordinate of the piece
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the piece.
     *
     * @return the y-coordinate of the piece
     */
    public int getY() {
        return y;
    }

    /**
     * Custom deserialization to reload transient fields.
     */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // Deserialize non-transient fields
        loadImage(this.name); // Reload the image
    }

}