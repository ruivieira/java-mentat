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

    @Override
    public double calculateHyperparamDerivative(double[] x1, double[] x2,
            int hyperparamIndex) {
        // final double sum = pointSquaredSum(x1, x2);

        // // df(a)/da = 2 * a * exp(-0.5 * x^2 / l^2);
        // if (hyperparamIndex == 0)
        // return 2 * hyperparameters[0] * Math.exp(-0.5 * sum *
        // inverseSquaredLengthscale);

        // // df(l)/dl = (x^2 / l^3) * f(l);
        // if (hyperparamIndex == 1)
        // return K(x1, x2) * sum * inverseSquaredLengthscale / hyperparameters[1];

        // throw new IllegalStateException();
        return 0;
    }
}
