import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.data.FloatSample;
import com.jsyn.instruments.SubtractiveSynthVoice;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.*;
import com.jsyn.util.SampleLoader;
import com.jsyn.util.WaveRecorder;
import com.softsynth.shared.time.TimeStamp;

import java.io.*;
import java.nio.file.Files;

/**
 * Created by alexmann on 29/07/2015.
 */
public class Candidate {

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
    private static Circuit c;
    private static MixerMono mixer;
    private static MixerMono preMixer;

    //Declare ports.
    private static UnitInputPort frequency;
    private static UnitInputPort amplitude;
    private static UnitInputPort cutoffRange;
    private static UnitInputPort cutoff;
    private static UnitInputPort qFactor;
}
