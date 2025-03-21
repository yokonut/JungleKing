import javax.swing.*;

public class JungleKing {
    public static void main(String[] args) {
        Board board = new Board(); // Create ONE board instance
        ArrayDisplayPanel displayPanel = new ArrayDisplayPanel(board); // Use the same board
        Game game = new Game(board,displayPanel); // Pass it to Game

        // Create the GUI
        JFrame frame = new JFrame("Jungle King Board");
        frame.add(displayPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);

        // Start the game
        game.start();
    }
}
