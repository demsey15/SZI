package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.geneticAlgorithm;

import java.util.ArrayList;
import java.util.List;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

/**
 * Created by Dominik on 2015-06-05.
 */
public class MyFitnessFunction extends FitnessFunction{

    private int amountOfTables; //liczba sto³ów do przejœcia
    private List<Integer> listOfTables; //lista sto³ów do przejœcia
    private double[][] distanceMatrix; //macierz odleg³oœci pomiêdzy wszystkimi sto³ami



    public MyFitnessFunction(List<Integer> listOfTables,
                             double[][] distanceMatrix) {
        this.listOfTables = listOfTables;
        this.distanceMatrix = distanceMatrix;
        amountOfTables = listOfTables.size();
    }

    @Override
    protected double evaluate(IChromosome path) {
        double length = getPathLength(path);
        double fitness = Double.MAX_VALUE;

        return fitness - length;
    }

    public double getPathLength(IChromosome path){
        double length = 0;
        List<Integer> pathCandidate = convertChromosomeToTableList(path);
        for(int i = 0; i < amountOfTables - 1; i++){
            double distance = distanceMatrix[pathCandidate.get(i)][pathCandidate.get(i + 1)];
            length += distance;
        }
        length += distanceMatrix[pathCandidate.get(0)][0];
        length += distanceMatrix[pathCandidate.get(amountOfTables - 1)][0];
        return length;
    }

    public List<Integer> convertChromosomeToTableList(IChromosome chromosome){
        List<Integer> path = new ArrayList<Integer>(amountOfTables);
        List<Integer> copyListOfTables = new ArrayList<Integer>(amountOfTables);

        for(Integer i : listOfTables){
            copyListOfTables.add(new Integer(i));
        }

        for(int i = 0; i < amountOfTables; i++){
            int number = (Integer) chromosome.getGene(i).getAllele();
            path.add(copyListOfTables.get(number));
            copyListOfTables.remove(number);
        }
        return path;
    }

}

