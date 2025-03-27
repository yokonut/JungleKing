import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class DecideFirstPlayer extends JPanel {

    private final int boxSize = 50;
    private final int gap = 5;
    private final int numBoxes = 7;

    private int selectedBoxIndex = -1;
    private int currentPlayerTurn = 1;
    private boolean selectionComplete = false;

    private final SelectSoundEffect selectSoundEffect;
    private final Game game;
    private final Map<String, Integer> pieceHierarchy;
    private final JFrame frame;
    private final ArrayDisplayPanel displayPanel;

    private final String[] animalPieces;
    private final String[] boxAssignments = new String[numBoxes];
    private final String[] playerSelections = new String[2];

    public DecideFirstPlayer(SelectSoundEffect selectSoundEffect, Game game, JFrame frame, ArrayDisplayPanel displayPanel) {
        this.selectSoundEffect = selectSoundEffect;
        this.game = game;
        this.pieceHierarchy = game.getPieceHierarchy();
        this.frame = frame;
        this.displayPanel = displayPanel;
        setPreferredSize(new Dimension(450, 350));

        // Get animal names from hierarchy
        this.animalPieces = pieceHierarchy.keySet().toArray(new String[0]);

        // Shuffle and assign animals to boxes
        List<String> shuffled = new ArrayList<>(Arrays.asList(animalPieces));
        Collections.shuffle(shuffled);
        for (int i = 0; i < numBoxes; i++) {
            boxAssignments[i] = shuffled.get(i); // assigning shuffled pieces to the mystery boxes
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });
    }

    private void handleClick(int mouseX, int mouseY) {
        if (selectionComplete)
            return;

        int totalWidth = (boxSize * numBoxes) + (gap * (numBoxes - 1));
        int startX = (getWidth() - totalWidth) / 2;
        int y = 125;

        for (int i = 0; i < numBoxes; i++) {
            int x = startX + i * (boxSize + gap);
            Rectangle boxBounds = new Rectangle(x, y, boxSize, boxSize);
            if (boxBounds.contains(mouseX, mouseY) && boxAssignments[i] != null) {

                String picked = boxAssignments[i];
                boxAssignments[i] = null; // Mark box as used
                playerSelections[currentPlayerTurn - 1] = picked;
                selectedBoxIndex = i;
                selectSoundEffect.play();
                repaint();

                System.out.println("Player " + currentPlayerTurn + " picked: " + picked);

                if (currentPlayerTurn == 1) {
                    currentPlayerTurn = 2;
                } else {
                    selectionComplete = true;
                    decideWinner();
                    frame.remove(this);
                    frame.add(displayPanel);
                    frame.pack();
                    frame.setAlwaysOnTop(true);
                    reset();

                }

                break;
            }
        }
    }

    private void decideWinner() {
        String piece1 = playerSelections[0];
        String piece2 = playerSelections[1];

        int rank1 = pieceHierarchy.get(piece1);
        int rank2 = pieceHierarchy.get(piece2);

        String winnerMessage;
        if (rank1 > rank2) {
            game.setCurrentPlayer(game.getPlayer1());
            winnerMessage = "Player 1 goes first!";
        } else if (rank2 > rank1) {
            game.setCurrentPlayer(game.getPlayer2());
            winnerMessage = "Player 2 goes first!";
        } else {
            game.setCurrentPlayer(game.getPlayer1()); // Tie-breaker rule
            winnerMessage = "It's a tie! Player 1 goes first by default.";
        }

        JOptionPane.showMessageDialog(this,
                "Player 1 picked: " + piece1 + "\n" +
                        "Player 2 picked: " + piece2 + "\n\n" +
                        winnerMessage);

    }

    public void reset() {
        currentPlayerTurn = 1;
        selectionComplete = false;
        selectedBoxIndex = -1;
    
        // Clear player picks
        playerSelections[0] = null;
        playerSelections[1] = null;
    
        // Shuffle and reassign animal pieces
        List<String> shuffled = new ArrayList<>(Arrays.asList(animalPieces));
        Collections.shuffle(shuffled);
        for (int i = 0; i < numBoxes; i++) {
            boxAssignments[i] = shuffled.get(i);
        }
    
        repaint();
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Load images
        Image background = new ImageIcon("C:/Users/yohan/Desktop/JUNGLE KING/JungleKing/src/images/background.png")
                .getImage();
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
