import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.MixerMono;
import com.jsyn.unitgen.SineOscillatorPhaseModulated;

/**
 * Created by alexmann on 14/08/2015.
 */
public class FMSynth extends Synth {

    public FMSynth (double mainFreq, double mainAmp, int preset,
                    double operator2Freq, double operator2Amp,
                    double operator3Freq, double operator3Amp,
                    double operator4Freq, double operator4Amp,
                    double operator5Freq, double operator5Amp,
                    double operator6Freq, double operator6Amp) {

        frequency = mainFreq;
        dx7Preset = preset;
        System.out.println("FM Constructor");
        makeWaveform(frequency, mainAmp, preset,
                operator2Freq, operator2Amp,
                operator3Freq, operator3Amp,
                operator4Freq, operator4Amp,
                operator5Freq, operator5Amp,
                operator6Freq, operator6Amp);





    }
    public void makeWaveform (double mainFreq, double mainAmp, int preset,
                              double operator2Freq, double operator2Amp,
                              double operator3Freq, double operator3Amp,
                              double operator4Freq, double operator4Amp,
                              double operator5Freq, double operator5Amp,
                              double operator6Freq, double operator6Amp) {

        Synthesizer synth = JSyn.createSynthesizer();
        MixerMono mixer = new MixerMono(6);
        this.add(mixer);

        boolean realtime = true;

        this.add(operator1 = new SineOscillatorPhaseModulated());
        this.add(operator2 = new SineOscillatorPhaseModulated());
        this.add(operator3 = new SineOscillatorPhaseModulated());
        this.add(operator4 = new SineOscillatorPhaseModulated());
        this.add(operator5 = new SineOscillatorPhaseModulated());
        this.add(operator6 = new SineOscillatorPhaseModulated());
        operator1.frequency.set(mainFreq);
        operator1.amplitude.set(mainAmp);
        operator2.frequency.set(operator2Freq);
        operator2.amplitude.set(operator2Amp);
        operator3.frequency.set(operator3Freq);
        operator3.amplitude.set(operator3Amp);
        operator4.frequency.set(operator4Freq);
        operator4.amplitude.set(operator4Amp);
        operator5.frequency.set(operator5Freq);
        operator5.amplitude.set(operator5Amp);
        operator6.frequency.set(operator6Freq);
        operator6.amplitude.set(operator6Amp);

        switch (preset) {
            case 1:
                // Carriers other than operator 1 assigned note frequency
                operator3.frequency.set(mainFreq);

                // Modulation connections
                operator2.output.connect(operator1.modulation);
                operator6.output.connect(operator6.modulation);
                operator6.output.connect(operator5.modulation);
                operator5.output.connect(operator4.modulation);
                operator4.output.connect(operator3.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator3.output.connect(mixer.input);
                break;

            case 2:
                // Carriers other than operator 1 assigned note frequency
                operator3.frequency.set(mainFreq);

                // Modulation connections
                operator2.output.connect(operator2.modulation);
                operator2.output.connect(operator1.modulation);
                operator6.output.connect(operator5.modulation);
                operator5.output.connect(operator4.modulation);
                operator4.output.connect(operator3.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator3.output.connect(mixer.input);
                break;

            case 3:
                // Carriers other than operator 1 assigned note frequency
                operator4.frequency.set(mainFreq);

                // Modulation connections
                operator3.output.connect(operator2.modulation);
                operator2.output.connect(operator1.modulation);
                operator6.output.connect(operator6.modulation);
                operator6.output.connect(operator5.modulation);
                operator5.output.connect(operator4.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator4.output.connect(mixer.input);
                break;

            case 4:
                // Carriers other than operator 1 assigned note frequency
                operator4.frequency.set(mainFreq);

                // Modulation connections
                operator3.output.connect(operator2.modulation);
                operator2.output.connect(operator1.modulation);
                operator6.output.connect(operator5.modulation);
                operator5.output.connect(operator4.modulation);
                operator4.output.connect(operator6.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator4.output.connect(mixer.input);
                break;

            case 5:
                // Carriers other than operator 1 assigned note frequency
                operator3.frequency.set(mainFreq);
                operator5.frequency.set(mainFreq);

                // Modulation connections
                operator2.output.connect(operator1.modulation);
                operator4.output.connect(operator3.modulation);
                operator6.output.connect(operator6.modulation);
                operator6.output.connect(operator5.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator3.output.connect(mixer.input);
                operator5.output.connect(mixer.input);
                break;

            case 6:
                // Carriers other than operator 1 assigned note frequency
                operator3.frequency.set(mainFreq);
                operator5.frequency.set(mainFreq);

                // Modulation connections
                operator2.output.connect(operator1.modulation);
                operator4.output.connect(operator3.modulation);
                operator6.output.connect(operator5.modulation);
                operator5.output.connect(operator6.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator3.output.connect(mixer.input);
                operator5.output.connect(mixer.input);
                break;

            case 7:
                // Carriers other than operator 1 assigned note frequency
                operator3.frequency.set(mainFreq);

                // Modulation connections
                operator2.output.connect(operator1.modulation);
                operator6.output.connect(operator6.modulation);
                operator6.output.connect(operator5.modulation);
                operator4.output.connect(operator3.modulation);
                operator5.output.connect(operator3.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator3.output.connect(mixer.input);
                break;

            case 8:
                // Carriers other than operator 1 assigned note frequency
                operator3.frequency.set(mainFreq);

                // Modulation connections
                operator2.output.connect(operator1.modulation);
                operator4.output.connect(operator4.modulation);
                operator4.output.connect(operator3.modulation);
                operator6.output.connect(operator5.modulation);
                operator5.output.connect(operator3.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator3.output.connect(mixer.input);
                break;

            case 9:
                // Carriers other than operator 1 assigned note frequency
                operator3.frequency.set(mainFreq);

                // Modulation connections
                operator2.output.connect(operator2.modulation);
                operator2.output.connect(operator1.modulation);
                operator4.output.connect(operator3.modulation);
                operator6.output.connect(operator5.modulation);
                operator5.output.connect(operator3.modulation);

                operator1.output.connect(mixer.input);
                operator3.output.connect(mixer.input);
                break;

            case 10:
                // Carriers other than operator 1 assigned note frequency
                operator4.frequency.set(mainFreq);

                // Modulation connections
                operator3.output.connect(operator3.modulation);
                operator3.output.connect(operator2.modulation);
                operator2.output.connect(operator1.modulation);
                operator6.output.connect(operator4.modulation);
                operator5.output.connect(operator4.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator4.output.connect(mixer.input);
                break;

            case 11:
                // Carriers other than operator 1 assigned note frequency
                operator4.frequency.set(mainFreq);

                // Modulation connections
                operator3.output.connect(operator3.modulation);
                operator3.output.connect(operator2.modulation);
                operator2.output.connect(operator1.modulation);
                operator6.output.connect(operator4.modulation);
                operator5.output.connect(operator4.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator4.output.connect(mixer.input);
                break;

            case 12:
                // Carriers other than operator 1 assigned note frequency
                operator3.frequency.set(mainFreq);

                // Modulation connections
                operator2.output.connect(operator2.modulation);
                operator2.output.connect(operator1.modulation);
                operator4.output.connect(operator3.modulation);
                operator5.output.connect(operator3.modulation);
                operator6.output.connect(operator3.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator3.output.connect(mixer.input);
                break;

            case 13:
                // Carriers other than operator 1 assigned note frequency
                operator3.frequency.set(mainFreq);

                // Modulation connections
                operator2.output.connect(operator1.modulation);
                operator4.output.connect(operator3.modulation);
                operator5.output.connect(operator3.modulation);
                operator6.output.connect(operator3.modulation);
                operator6.output.connect(operator6.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator3.output.connect(mixer.input);
            break;

            case 14:
                // Carriers other than operator 1 assigned note frequency
                operator3.frequency.set(mainFreq);

                // Modulation connections
                operator2.output.connect(operator1.modulation);
                operator6.output.connect(operator6.modulation);
                operator6.output.connect(operator4.modulation);
                operator5.output.connect(operator4.modulation);
                operator4.output.connect(operator3.modulation);


                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator3.output.connect(mixer.input);
                break;

            case 15:
                // Carriers other than operator 1 assigned note frequency
                operator3.frequency.set(mainFreq);

                // Modulation connections
                operator2.output.connect(operator2.modulation);
                operator2.output.connect(operator1.modulation);
                operator6.output.connect(operator4.modulation);
                operator5.output.connect(operator4.modulation);
                operator4.output.connect(operator3.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator3.output.connect(mixer.input);
                break;

            case 16:
                // Operator 1 only carrier, so straight to modulation connections
                operator2.output.connect(operator1.modulation);
                operator4.output.connect(operator3.modulation);
                operator3.output.connect(operator1.modulation);
                operator6.output.connect(operator6.modulation);
                operator6.output.connect(operator5.modulation);
                operator5.output.connect(operator1.modulation);

                // Connect carrier to mixer
                operator1.output.connect(mixer.input);
                break;

            case 17:
                // Operator 1 only carrier, so straight to modulation connections
                operator2.output.connect(operator2.modulation);
                operator2.output.connect(operator1.modulation);
                operator4.output.connect(operator3.modulation);
                operator3.output.connect(operator1.modulation);
                operator6.output.connect(operator5.modulation);
                operator5.output.connect(operator1.modulation);

                // Connect carrier to mixer
                operator1.output.connect(mixer.input);
                break;

            case 18:
                // Operator 1 only carrier, so straight to modulation connections
                operator2.output.connect(operator1.modulation);
                operator3.output.connect(operator3.modulation);
                operator3.output.connect(operator1.modulation);
                operator6.output.connect(operator5.modulation);
                operator5.output.connect(operator4.modulation);
                operator4.output.connect(operator1.modulation);

                // Connect carrier to mixer
                operator1.output.connect(mixer.input);
                break;

            case 19:
                // Carriers other than operator 1 assigned note frequency
                operator4.frequency.set(mainFreq);
                operator5.frequency.set(mainFreq);

                // Modulation connections
                operator3.output.connect(operator2.modulation);
                operator2.output.connect(operator1.modulation);
                operator6.output.connect(operator6.modulation);
                operator6.output.connect(operator4.modulation);
                operator6.output.connect(operator5.modulation);


                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator4.output.connect(mixer.input);
                operator5.output.connect(mixer.input);
                break;

            case 20:
                // Carriers other than operator 1 assigned note frequency
                operator2.frequency.set(mainFreq);
                operator4.frequency.set(mainFreq);

                // Modulation connections
                operator3.output.connect(operator3.modulation);
                operator3.output.connect(operator1.modulation);
                operator3.output.connect(operator2.modulation);
                operator5.output.connect(operator4.modulation);
                operator6.output.connect(operator4.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator2.output.connect(mixer.input);
                operator4.output.connect(mixer.input);
                break;

            case 21:
                // Carriers other than operator 1 assigned note frequency
                operator2.frequency.set(mainFreq);
                operator4.frequency.set(mainFreq);
                operator5.frequency.set(mainFreq);

                // Modulation connections
                operator3.output.connect(operator3.modulation);
                operator3.output.connect(operator1.modulation);
                operator3.output.connect(operator2.modulation);
                operator6.output.connect(operator4.modulation);
                operator6.output.connect(operator5.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator2.output.connect(mixer.input);
                operator4.output.connect(mixer.input);
                operator5.output.connect(mixer.input);
                break;

            case 22:
                // Carriers other than operator 1 assigned note frequency
                operator3.frequency.set(mainFreq);
                operator4.frequency.set(mainFreq);
                operator5.frequency.set(mainFreq);

                // Modulation connections
                operator2.output.connect(operator1.modulation);
                operator6.output.connect(operator6.modulation);
                operator6.output.connect(operator3.modulation);
                operator6.output.connect(operator4.modulation);
                operator6.output.connect(operator5.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator3.output.connect(mixer.input);
                operator4.output.connect(mixer.input);
                operator5.output.connect(mixer.input);
                break;

            case 23:
                // Carriers other than operator 1 assigned note frequency
                operator2.frequency.set(mainFreq);
                operator4.frequency.set(mainFreq);
                operator5.frequency.set(mainFreq);

                // Modulation connections
                operator3.output.connect(operator2.modulation);
                operator6.output.connect(operator6.modulation);
                operator6.output.connect(operator4.modulation);
                operator6.output.connect(operator5.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator2.output.connect(mixer.input);
                operator4.output.connect(mixer.input);
                operator5.output.connect(mixer.input);
                break;

            case 24:
                // Carriers other than operator 1 assigned note frequency
                operator2.frequency.set(mainFreq);
                operator3.frequency.set(mainFreq);
                operator4.frequency.set(mainFreq);
                operator5.frequency.set(mainFreq);

                // Modulation connections
                operator6.output.connect(operator6.modulation);
                operator6.output.connect(operator3.modulation);
                operator6.output.connect(operator4.modulation);
                operator6.output.connect(operator5.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator2.output.connect(mixer.input);
                operator3.output.connect(mixer.input);
                operator4.output.connect(mixer.input);
                operator5.output.connect(mixer.input);
                break;

            case 25:
                // Carriers other than operator 1 assigned note frequency
                operator2.frequency.set(mainFreq);
                operator3.frequency.set(mainFreq);
                operator4.frequency.set(mainFreq);
                operator5.frequency.set(mainFreq);

                // Modulation connections
                operator6.output.connect(operator6.modulation);
                operator6.output.connect(operator4.modulation);
                operator6.output.connect(operator5.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator2.output.connect(mixer.input);
                operator3.output.connect(mixer.input);
                operator4.output.connect(mixer.input);
                operator5.output.connect(mixer.input);
                break;

            case 26:
                // Carriers other than operator 1 assigned note frequency
                operator2.frequency.set(mainFreq);
                operator4.frequency.set(mainFreq);

                // Modulation connections
                operator3.output.connect(operator2.modulation);
                operator5.output.connect(operator4.modulation);
                operator6.output.connect(operator6.modulation);
                operator6.output.connect(operator4.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator2.output.connect(mixer.input);
                operator4.output.connect(mixer.input);
                break;

            case 27:
                // Carriers other than operator 1 assigned note frequency
                operator2.frequency.set(mainFreq);
                operator4.frequency.set(mainFreq);

                // Modulation connections
                operator3.output.connect(operator3.modulation);
                operator3.output.connect(operator2.modulation);
                operator5.output.connect(operator4.modulation);
                operator6.output.connect(operator4.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator2.output.connect(mixer.input);
                operator4.output.connect(mixer.input);
                break;

            case 28:
                // Carriers other than operator 1 assigned note frequency
                operator3.frequency.set(mainFreq);
                operator6.frequency.set(mainFreq);

                // Modulation connections
                operator2.output.connect(operator1.modulation);
                operator5.output.connect(operator5.modulation);
                operator5.output.connect(operator4.modulation);
                operator4.output.connect(operator3.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator3.output.connect(mixer.input);
                operator6.output.connect(mixer.input);
                break;

            case 29:
                // Carriers other than operator 1 assigned note frequency
                operator2.frequency.set(mainFreq);
                operator3.frequency.set(mainFreq);
                operator5.frequency.set(mainFreq);

                // Modulation connections
                operator4.output.connect(operator3.modulation);
                operator6.output.connect(operator6.modulation);
                operator6.output.connect(operator5.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator2.output.connect(mixer.input);
                operator3.output.connect(mixer.input);
                operator5.output.connect(mixer.input);
                break;

            case 30:
                // Carriers other than operator 1 assigned note frequency
                operator2.frequency.set(mainFreq);
                operator3.frequency.set(mainFreq);
                operator6.frequency.set(mainFreq);

                // Modulation connections
                operator5.output.connect(operator5.modulation);
                operator5.output.connect(operator4.modulation);
                operator4.output.connect(operator3.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator2.output.connect(mixer.input);
                operator3.output.connect(mixer.input);
                operator6.output.connect(mixer.input);
                break;

            case 31:
                // Carriers other than operator 1 assigned note frequency
                operator2.frequency.set(mainFreq);
                operator3.frequency.set(mainFreq);
                operator4.frequency.set(mainFreq);
                operator5.frequency.set(mainFreq);

                // Modulation
                operator6.output.connect(operator6.modulation);
                operator6.output.connect(operator5.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator2.output.connect(mixer.input);
                operator3.output.connect(mixer.input);
                operator4.output.connect(mixer.input);
                operator5.output.connect(mixer.input);
                break;

            case 32:
                // Carriers other than operator 1 assigned note frequency
                operator2.frequency.set(mainFreq);
                operator3.frequency.set(mainFreq);
                operator4.frequency.set(mainFreq);
                operator5.frequency.set(mainFreq);
                operator6.frequency.set(mainFreq);

                // Modulation connections
                operator6.output.connect(operator6.modulation);

                // Connect carriers to mixer
                operator1.output.connect(mixer.input);
                operator2.output.connect(mixer.input);
                operator3.output.connect(mixer.input);
                operator4.output.connect(mixer.input);
                operator5.output.connect(mixer.input);
                operator6.output.connect(mixer.input);
                break;

            default:
                System.err.println("Incorrect DX7 preset inputted");
                System.exit(1);

        }

        getWaveform(realtime, synth, mixer);

    }
    int dx7Preset;
    SineOscillatorPhaseModulated operator1, operator2, operator3, operator4, operator5, operator6;
}
