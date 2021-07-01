package metrics;

public class Confusion {

    public static double truePositiveRate(double truePositives, double falseNegatives) {
        return truePositives / (truePositives + falseNegatives);
    }

    public static double falsePositiveRate(double trueNegatives, double falsePositives) {
        return 1.0 - (trueNegatives / (falsePositives + trueNegatives));
    }

}
