package network.models;

import org.apache.commons.math3.linear.RealMatrix;

import static org.apache.commons.math3.linear.MatrixUtils.createRealIdentityMatrix;

public class WattsStrotgatz {

    private final RealMatrix map;

    public WattsStrotgatz() {
        map = createRealIdentityMatrix(10);

    }

//    private double[] extract(int j) {
//
//
//    }

}
