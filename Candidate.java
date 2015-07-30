import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.data.FloatSample;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.*;
import com.jsyn.util.SampleLoader;
import com.jsyn.util.WaveRecorder;

import java.io.*;

/**
 * Created by alexmann on 29/07/2015.
 */
public class Candidate {

    public static float[] generateCandidate(double cutoffFreq, double qValue) {

        Synthesizer synth = JSyn.createSynthesizer();

        synth.start();

        Circuit c = new Circuit();
        c.add(sawOsc = new SawtoothOscillator() );
        c.add(myFilter = new FilterLowPass() );

        c.addPort(frequency = sawOsc.frequency);
        c.addPort(amplitude = sawOsc.amplitude);

        c.addPort(cutoff = myFilter.frequency);
        c.addPort(qFactor = myFilter.Q);

        sawOsc.output.connect(myFilter.input);

        frequency.set(440);
        amplitude.set(1.0);
        cutoff.set(cutoffFreq);
        qFactor.set(qValue);



        LineOut lineOut = new LineOut();

        synth.add(sawOsc);
        synth.add(c);
        synth.add(lineOut);

        System.out.println("Connecting");
        mixer = new MixerMono(1);
        myFilter.output.connect(0, mixer.input, 0);
        mixer.output.connect(0, lineOut.input, 0);
        mixer.output.connect(0, lineOut.input, 1);
        System.out.println("Connected");

//        myFilter.output.connect(0, lineOut.input, 0);

        try{
            File candidateFile = new File("candidate.wav");
            WaveRecorder recorder = new WaveRecorder(synth, candidateFile, 1);
            sawOsc.output.connect(0, recorder.getInput(), 0);

            lineOut.start();
            recorder.start();
            Thread.sleep(Project.lengthInMS);
            recorder.stop();
            recorder.close();
            lineOut.stop();

            Thread.sleep(200);


            File readFile = new File("candidate.wav");
            FloatSample fileSamples = SampleLoader.loadFloatSample(readFile);
            candidateSamples = new float[fileSamples.getNumFrames()];
            System.out.println("CandidateSmaples.length = " + candidateSamples.length);
            fileSamples.read(candidateSamples);


            FileWriter write = new FileWriter("candidateOutput.txt", false);
            PrintWriter printLine = new PrintWriter(write);
            for (int i = 0; i < candidateSamples.length; i++) {
                printLine.println(candidateSamples[i]);
            }
            printLine.close();

        }
        catch (FileNotFoundException nfne) {
            nfne.printStackTrace();
        }
        catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return candidateSamples;
    }

    /**
     * Normalises the waveforms by dividing each individual sample by the largest sample in its array, then
     * performs the comparison function
     *
     * @param targetSamples A float array of samples imported from the .wav file of the target sound
     * @param candidateSamples A float array of samples generated with the JSyn API using the candidate parameters
     * @return The deviation between the two waveforms
     */
    public static double normaliseAndCompare(float[] targetSamples, float[] candidateSamples) {


        // Iterate through both arrays and set each MaxFloat value to the highest value in each array
        float targetMaxFloat = 0;
        float candidateMaxFloat = 0;
        for (int i = 0; i<targetSamples.length && i<candidateSamples.length; i++) {

            if (targetSamples[i] > targetMaxFloat)
                targetMaxFloat = targetSamples[i];

            if (candidateSamples[i] > candidateMaxFloat)
                candidateMaxFloat = candidateSamples[i];
        }


        double runningTotal = 0;
        for (int i = 0; i < targetSamples.length && i < candidateSamples.length; i++) {

            // Normalise each float value as it is passed in the iteration
            float candidateSample = (candidateSamples[i]/candidateMaxFloat);
            float targetSample = (targetSamples[i]/targetMaxFloat);

            // Comparison function
            runningTotal += (((candidateSample) - targetSample) * (candidateSample - targetSample));
            
        }
        double deviationValue = (Math.sqrt(runningTotal));
        System.out.println(Math.sqrt(runningTotal));

        return deviationValue;
    }


    static float[] candidateSamples;

    //Declare Units
    private static SawtoothOscillator sawOsc;
    private static FilterLowPass myFilter;
    private static MixerMono mixer;

    //Declare ports.
    private static UnitInputPort frequency;
    private static UnitInputPort amplitude;
    private static UnitInputPort cutoff;
    private static UnitInputPort qFactor;
}
