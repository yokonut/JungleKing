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
        WaterSplashEffect waterSplashEffect = new WaterSplashEffect();
        WinSoundEffect winSoundEffect = new WinSoundEffect();
        SelectSoundEffect selectSoundEffect = new SelectSoundEffect();
        DecideFirstPlayer decideFirstPlayer = new DecideFirstPlayer();
        ArrayDisplayPanel displayPanel = new ArrayDisplayPanel(board, errorSoundEffect, waterSplashEffect, selectSoundEffect);
        MenuPanel menuPanel = new MenuPanel();
        Game game = new Game(board, displayPanel, eatingSoundEffect, movingSoundEffect, errorSoundEffect, player1, player2, winSoundEffect, frame, menuPanel);
        
        displayPanel.setGame(game);

        // Create the menu panel

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
            // Create a fresh board and display panel
            Board newBoard = new Board(player1, player2);
            ArrayDisplayPanel newDisplayPanel = new ArrayDisplayPanel(newBoard, errorSoundEffect, waterSplashEffect, selectSoundEffect);
            Game newGame = new Game(newBoard, newDisplayPanel, eatingSoundEffect, movingSoundEffect,
                                    errorSoundEffect, player1, player2, winSoundEffect, frame, menuPanel);
        
            newDisplayPanel.setGame(newGame);
        
            // Swap panels
            frame.remove(menuPanel);
            frame.add(displayPanel);
            frame.pack();
            frame.setAlwaysOnTop(true);
        
            // Make it focusable for key input
            newDisplayPanel.setFocusable(true);
            newDisplayPanel.requestFocusInWindow();
        });

        instructionsButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(frame,
                    "Instructions:\n- Click on the piece to select!\n- Use WASD or ARROW KEYS to move\n- Avoid traps!",
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
