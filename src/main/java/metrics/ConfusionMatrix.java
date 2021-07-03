package metrics;

public class ConfusionMatrix {

    private final int truePositives;
    private final int trueNegatives;
    private final int falseNegatives;
    private final int falsePositives;

    public ConfusionMatrix(int truePositives, int trueNegatives, int falseNegatives, int falsePositives) {
        this.truePositives = truePositives;
        this.trueNegatives = trueNegatives;
        this.falseNegatives = falseNegatives;
        this.falsePositives = falsePositives;
    }

    public int getTruePositives() {
        return truePositives;
    }

    public int getTrueNegatives() {
        return trueNegatives;
    }

    public int getFalseNegatives() {
        return falseNegatives;
    }

    public int getFalsePositives() {
        return falsePositives;
    }
}
