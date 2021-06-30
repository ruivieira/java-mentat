package network.models;

import org.apache.commons.math3.distribution.BinomialDistribution;
import org.apache.commons.math3.linear.RealMatrix;

import static org.apache.commons.math3.linear.MatrixUtils.createRealIdentityMatrix;

public class ErdosRenyi {

    private final RealMatrix map;

    public ErdosRenyi(int nodes, double probability) {
        map = createRealIdentityMatrix(nodes);
        final BinomialDistribution binomial = new BinomialDistribution(1, probability);
        final int[] link = binomial.sample(nodes*(nodes-1)/2);
        int t = 0;
        for (int j = 1 ; j < nodes ; j++) {
            for (int i = 0 ; i < j - 1 ; i++) {
                map.setEntry(i, j, link[t]);
                t += 1;
            }
        }
    }

    public RealMatrix getMap() {
        return map;
    }

}
