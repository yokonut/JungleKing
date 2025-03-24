import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class WaterSplashEffect {
    private Clip clip;

    public void play() {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("c:/users/yohan/Desktop/JUNGLE KING/JungleKing/src/sounds/water.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f); // Reduce volume by 10 decibels
 
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error playing music: " + e.getMessage());
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
