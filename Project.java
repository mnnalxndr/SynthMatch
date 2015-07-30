

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by alexmann on 28/07/2015.
 */
public class Project {

    public void comparisonMethod () {

        double runningTotal = 0;

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
                System.out.print("At " + i / 44100 + " seconds: " + Math.sqrt(runningTotal));
                System.out.println("\t\t" + ((Math.log(((Math.sqrt((runningTotal)))/420)-1))/Math.log(4)));
            }
            runningTotal += 4;

        }

//        runningTotal /= 420;
        System.out.println((Math.sqrt(runningTotal)));

    }
    
    public static void main(String[] args) {

        System.out.println("Take 3!");

        File targetFile = new File("440Hz-SAW.wav");

        lengthInMS = TargetImport.getSoundLength(targetFile);
        targetSamples = TargetImport.getTargetSamples(targetFile);

        Candidate c = new Candidate();

        candidateSamples = c.generateCandidate(500, 1.0);

        double fitness = c.normaliseAndCompare(targetSamples, candidateSamples);

        System.out.println(fitness);

    }

    public static long lengthInMS;
    public static float[] targetSamples = null;
    public static float[] candidateSamples = null;
}
