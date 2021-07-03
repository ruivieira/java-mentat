package metrics;

import java.util.HashMap;
import java.util.Map;

public class Fairness {

    private final Map<String, ConfusionMatrix> subgroups = new HashMap<>();
    private final String priviledgedName;
    private final ConfusionMatrix priviledgedMatrix;
    private final Map<String, Double> TPR = new HashMap<>();
    private final Map<String, Double> accuracy = new HashMap<>();
    private double epsilon = 0.8;

    private Fairness(String priviledgedName, int[] priviledgedLabels, double[] priviledgedProbabilities) {
        this.priviledgedName = priviledgedName;
        this.priviledgedMatrix = Confusion.matrix(priviledgedLabels, priviledgedProbabilities);
    }

    public static Fairness create(String priviledgedName, int[] priviledgedLabels, double[] priviledgedProbabilities) {
        return new Fairness(priviledgedName, priviledgedLabels, priviledgedProbabilities);
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
    }

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    public void check() {
        final double priviledgeTPR =
                Confusion.truePositiveRate(priviledgedMatrix.getTruePositives(), priviledgedMatrix.getFalseNegatives());

        for (Map.Entry<String, ConfusionMatrix> protectedGroup : this.subgroups.entrySet()) {
            final ConfusionMatrix matrix = protectedGroup.getValue();
            final String name = protectedGroup.getKey();
            final double protectedTPR = Confusion.truePositiveRate(matrix);
            this.TPR.put(name, protectedTPR);
            final double protectedAccuracy = Confusion.accuracy(matrix);
            this.accuracy.put(name, protectedAccuracy);
        }
    }
}
