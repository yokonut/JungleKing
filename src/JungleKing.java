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

    JMenuBar menuBar = new JMenuBar();

    // Create menus
    JMenu fileMenu = new JMenu("File");
    JMenu helpMenu = new JMenu("Help");
    JMenu muteMenu = new JMenu("Sound");

    // Create menu items
    JMenuItem newGameItem = new JMenuItem("New Game");
    JMenuItem saveGameIteam = new JMenuItem("Save Game"); // NEW
    JMenuItem exitItem = new JMenuItem("Exit");
    JMenuItem instructionsItem = new JMenuItem("Instructions");
    JMenuItem muteItem = new JMenuItem("Mute");

    // Add menu items to menus
    fileMenu.add(newGameItem);
    fileMenu.add(saveGameIteam); // NEW
    fileMenu.add(exitItem);
    helpMenu.add(instructionsItem);
    muteMenu.add(muteItem);

    // Add menus to the menu bar
    menuBar.add(fileMenu);
    menuBar.add(helpMenu);
    menuBar.add(muteMenu);

    // Create the board and display panel (but don’t show yet)
    Board board = new Board(player1, player2);
    EatingSoundEffect eatingSoundEffect = new EatingSoundEffect();
    MovingSoundEffect movingSoundEffect = new MovingSoundEffect();
    ErrorSoundEffect errorSoundEffect = new ErrorSoundEffect();
    WaterSplashEffect waterSplashEffect = new WaterSplashEffect();
    WinSoundEffect winSoundEffect = new WinSoundEffect();
    SelectSoundEffect selectSoundEffect = new SelectSoundEffect();
    ArrayDisplayPanel displayPanel = new ArrayDisplayPanel(board, errorSoundEffect, waterSplashEffect,
        selectSoundEffect);
    MenuPanel menuPanel = new MenuPanel();
    Game game = new Game(board, displayPanel, eatingSoundEffect, movingSoundEffect, errorSoundEffect, player1,
        player2, winSoundEffect, frame, menuPanel);
    DecideFirstPlayer decideFirstPlayer = new DecideFirstPlayer(selectSoundEffect, game, frame, displayPanel);

    displayPanel.setGame(game);

    // Create the menu panel

    JLabel title = new JLabel("Welcome to Jungle King");
    title.setAlignmentX(Component.CENTER_ALIGNMENT);
    title.setFont(new Font("Arial", Font.BOLD, 24));

    JToggleButton musicButton = new JToggleButton("Mute");
    JButton startButton = new JButton("Start Game");
    JButton loadButton = new JButton("Load Game"); // n
    JButton instructionsButton = new JButton("Instructions");
    JButton exitButton = new JButton("Exit");

    startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    instructionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    musicButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    menuPanel.add(title);
    menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));
    menuPanel.add(startButton);
    menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    menuPanel.add(loadButton); // NEW
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

    // initialize music
    musicButton.setSelected(false); // Assume music is playing initially
    musicButton.setText("Mute");
    muteItem.setText("Mute");

    startButton.addActionListener((ActionEvent e) -> {
      // Create a fresh board and display panel
      Board newBoard = new Board(player1, player2);
      ArrayDisplayPanel newDisplayPanel = new ArrayDisplayPanel(newBoard, errorSoundEffect, waterSplashEffect,
          selectSoundEffect);
      Game newGame = new Game(newBoard, newDisplayPanel, eatingSoundEffect, movingSoundEffect,
          errorSoundEffect, player1, player2, winSoundEffect, frame, menuPanel);

      newDisplayPanel.setGame(newGame);
      DecideFirstPlayer newDecidePanel = new DecideFirstPlayer(selectSoundEffect, newGame, frame,
          newDisplayPanel);

      // Swap panels
      frame.getContentPane().removeAll();
      frame.add(newDecidePanel);
      frame.pack();
      frame.setAlwaysOnTop(true);
      // Set the menu bar to the frame
      frame.setJMenuBar(menuBar);

      // Focus input
      newDisplayPanel.setFocusable(true);
      newDisplayPanel.requestFocusInWindow();

    });

    loadButton.addActionListener((ActionEvent e) -> {
      // Load game logic here
      JOptionPane.showMessageDialog(frame, "Load Game feature is not implemented yet.", "Load Game",
          JOptionPane.INFORMATION_MESSAGE);
    });

    instructionsButton.addActionListener((ActionEvent e) -> {
      JOptionPane.showMessageDialog(frame,
          "Instructions:\n- Click on the piece to select!\n- Use MOUSE, WASD or ARROW KEYS to move\n- Avoid traps!",
          "Instructions", JOptionPane.INFORMATION_MESSAGE);
    });

    exitButton.addActionListener((ActionEvent e) -> System.exit(0));

    musicButton.addActionListener((ActionEvent e) -> {
      if (musicButton.isSelected()) {
        musicPlayer.stop();
        musicButton.setText("Unmute");
        muteItem.setText("Unmute"); // Update muteItem text
      } else {
        musicPlayer.play();
        musicButton.setText("Mute");
        muteItem.setText("Mute"); // Update muteItem text
      }
    });

    // Menu item actions
    newGameItem.addActionListener((ActionEvent e) -> {
      /*
       * ADD LOGIC HERE
       */

      JOptionPane.showMessageDialog(frame, "New Game feature is not implemented yet.", "New Game",
          JOptionPane.INFORMATION_MESSAGE);
    });

    saveGameIteam.addActionListener((ActionEvent e) -> {
      /*
       * ADD LOGIC HERE
       */
      JOptionPane.showMessageDialog(frame, "Save Game feature is not implemented yet.", "Save Game",
          JOptionPane.INFORMATION_MESSAGE);
    });

    exitItem.addActionListener((ActionEvent e) -> System.exit(0));

    instructionsItem.addActionListener((ActionEvent e) -> {
      JOptionPane.showMessageDialog(frame,
          "Instructions:\n- Click on the piece to select!\n- Use MOUSE, WASD or ARROW KEYS to move\n- Avoid traps!",
          "Instructions", JOptionPane.INFORMATION_MESSAGE);
    });

    muteItem.addActionListener((ActionEvent e) -> {
      if (muteItem.getText().equals("Mute")) {
        musicPlayer.stop();
        musicButton.setSelected(true);
        musicButton.setText("Unmute");
        muteItem.setText("Unmute");
      } else {
        musicPlayer.play();
        musicButton.setSelected(false);
        musicButton.setText("Mute");
        muteItem.setText("Mute");
      }
    });
  }
}