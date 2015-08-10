import java.io.File;

/**
 * Created by alexmann on 28/07/2015.
 */
public class Project {

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

        Candidate c = new Candidate();

        candidateSamples = c.generateCandidate(550, 6);
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
