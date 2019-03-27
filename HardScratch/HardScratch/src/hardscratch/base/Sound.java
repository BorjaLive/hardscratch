package hardscratch.base;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class Sound extends Thread{
    
    private String filename;
    private final int BUFFER_SIZE = 128000;
    private boolean playing;

    public Sound(String filename){
        this.filename = filename;
        start();
    }
    
    @Override
    public void run() {
        AudioInputStream audioStream;
        AudioFormat audioFormat;
        SourceDataLine sourceLine;

        try {
            audioStream = AudioSystem.getAudioInputStream(new File(filename));
            audioFormat = audioStream.getFormat();
            sourceLine = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, audioFormat));
            sourceLine.open(audioFormat);

            sourceLine.start();
            playing = true;
            int nBytesRead = 0;
            byte[] abData = new byte[BUFFER_SIZE];
            while (nBytesRead != -1 && playing) {
                nBytesRead = audioStream.read(abData, 0, abData.length);
                if(nBytesRead > 0)
                    sourceLine.write(abData, 0, nBytesRead);
            }

            sourceLine.drain();
            sourceLine.close();

            playing = false;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public boolean isPlaying(){
        return playing;
    }
    public void stopPlaying(){
        playing = false;
    }
    
}
