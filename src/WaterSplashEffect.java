import javax.sound.sampled.*;
//import java.io.File;
import java.io.IOException;

public class WaterSplashEffect {
    private Clip clip;

    public void play() {
        try {
            AudioInputStream audioStream = AudioSystem
                    .getAudioInputStream(getClass().getResource("/sounds/water.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(+6.0f); // Reduce volume by 10 decibels

            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }

}
