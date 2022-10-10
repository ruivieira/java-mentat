package dev.ruivieira.mentat.kernels;

import java.util.stream.IntStream;

public interface Kernel {

    /**
     * Calculate the Kernel's value for points x1 and x2
     * @param x1 Point x1 as an array
     * @param x2 Point x2 as an array
     * @return The Kernel's value for points x1 and x2
     */
    double K(final double[] x1, final double[] x2);

    default double squaredSum(double[] x1, double[] x2) {
        return IntStream.range(0, x1.length).mapToDouble(i -> {
            double v = x1[i] - x2[i];
            return v * v;
        }).sum();
    }
}
