package metrics;

import java.util.HashMap;
import java.util.Map;

public class Fairness {

    private final Map<String, ConfusionMatrix> subgroups = new HashMap<>();
    private final Map<String, double[]> subgroupProbabilities = new HashMap<>();
    private final String priviledgedName;
    private final double[] priviledgedProbabilities;
    private final ConfusionMatrix priviledgedMatrix;
    private final Map<String, Double> TPR = new HashMap<>();
    private final Map<String, Double> FPR = new HashMap<>();
    private final Map<String, Double> PPV = new HashMap<>();
    private final Map<String, Double> accuracy = new HashMap<>();
    // Independence or statistical parity difference
    private final Map<String, Double> independence = new HashMap<>();
    // Parity loss
    private double parityLossTPR = 0.0;
    private double parityAccuracy = 0.0;
    private double parityLossPPV = 0.0;
    private double parityLossFPR = 0.0;
    private double parityIndependence = 0.0;
    private double epsilon = 0.8;

    private Fairness(String priviledgedName, int[] priviledgedLabels, double[] priviledgedProbabilities) {
        this.priviledgedName = priviledgedName;
        this.priviledgedProbabilities = priviledgedProbabilities;
        this.priviledgedMatrix = Confusion.matrix(priviledgedLabels, priviledgedProbabilities);
    }

    public static Fairness create(String priviledgedName, int[] priviledgedLabels, double[] priviledgedProbabilities) {
        return new Fairness(priviledgedName, priviledgedLabels, priviledgedProbabilities);
    }

    public Map<String, Double> getIndependence() {
        return independence;
    }

    public Map<String, Double> getAccuracy() {
        return accuracy;
    }

    public Map<String, Double> getTPR() {
        return TPR;
    }

    public void addProtectedGroup(String name, int[] labels, double[] probabilities) {
        final ConfusionMatrix confusion = Confusion.matrix(labels, probabilities);
        subgroups.put(name, confusion);
        subgroupProbabilities.put(name, probabilities);
    }

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    public void check() {
        final double priviledgeTPR = Confusion.truePositiveRate(priviledgedMatrix);
        final double priviledgeAccuracy = Confusion.accuracy(priviledgedMatrix);
        final double priviledgePPV = Confusion.positivePredictiveValue(priviledgedMatrix);
        final double priviledgeFPR = Confusion.falsePositiveRate(priviledgedMatrix);

        for (Map.Entry<String, ConfusionMatrix> protectedGroup : this.subgroups.entrySet()) {
            final ConfusionMatrix matrix = protectedGroup.getValue();
            final String name = protectedGroup.getKey();

            // True positive rate ratios
            final double protectedTPR = Confusion.truePositiveRate(matrix);
            final double ratioTPR = protectedTPR / priviledgeTPR;
            this.TPR.put(name, ratioTPR);
            parityLossTPR += Math.abs(Math.log(ratioTPR));

            // Accuracy ratios
            final double protectedAccuracy = Confusion.accuracy(matrix);
            final double ratioAccuracy = protectedAccuracy / priviledgeAccuracy;
            this.accuracy.put(name, ratioAccuracy);
            parityAccuracy += Math.abs(Math.log(ratioAccuracy));

            // Positive predictive value ratios
            final double protectedPPV = Confusion.positivePredictiveValue(matrix);
            final double ratioPPV = protectedPPV / priviledgePPV;
            this.PPV.put(name, ratioPPV);
            parityLossPPV += Math.abs(Math.log(ratioPPV));

            // False positive rate ratios
            final double protectedFPR = Confusion.falsePositiveRate(matrix);
            final double ratioFPR = protectedFPR / priviledgeFPR;
            this.FPR.put(name, ratioFPR);
            parityLossFPR += Math.abs(Math.log(ratioFPR));

            // Statistical parity difference (or independence)
            final double protectedIndependence =
                    Confusion.statisticalParityDifference(this.priviledgedProbabilities, subgroupProbabilities.get(name));
            independence.put(name, protectedIndependence);
            parityIndependence += Math.abs(Math.log(protectedIndependence));
        }
    }

    public Map<String, Double> getPPV() {
        return PPV;
    }

    public Map<String, Double> getFPR() {
        return FPR;
    }

    public double getParityLossTPR() {
        return parityLossTPR;
    }

    public double getParityAccuracy() {
        return parityAccuracy;
    }

    public double getParityLossPPV() {
        return parityLossPPV;
    }

    public double getParityLossFPR() {
        return parityLossFPR;
    }

    public double getParityIndependence() {
        return parityIndependence;
    }
}
