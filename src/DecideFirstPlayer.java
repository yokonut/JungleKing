import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DecideFirstPlayer extends JPanel {

    private final int boxSize = 50;
    private final int gap = 5;
    private final int numBoxes = 7;
    private int selectedBoxIndex = -1; // -1 = nothing selected

    public DecideFirstPlayer() {
        setPreferredSize(new Dimension(450, 350));

        // Add mouse listener ONCE here
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });
    }

    private void handleClick(int mouseX, int mouseY) {
        int totalWidth = (boxSize * numBoxes) + (gap * (numBoxes - 1));
        int startX = (getWidth() - totalWidth) / 2;
        int y = 125;

        for (int i = 0; i < numBoxes; i++) {
            int x = startX + i * (boxSize + gap);
            Rectangle boxBounds = new Rectangle(x, y, boxSize, boxSize);
            if (boxBounds.contains(mouseX, mouseY)) {
                selectedBoxIndex = i; // store which box was clicked
                repaint(); // trigger repaint to update the image
                break;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Load images
        Image background = new ImageIcon("C:/Users/yohan/Desktop/JUNGLE KING/JungleKing/src/images/background.png").getImage();
        Image mystery = new ImageIcon("C:/Users/yohan/Desktop/JUNGLE KING/JungleKing/src/images/box.png").getImage();
        Image selected = new ImageIcon("C:/Users/yohan/Desktop/JUNGLE KING/JungleKing/src/images/box1.png").getImage();

        // Draw background
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

        // Draw boxes
        int totalWidth = (boxSize * numBoxes) + (gap * (numBoxes - 1));
        int startX = (getWidth() - totalWidth) / 2;
        int y = 125;

        for (int i = 0; i < numBoxes; i++) {
            int x = startX + i * (boxSize + gap);
            if (i == selectedBoxIndex) {
                g.drawImage(selected, x, y, boxSize, boxSize, null);
            } else {
                g.drawImage(mystery, x, y, boxSize, boxSize, null);
            }
        }
    }
}
