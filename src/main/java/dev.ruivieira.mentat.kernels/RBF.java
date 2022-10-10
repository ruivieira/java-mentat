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
        final double sum = squaredSum(x1, x2);
        return variance * Math.exp(-0.5 * sum / squaredLengthscale);
    }
}
