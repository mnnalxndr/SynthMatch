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

    enum SYNTHESIS_TYPE {
        SubtractSaw, SubtractSquare, FM, Additive, SUBVOICE
    }

    public static float[] generateCandidate(double cutoffFreq, double qValue) {

        SYNTHESIS_TYPE synthesisType = SYNTHESIS_TYPE.SubtractSaw;



        Synthesizer synth = JSyn.createSynthesizer();
        FloatSample samples = new FloatSample((int)Math.floor(Project.lengthInS * 44100), 1);

        mixer = new MixerMono(1);
        boolean realtime = true;

        switch (synthesisType) {

            case SUBVOICE:
                SubtractiveSynthVoice subVoice = new SubtractiveSynthVoice();
                subVoice.addPort(frequency = subVoice.frequency);
                subVoice.addPort(cutoff = subVoice.cutoff);
                subVoice.addPort(cutoffRange = subVoice.cutoffRange);
                subVoice.addPort(amplitude = subVoice.amplitude);
                subVoice.addPort(qFactor = subVoice.Q);

                synth.add(subVoice);


                synth.add(mixer);

                subVoice.getOutput().connect(mixer.input);

                frequency.set(440);
                amplitude.set(1.0);
                cutoff.set(cutoffFreq);
                cutoffRange.set(50);
                qFactor.set(qValue);

            case SubtractSaw:
                c = new Circuit();
                c.add(sawOsc = new SawtoothOscillator() );
                c.add(myFilter = new FilterLowPass() );
                c.add(mixer);

                c.addPort(frequency = sawOsc.frequency);
                c.addPort(amplitude = sawOsc.amplitude);
                c.addPort(cutoff = myFilter.frequency);
                c.addPort(qFactor = myFilter.Q);

                synth.add(c);

                sawOsc.output.connect(myFilter.input);
                myFilter.output.connect(mixer.input);


                frequency.set(440);
                amplitude.set(1.0);
                cutoff.set(cutoffFreq);
                qFactor.set(qValue);
                break;


            case Additive:
                c = new Circuit();
                c.add(mixer);
                int count = 0;
                for (int i = 440; i <= 20000; i+=(440*44)) {
                    SineOscillator sineOsc;
                    c.add(sineOsc = new SineOscillator());
                    c.addPort(frequency = sineOsc.frequency);
                    c.addPort(amplitude = sineOsc.amplitude);
                    frequency.set(i);
                    amplitude.set(1.0);
                    sineOsc.output.connect(mixer.input);
                    count ++;
                }
                synth.add(c);
                System.out.println("Number of oscillators = " + count);
                break;


            case FM:
                UnitInputPort modulatorFreq, modulatorAmp, carrierFreq, carrierAmp;
                c = new Circuit();
                SineOscillatorPhaseModulated modulatorOsc, carrierOsc;
                c.add(modulatorOsc = new SineOscillatorPhaseModulated());
                c.add(carrierOsc = new SineOscillatorPhaseModulated());
                c.add(mixer);

                c.addPort(modulatorFreq = modulatorOsc.frequency);
                c.addPort(modulatorAmp = modulatorOsc.amplitude);
                c.addPort(carrierFreq = carrierOsc.frequency);
                c.addPort(carrierAmp = carrierOsc.amplitude);

                modulatorFreq.set(220);
                modulatorAmp.set(1.0);
                carrierFreq.set(440);
                carrierAmp.set(1.0);

                modulatorOsc.output.connect(carrierOsc.modulation);
                carrierOsc.output.connect(mixer.input);

                synth.add(c);
                break;

            case SubtractSquare:
                c = new Circuit();
                SquareOscillator squOsc;
                c.add(squOsc = new SquareOscillator());
                c.add(myFilter = new FilterLowPass() );
                c.add(mixer);

                c.addPort(frequency = squOsc.frequency);
                c.addPort(amplitude = squOsc.amplitude);
                c.addPort(cutoff = myFilter.frequency);
                c.addPort(qFactor = myFilter.Q);

                synth.add(c);

                squOsc.output.connect(myFilter.input);
                myFilter.output.connect(mixer.input);


                frequency.set(440);
                amplitude.set(1.0);
                cutoff.set(cutoffFreq);
                qFactor.set(qValue);
                break;

        }




        synth.start();




        if (realtime) {
            try {
                LineOut lineOut = new LineOut();
                synth.add(lineOut);
                mixer.output.connect(0, lineOut.input, 0);
                mixer.output.connect(0, lineOut.input, 1);
                lineOut.start();
                Thread.sleep((long) Project.lengthInS * 1000);
                lineOut.stop();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        else {
            try {

                FixedRateMonoWriter writer = new FixedRateMonoWriter();

                synth.add(writer);
                mixer.output.connect(writer.input);
                writer.dataQueue.queue(samples);
                synth.setRealTime(false);
                System.out.println("Start");
                writer.start();
                synth.sleepFor(1);
                writer.stop();


                System.out.println("Stop");


            }  catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        try {
            System.out.println("NumFrames = " + samples.getNumFrames());
            candidateSamples = new float[samples.getNumFrames()];
            samples.read(candidateSamples);

            FileWriter write = new FileWriter("candidateOutput.txt", false);
            PrintWriter printLine = new PrintWriter(write);
            for (int i = 0; i < candidateSamples.length; i++) {
                printLine.println(candidateSamples[i]);
            }
            printLine.close();
        } catch (IOException ioe) {
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
    private static Circuit c;
    private static SawtoothOscillator sawOsc;
    private static FilterLowPass myFilter;
    private static MixerMono mixer;

    //Declare ports.
    private static UnitInputPort frequency;
    private static UnitInputPort amplitude;
    private static UnitInputPort cutoffRange;
    private static UnitInputPort cutoff;
    private static UnitInputPort qFactor;
}
