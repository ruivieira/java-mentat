package metrics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfusionTest {

    @Test
    void testMatrixCorrectSize() {
        final int[] labels = {0, 1, 0};
        final double[] probs = {0.7, 0.6, 0.1};
        final ConfusionMatrix matrix = Confusion.matrix(labels, probs);
        System.out.println(matrix.getFalseNegatives());
        assertEquals(0, matrix.getFalseNegatives());
        assertEquals(1, matrix.getFalsePositives());
        assertEquals(1, matrix.getTruePositives());
        assertEquals(1, matrix.getTrueNegatives());
    }
}