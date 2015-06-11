package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.geneticAlgorithm;

import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.Control;
import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.Coordinates;
import org.jgap.*;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

import java.io.IOException;
import java.util.*;

/**
 * Created by Dominik on 2015-06-05.
 */
public class FindProperOrder {

        private Control control = Control.getInstance();
        private double[][] distanceMatrix;

        private final int POPULATION_SIZE = 1000;
        private final int NUMBER_OF_EVOLUTION = 2000;

        public FindProperOrder() {
            List<Coordinates> allTables = control.getAllTablesCoordinates();
            if(allTables == null) throw new NullPointerException("Stworz najpierw mape");

            distanceMatrix = new double[allTables.size() + 1][allTables.size() + 1]; //wszystkie sto³y + punkt wyjœcia

            Collections.sort(allTables, new Comparator<Coordinates>() {
                public int compare(Coordinates o1, Coordinates o2) {
                    int table1 = control.getTableNumber(o1);
                    int table2 = control.getTableNumber(o2);

                    return table1 - table2;   //wczeœniej ma byæ ten o ni¿szym numerze
                }
            });

            for(int i = 0; i < allTables.size(); i++){
                Coordinates coordinates = allTables.get(i);
                double distance = Math.abs(coordinates.getColumn()) + Math.abs(coordinates.getRow());
                distanceMatrix[0][i + 1] = distance;
                distanceMatrix[i + 1][0] = distance;
            }

            for(int i = 1; i <= allTables.size(); i++){
                Coordinates first = allTables.get(i - 1);
                for(int j = i; j <= allTables.size(); j++){
                    Coordinates second = allTables.get(j - 1);
                    double distance =  Math.abs(first.getRow() - second.getRow())
                            + Math.abs(first.getColumn() - second.getColumn());
                    distanceMatrix[i][j] = distance;
                    distanceMatrix[j][i] = distance;
                }
            }
/*  //  Wydrukuj macierz odleglosci
            for(int i = 0; i < distanceMatrix.length; i++){
                System.out.println(Arrays.toString(distanceMatrix[i]));
            }
            */
        }

        public List<Integer> findProperOrder(List<Integer> tablesToGoThrow){

            Configuration conf = new DefaultConfiguration();
            try {
                MyFitnessFunction fitnessFunction = new MyFitnessFunction(tablesToGoThrow, distanceMatrix);
                conf.reset();
                conf.setFitnessFunction(fitnessFunction);

                int numberOfTablesToGo = tablesToGoThrow.size();

                Gene[] sampleGenes = new Gene[numberOfTablesToGo];
                for(int i = 0; i < numberOfTablesToGo; i++){
                    sampleGenes[i] = new IntegerGene(conf, 0, numberOfTablesToGo - i - 1);
                }

                Chromosome sampleChromosome = new Chromosome(conf, sampleGenes);

                conf.setSampleChromosome(sampleChromosome);

                conf.setPopulationSize(POPULATION_SIZE);

                Genotype population = Genotype.randomInitialGenotype(conf);

                for(int i = 0; i < NUMBER_OF_EVOLUTION; i++){
                    population.evolve();
                }

                IChromosome bestSolution = population.getFittestChromosome();

                List<Integer> solution = fitnessFunction.convertChromosomeToTableList(bestSolution);
                double distance = fitnessFunction.getPathLength(bestSolution);

                System.out.println("Rozwi¹zanie:\n" + Arrays.toString(solution.toArray()));
                System.out.println("D³ugoœæ œcie¿ki:\n" + distance);
                return solution;
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }
            return null;
        }

    public static void main(String[] args) {
        Control control = Control.getInstance();
        try {
            control.prepareMap();
            FindProperOrder f = new FindProperOrder();
            f.findProperOrder(Arrays.asList(new Integer[] {1, 2, 5, 15}));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

