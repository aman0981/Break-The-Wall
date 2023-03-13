import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;

public class soundHandler {
    public static void runMusic(String path) {
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(path));
            Clip clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.loop(3);
        }catch(UnsupportedAudioFileException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } catch (LineUnavailableException e){
            e.printStackTrace();
        }
    }
}
