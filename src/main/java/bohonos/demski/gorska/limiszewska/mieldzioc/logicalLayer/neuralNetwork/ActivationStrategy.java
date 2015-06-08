package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.neuralNetwork;

/**
 * Created by Delirus on 2015-06-09.
 */
public interface ActivationStrategy {
    double activate(double weightedSum);
    double derivative(double weightedSum);
    ActivationStrategy copy();
}
