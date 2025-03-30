import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class MenuPanel extends JPanel {
    private BufferedImage backgroundImage;

    /**
     * Constructs a new MenuPanel and initializes the background image.
     */
    public MenuPanel() {
        try {
            /*
             * backgroundImage = ImageIO
             * .read(new
             * File("C:/Users/silus/Desktop/CCPROG3/MC02/JUNGLE-KING-IMAGES/background.png")
             * );
             */

            backgroundImage = ImageIO
                    .read(getClass().getResource("/images/background.png"));
        } catch (IOException e) {
            System.out.println("Failed to load menu background: " + e.getMessage());
        }
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
