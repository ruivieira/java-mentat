package metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ROC {

    private static final Logger logger = LoggerFactory.getLogger(ROC.class);
    private static final int POSITIVE_LABEL = 1;
    private static final int NEGATIVE_LABEL = 0;
    protected int numberOfPoints;
    private final double[] falsePositiveRates;
    private final double[] truePositiveRates;

    public ROC(int[] labels, double[] probabilities, int numberOfPoints) {

        this.numberOfPoints = numberOfPoints;

        final int SIZE = labels.length;

        if (probabilities.length != SIZE) {
            final String message = "Labels and probabilities must have the same size";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }

        falsePositiveRates = new double[numberOfPoints];
        truePositiveRates = new double[numberOfPoints];

        for (int i = 0; i < numberOfPoints; i++) {

            double threshold = 1.0 - (double) (i) / (double) (numberOfPoints - 1);

            int _truePositives = 0;
            int _trueNegatives = 0;
            int _falsePositives = 0;
            int _falseNegatives = 0;

            for (int j = 0; j < SIZE; j++) {

                final double probability = probabilities[j];
                final double label = labels[j];

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

            falsePositiveRates[i] = Confusion.falsePositiveRate(_trueNegatives, _falsePositives);
            truePositiveRates[i] = Confusion.truePositiveRate(_truePositives, _falseNegatives);
        }
    }

    public double[] getFalsePositiveRates() {
        return falsePositiveRates;
    }

    public double[] getTruePositiveRates() {
        return truePositiveRates;
    }

}

