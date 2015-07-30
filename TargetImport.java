import com.jsyn.data.FloatSample;
import com.jsyn.util.SampleLoader;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
//import SynthGen.*;

/**
 * Created by alexmann on 06/07/2015.
 */
public class TargetImport extends Thread {


    public static long getSoundLength(File targetFile) {

        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(targetFile);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AudioFormat format = audioInputStream.getFormat();
        long audioFileLength = targetFile.length();
        System.out.println(audioFileLength);
        int frameSize = format.getFrameSize();
        float frameRate = format.getFrameRate();
        float durationInSeconds = (float) Math.floor((audioFileLength / (frameSize * frameRate)));
        long durationInMillieconds = (long) (1000 * durationInSeconds);

        System.out.println("Length = " + durationInSeconds);

        return durationInMillieconds;
    }

    public static float[] getTargetSamples(File targetFile) {
        try {
//            File file = new File("440Hz-SAW.wav");
            FloatSample fileSamples = SampleLoader.loadFloatSample(targetFile);
            targetSamples = new float[fileSamples.getNumFrames()];
            fileSamples.read(1, targetSamples, 0, targetSamples.length-1);


            FileWriter write = new FileWriter("targetOutput.txt", false);
            PrintWriter printLine = new PrintWriter(write);
            for (int i = 0; i < targetSamples.length; i++) {
                printLine.println(targetSamples[i]);
            }
            printLine.close();


        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return targetSamples;

    }


    private static float[] targetSamples = null;
    private static short[] candidateSamples = null;

}
