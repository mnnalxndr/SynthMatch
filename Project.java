import java.io.File;

/**
 * Created by alexmann on 28/07/2015.
 */
public class Project {

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
        for (int i = 0; i < targetSamples.length-1 && i < candidateSamples.length-1; i++) {

            // Normalise each float value as it is passed in the iteration
            float candidateSample = (candidateSamples[i]/candidateMaxFloat);
            float targetSample = (targetSamples[i]/targetMaxFloat);

            if (targetSamples[i] == 0.0) {
                if (targetSamples[i + 1] == 0.0) {
                    System.out.println("TargetSamples end reached early");
                    System.out.println("Index reached: " + (i));
                    break;
                }
            }

            if (candidateSamples[i] == 0.0) {
                if (candidateSamples[i + 1] == 0.0) {
                    System.out.println("CandidateSamples end reached early");
                    System.out.println("Index reached: " + (i));
                    break;
                }
            }

            // Comparison function
            runningTotal += (((candidateSample) - targetSample) * (candidateSample - targetSample));

        }
        double deviationValue = (Math.sqrt(runningTotal));
        System.out.println("Result: " + Math.sqrt(runningTotal));

        return deviationValue;
    }

    public static double comparisonFunction(float[] targetSamples, float[] candidateSamples) {


        double runningTotal = 0;
        for (int i = 0; i < targetSamples.length && i < candidateSamples.length; i++) {



            // Comparison function
            runningTotal += Math.pow((candidateSamples[i] - targetSamples[i]), 2);

        }
        double deviationValue = (Math.sqrt(runningTotal));
        System.out.println(Math.sqrt(runningTotal));

        return deviationValue;
    }

    public static void normalise(float[] targetSamples, float[] candidateSamples) {

        // Iterate through both arrays and set each MaxFloat value to the highest value in each array
        float targetMaxFloat = 0;
        float candidateMaxFloat = 0;
        for (int i = 0; i<targetSamples.length && i<candidateSamples.length; i++) {

            if (targetSamples[i] > targetMaxFloat)
                targetMaxFloat = targetSamples[i];

            if (candidateSamples[i] > candidateMaxFloat)
                candidateMaxFloat = candidateSamples[i];
        }

        for (int i = 0; i<targetSamples.length && i<candidateSamples.length; i++) {
            candidateSamples[i] /= targetMaxFloat;
            targetSamples[i] /= targetMaxFloat;
        }
    }

    public static void comparisonMethod () {

        double runningTotal = 0;
        double[] array = new double[16];


        for (int i=0; i<705601; i++) {

            if (i==44100 ||
                    i==88200 ||
                    i==132300 ||
                    i==176400 ||
                    i==220500 ||
                    i==264600 ||
                    i==308700 ||
                    i==352800 ||
                    i==396900 ||
                    i==441000 ||
                    i==485100 ||
                    i==529200 ||
                    i==573300 ||
                    i==617400 ||
                    i==661500 ||
                    i==705600) {
                System.out.print("At " + i / 44100 + " seconds: " + (Math.sqrt(runningTotal)-420));
                System.out.println("\t\t" + ((Math.log(((Math.sqrt((runningTotal)))/420)-1))/Math.log(4)));
                array[(i/44100)-1] = Math.sqrt(runningTotal);
            }
            runningTotal += 4;

        }

//        runningTotal /= 420;
        System.out.println((Math.sqrt(runningTotal)));

    }
    
    public static void main(String[] args) {

        System.out.println("Take 3!");

        File targetFile = new File("440Hz-SAW-1000LP.wav");

        lengthInS = TargetImport.getSoundLength(targetFile);
        targetSamples = TargetImport.getTargetSamples(targetFile);

        SubtractiveSynth subSynth;
        System.out.println("Subsynth make start");
        subSynth = new SubtractiveSynth(440, SubtractiveSynth.WAVETYPE.SAWTOOTH, 0.5,
                false, SubtractiveSynth.WAVETYPE.TRIANGLE, -1, 0.75,
                false, SubtractiveSynth.WAVETYPE.TRIANGLE, 2, 0.75,
                SubtractiveSynth.FILTERTYPE.LOWPASS, 1000, 0.55);
//
//        FMSynth fmSynth;
//        fmSynth = new FMSynth(440, 1, 20, 880, 0.5, 440, 0.5, 44, 0.5, 44, 0.5, 44, 0.5);

//        Candidate c = new Candidate();



//
//        if (synthesisType == SYNTHESIS_TYPE.SUBTRACTIVE) {
//            SUBSYNTH_WAVETYPE waveType = SUBSYNTH_WAVETYPE.SAWTOOTH;
//            SUBSYNTH_FILTERTYPE filterType = SUBSYNTH_FILTERTYPE.LOWPASS;
//        }

        normaliseAndCompare(targetSamples, subSynth.candidateSamples);

//
//        double fitness = c.normaliseAndCompare(targetSamples, candidateSamples);
//        System.out.println("Deviation value = " + fitness);

//        comparisonMethod();

        System.exit(0);

    }

    public static double lengthInS;
    public static float[] targetSamples = null;
    public static float[] candidateSamples = null;
}
