import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class ErrorSoundEffect {
    private Clip clip;

    public void play() {
        try {

            /*
             * AudioInputStream audioStream = AudioSystem
             * .getAudioInputStream(new
             * File("C:/Users/silus/Desktop/CCPROG3/MC02/JUNGLE-KING-SOUNDS/error.wav"));
             */

            AudioInputStream audioStream = AudioSystem
                    .getAudioInputStream(getClass().getResource("/sounds/error.wav"));

            clip = AudioSystem.getClip();
            clip.open(audioStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(+.10f); // Increase volume by 10 decibels

            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }

}
