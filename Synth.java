import com.jsyn.Synthesizer;
import com.jsyn.data.FloatSample;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.FixedRateMonoWriter;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.MixerMono;
import com.jsyn.util.WaveRecorder;
import com.softsynth.shared.time.TimeStamp;

import java.io.FileWriter;
import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by alexmann on 14/08/2015.
 */
public class Synth extends Circuit {

    public Synth () {
    }

    public void getWaveform(boolean realtime, Synthesizer synth, MixerMono mixer) {
        FloatSample samples = new FloatSample((int) Math.floor(Project.lengthInS * 44100), 1);
        if (realtime) {
            try {
                File waveFile = new File("1Sawtooth440Hz-LP1000Hz.wav");
                WaveRecorder recorder = new WaveRecorder(synth, waveFile, 1);
                LineOut lineOut = new LineOut();
                synth.add(lineOut);
                mixer.output.connect(0, lineOut.input, 0);
                mixer.output.connect(0, lineOut.input, 1);
                mixer.output.connect(recorder.getInput());
                lineOut.start();
                recorder.start();
                Thread.sleep((long) Project.lengthInS * 1000);
                lineOut.stop();
                recorder.stop();
                recorder.close();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else {
            try {

                FixedRateMonoWriter writer = new FixedRateMonoWriter();
                synth.add(writer);
                mixer.output.connect(writer.input);
                writer.dataQueue.queue(samples);
                synth.setRealTime(false);
                System.out.println("Start");
                writer.start();
                synth.sleepUntil(synth.getCurrentTime() + Project.lengthInS + 1);
                writer.stop();

                System.out.println("Stop");
                System.out.println("NumFrames = " + samples.getNumFrames());
                candidateSamples = new float[samples.getNumFrames() - 1];
                samples.read(1, candidateSamples, 0, candidateSamples.length);
                FileWriter write = new FileWriter("candidateOutput.txt", false);
                PrintWriter printLine = new PrintWriter(write);
                for (int i = 0; i < candidateSamples.length; i++) {
                    printLine.println(candidateSamples[i]);
                }
                printLine.close();

                System.out.println(candidateSamples[44097]);
                System.out.println(candidateSamples[44098]);


            } catch (InterruptedException ie) {
                ie.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public double frequency;
    public float[] candidateSamples;
}
