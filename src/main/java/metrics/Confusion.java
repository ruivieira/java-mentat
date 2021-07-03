package metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Confusion {

    public static final int POSITIVE_LABEL = 1;
    public static final int NEGATIVE_LABEL = 0;
    private static final Logger logger = LoggerFactory.getLogger(Confusion.class);

    public static double truePositiveRate(double truePositives, double falseNegatives) {
        return truePositives / (truePositives + falseNegatives);
    }

    public static double truePositiveRate(ConfusionMatrix matrix) {
        return truePositiveRate(matrix.getTruePositives(), matrix.getFalseNegatives());
    }

    public static double falsePositiveRate(double trueNegatives, double falsePositives) {
        return 1.0 - (trueNegatives / (falsePositives + trueNegatives));
    }

    public static double falsePositiveRate(ConfusionMatrix matrix) {
        return falsePositiveRate(matrix.getTrueNegatives(), matrix.getFalsePositives());
    }

    public static double accuracy(ConfusionMatrix matrix) {
        final double trueSum = matrix.getTruePositives() + matrix.getTrueNegatives();
        final double falseSum = matrix.getFalsePositives() + matrix.getFalseNegatives();
        return (trueSum) / (trueSum + falseSum);
    }

    public static double positivePredictiveValue(ConfusionMatrix matrix) {
        return (double) matrix.getTruePositives() / (double) (matrix.getTruePositives() + matrix.getFalsePositives());
    }

    public static ConfusionMatrix matrix(int[] labels, double[] probabilities) {

        if (labels.length != probabilities.length) {
            final String message = "Labels and probabilities must have the same size";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }

        final int N = labels.length;

        final double threshold = 0.5;

        int _truePositives = 0;
        int _trueNegatives = 0;
        int _falsePositives = 0;
        int _falseNegatives = 0;

        for (int i = 0; i < N; i++) {

            final double probability = probabilities[i];
            final double label = labels[i];

            if (probability >= threshold) {
                if (label == POSITIVE_LABEL) {
                    _truePositives++;
                } else if (label == NEGATIVE_LABEL) {
                    _falsePositives++;
                }
            } else {
                if (label == POSITIVE_LABEL) {
                    _falseNegatives++;
                } else if (label == NEGATIVE_LABEL) {
                    _trueNegatives++;
                }
            }
        }
        return new ConfusionMatrix(_truePositives, _trueNegatives, _falseNegatives, _falsePositives);
    }

}
