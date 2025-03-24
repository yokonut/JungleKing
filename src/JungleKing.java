import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class JungleKing {
    public static void main(String[] args) {
        // Create the main frame
        MusicPlayer musicPlayer = new MusicPlayer();
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        
        musicPlayer.play();
        JFrame frame = new JFrame("Jungle King");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(450, 350); // Initial size for the menu

        // Create the board and display panel (but don’t show yet)
        Board board = new Board(player1, player2);
        EatingSoundEffect eatingSoundEffect = new EatingSoundEffect();
        MovingSoundEffect movingSoundEffect = new MovingSoundEffect();
        ErrorSoundEffect errorSoundEffect = new ErrorSoundEffect();
        ArrayDisplayPanel displayPanel = new ArrayDisplayPanel(board, errorSoundEffect);

        Game game = new Game(board, displayPanel, eatingSoundEffect, movingSoundEffect, errorSoundEffect, player1, player2);
        displayPanel.setGame(game); // 🔗 Link panel to game

        // Create the menu panel
        MenuPanel menuPanel = new MenuPanel();

        JLabel title = new JLabel("Welcome to Jungle King");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JToggleButton musicButton = new JToggleButton("MUTE");
        JButton startButton = new JButton("Start Game");
        JButton instructionsButton = new JButton("Instructions");
        JButton exitButton = new JButton("Exit");


       
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        musicButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        menuPanel.add(title);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        menuPanel.add(startButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(instructionsButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(exitButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(musicButton);
       

       

        frame.add(menuPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        // Button actions

        startButton.addActionListener((ActionEvent e) -> {
            frame.remove(menuPanel);
            frame.add(displayPanel);
            frame.pack(); // Resize frame to fit game board
            frame.setAlwaysOnTop(true);

            displayPanel.setFocusable(true);
            displayPanel.requestFocusInWindow();
        });

        instructionsButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(frame,
                    "Instructions:\n- Click on the piece to select!\n- Use WASD to move\n- Avoid traps!",
                    "Instructions", JOptionPane.INFORMATION_MESSAGE);
        });

        exitButton.addActionListener((ActionEvent e) -> System.exit(0));

        musicButton.addActionListener((ActionEvent e) -> {
            if (musicButton.isSelected()) {
                musicPlayer.stop();
                musicButton.setText("UNMUTE");
            } else {
                musicPlayer.play();
                musicButton.setText("MUTE");
            }
        });
    }
}
