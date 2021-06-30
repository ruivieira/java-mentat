package explainability.fairness;

import java.util.Arrays;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

/**
 * Generates a counterfactually fair model.
 */
public class CounterfactualFairness {

    private final RealMatrix data;

    /**
     * Initialise a counterfactually fair model with the specified dataset.
     * The dataset is a {@link RealMatrix} and each column represents a variable and each row
     * represents an observation.
     * @param data The initial dataset as a {@link RealMatrix}
     */
    public CounterfactualFairness(RealMatrix data) {
        this.data = data;
    }

    /**
     * Calculate a model by giving the protected and variable indices.
     * @param protectedIndices An array of indices for the protected variables
     * @param variableIndices An array of indices for the non-protected variables
     * @param targetIndex The index of the target
     */
    public OLSMultipleLinearRegression calculate(int[] protectedIndices, int[] variableIndices, int targetIndex) {
        final RealMatrix residuals = new Array2DRowRealMatrix(this.data.getRowDimension(), variableIndices.length);

        for (int i = 0; i < variableIndices.length; i++) {
            final int index = variableIndices[i];
            final RealVector varResidual = this.calculateEpsilon(protectedIndices, index);
            residuals.setColumn(i, varResidual.toArray());
        }
        // predict target from residuals
        final OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        regression.newSampleData(this.data.getColumn(targetIndex), residuals.getData());
        final RealVector diff = this.predict(residuals, regression.estimateRegressionParameters()).subtract(this.data.getColumnVector(targetIndex));
        return regression;
    }

    /**
     * Calculate epsilon for the protected indices
     * @param protectedIndices An array of protected indices
     * @param targetIndex The target index
     * @return
     */
    public RealVector calculateEpsilon(int[] protectedIndices, int targetIndex) {
        int[] protectedRows = new int[this.data.getRowDimension()];
        Arrays.setAll(protectedRows, i -> i);
        final RealMatrix _x = this.data.getSubMatrix(protectedRows, protectedIndices);
        final RealVector _y = this.data.getSubMatrix(protectedRows, new int[]{targetIndex}).getColumnVector(0);

        final OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        regression.newSampleData(_y.toArray(), _x.getData());
        return new ArrayRealVector(regression.estimateResiduals());
    }

    /**
     *
     * @param data
     * @param coefficients
     * @return
     */
    private RealVector predict(RealMatrix data, double[] coefficients) {
        final int rows = data.getRowDimension();
        final int cols = data.getColumnDimension();
        final double[][] values = data.getData();
        final RealVector predictions = new ArrayRealVector(rows);
        for (int i = 0; i < rows; i++) {
            double prediction = coefficients[0];
            for (int j = 0; j < cols; j++) {
                prediction += coefficients[j + 1] * values[i][j];
            }
            predictions.setEntry(i, prediction);
        }
        return predictions;
    }
}
