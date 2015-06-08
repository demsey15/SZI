package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.neuralNetwork;

/**
 * Created by Delirus on 2015-06-09.
 */
public class TrainingData {

    private double[][] inputs;
    private double[][] outputs;

    public TrainingData(double[][] inputs, double[][] outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public double[][] getInputs() {
        return inputs;
    }

    public double[][] getOutputs() {
        return outputs;
    }
}