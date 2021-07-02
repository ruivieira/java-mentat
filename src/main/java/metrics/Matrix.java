package metrics;

public class Matrix {

    private final int truePositives;
    private final int trueNegatives;
    private final int falseNegatives;
    private final int falsePositives;

    public Matrix(int truePositives, int trueNegatives, int falseNegatives, int falsePositives) {
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
