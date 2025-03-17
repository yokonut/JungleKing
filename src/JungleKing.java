// Main Class

import javax.swing.*;

public class JungleKing {
    public static void main(String[] args) {



       
            Board board = new Board();
            JFrame frame = new JFrame("Jungle King Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new ArrayDisplayPanel(board));
            frame.pack();
            frame.setLocationRelativeTo(null); // Center the frame on the screen
            frame.setVisible(true);
      



        Game game = new Game();
        game.start();
    }
}
