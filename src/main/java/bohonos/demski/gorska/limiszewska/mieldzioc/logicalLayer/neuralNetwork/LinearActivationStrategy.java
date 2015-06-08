package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.neuralNetwork;

/**
 * Created by Delirus on 2015-06-09.
 */
import java.io.Serializable;

public class LinearActivationStrategy implements ActivationStrategy, Serializable {

    public double activate(double weightedSum) {
        return weightedSum;
    }


    public double derivative(double weightedSum) {
        return 1;
    }


    public ActivationStrategy copy() {
        return new LinearActivationStrategy();
    }
}
