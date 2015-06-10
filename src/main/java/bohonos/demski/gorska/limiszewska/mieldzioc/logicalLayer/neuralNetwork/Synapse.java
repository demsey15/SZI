package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.neuralNetwork;

/**
 * Created by Delirus on 2015-06-09.
 */
import java.io.Serializable;

public class Synapse implements Serializable {

    private Neuron sourceNeuron;
    private double weight;

    public Synapse(Neuron sourceNeuron, double weight) {
        this.sourceNeuron = sourceNeuron;
        this.weight = weight;
    }

    public Neuron getSourceNeuron() {
        return sourceNeuron;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}