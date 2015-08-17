import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.*;

/**
 * Created by alexmann on 14/08/2015.
 */
public class SubtractiveSynth extends Synth {



    enum WAVETYPE {
        SAWTOOTH, SQUARE, TRIANGLE
    }

    enum FILTERTYPE {
        LOWPASS, FOURPOLES
    }

    public SubtractiveSynth (double freq, WAVETYPE osc1Shape, double osc1Amp,
                             boolean osc2On, WAVETYPE osc2Shape, int osc2RelativeOctave, double osc2Amp,
                             boolean osc3On, WAVETYPE osc3Shape, int osc3RelativeOctave, double osc3Amp,
                             FILTERTYPE filterType, double filterCutoff, double filterQ) {

        this.frequency = freq;
        this.osc1Shape = osc1Shape;
        this.osc1Amp = osc1Amp;
        this.osc2On = osc2On;
        this.osc2Shape = osc2Shape;
        this.osc2RelativeOctave = osc2RelativeOctave;
        this.osc2Amp = osc2Amp;
        this.osc3On = osc3On;
        this.osc3Shape = osc3Shape;
        this.osc3RelativeOctave = osc3RelativeOctave;
        this.osc3Amp = osc3Amp;
        this.filterType = filterType;
        this.filterCutoff = filterCutoff;
        this.filterQ = filterQ;
        makeWaveform();


    }

    private void makeWaveform() {

        Synthesizer synth = JSyn.createSynthesizer();
        MixerMono mixer, preMixer;

        UnitInputPort cutoff, qValue;

        boolean realtime = false;


        this.add(mixer = new MixerMono(1));
        this.add(preMixer = new MixerMono(1 + (osc2On ? 1 : 0) + (osc3On ? 1 : 0)));


        oscillatorSetup(preMixer, frequency, osc1Shape, 0, osc1Amp);
        if (osc2On)
            oscillatorSetup(preMixer, frequency, osc2Shape, osc2RelativeOctave, osc2Amp);
        if (osc3On)
            oscillatorSetup(preMixer, frequency, osc3Shape, osc3RelativeOctave, osc3Amp);

        switch (filterType) {
            case FOURPOLES:
                FilterFourPoles filter4P;
                this.add(filter4P = new FilterFourPoles());
                this.addPort(cutoff = filter4P.frequency);
                this.addPort(qValue = filter4P.Q);
                cutoff.set(filterCutoff);
                qValue.set(filterQ);
                preMixer.output.connect(filter4P.input);
                filter4P.output.connect(mixer.input);
                break;

            case LOWPASS:
                FilterLowPass filter;
                this.add(filter = new FilterLowPass());
                this.addPort(cutoff = filter.frequency);
                this.addPort(qValue = filter.Q);
                cutoff.set(filterCutoff);
                qValue.set(filterQ);
                preMixer.output.connect(filter.input);
                filter.output.connect(mixer.input);
                break;
        }

        synth.add(this);

        synth.start();

        System.out.println("Before: " + candidateSamples);
        getWaveform(realtime, synth, mixer);
        System.out.println("After: " + candidateSamples);


    }

    private void oscillatorSetup (MixerMono preMixer, double freq, WAVETYPE shape, int octave, double amp) {

        UnitOscillator osc = null;
        UnitInputPort oscFreq, oscAmp;
        switch (shape) {
            case SAWTOOTH:
                this.add(osc = new SawtoothOscillator());
                break;

            case SQUARE:
                this.add(osc = new SquareOscillator());
                break;

            case TRIANGLE:
                this.add(osc = new TriangleOscillator());
                break;


        }
        this.addPort(oscFreq = osc.frequency);
        this.addPort(oscAmp = osc.amplitude);
        oscFreq.set(freq * Math.pow(2, octave));
//        oscAmp.set(amp);
        osc.output.connect(preMixer.input);
        System.out.println("Osc connected");

    }


    WAVETYPE osc1Shape;
    double osc1Amp;
    boolean osc2On;
    WAVETYPE osc2Shape;
    int osc2RelativeOctave;
    double osc2Amp;
    boolean osc3On;
    WAVETYPE osc3Shape;
    int osc3RelativeOctave;
    double osc3Amp;
    FILTERTYPE filterType;
    double filterCutoff;
    double filterQ;
}
