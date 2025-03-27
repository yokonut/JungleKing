
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Tile {
    
    private String type;
    private Piece piece;
    private Player owner;
    private Image image;

    /**
     * Constructs a new Tile with the specified type.
     *
     * @param type the type of the tile (NORMAL, LAKE, TRAP, HOME_BASE)
     */
    public Tile(String type) {
        this.type = type;
        this.piece = null; // No piece on the tile
        this.owner = null; // No owner of the tile
        loadImage();
    }


    /**
     * Loads the image for the tile based on its type.
     */
    private void loadImage() {
        try {
            this.image = ImageIO.read(new File("c:/users/yohan/Desktop/JUNGLE KING/JungleKing/src/images/" + type + ".png"))
                        .getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            System.out.println("Error loading image for tile: " + type);
        }
    }


    /**
     * Gets the image of the tile.
     *
     * @return the image of the tile
     */
    public Image getImage() {
        return image;
    }

    /**
     * Gets the type of the tile.
     *
     * @return the type of the tile
     */
    public String getType() {
        return type;
    }


    /**
     * Gets the piece on the tile.
     *
     * @return the piece on the tile, or null if the tile is empty
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Places a piece on the tile.
     *
     * @param piece the piece to place on the tile
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }


    /**
     * Gets the owner of the tile.
     *
     * @return the owner of the tile, or null if the tile has no owner
     */
    public Player getOwner() {
        return owner;
    }


    /**
     * Sets the owner of the tile.
     *
     * @param owner the owner of the tile
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    
    /**
     * Checks if the tile is occupied by a piece.
     *
     * @return true if the tile is occupied by a piece, false otherwise
     */
    public boolean isOccupied() {
        return piece != null;
    }
}