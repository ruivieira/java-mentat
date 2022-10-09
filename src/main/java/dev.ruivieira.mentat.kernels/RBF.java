package dev.ruivieira.mentat.kernels;

import java.util.stream.IntStream;

public class RBF implements Kernel {

    private final double variance;
    private final double squaredLengthscale;

    public RBF(double amplitude, double lengthscale) {
        this.variance = amplitude * amplitude;
        this.squaredLengthscale = lengthscale * lengthscale;
    }

    @Override
    public double K(double[] x1, double[] x2) {
        final double sum = IntStream.range(0, x1.length).mapToDouble(i -> {
            double v = x1[i] - x2[i];
            return v * v;
        }).sum();
        return variance * Math.exp(-0.5 * sum / squaredLengthscale);
    }
}
