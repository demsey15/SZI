/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Andrzej
 */
public class FindBestCircuit {

    /**
     * optymalizuje trasê kelnera
     * @param sPaths lista najkrótszych œcie¿ek pomiêdzy poszczególnymi stolikami i miejscem startowym
     * @param vert stoliki, które nale¿y odwiedziæ
     * @return lista stolików (na pierwszym i ostatnim miejscu znajduje siê pozycja pocz¹tkowa)
     */
    public ArrayList<Integer> getOrderList(ShortestPaths sPaths, Set<Integer> vert) {

        ShortestPaths shortestPaths = sPaths;
        Set<Integer> vertices = vert;
        int numberOfVertices = vertices.size();
        HashMap<Integer,HashMap<Integer,Integer>> availablePaths = new HashMap<Integer,HashMap<Integer,Integer>>();
        Set<Integer> visited = new HashSet<Integer>();
        Set<Integer> toVisit = new HashSet<Integer>();
        int next[] = new int[22];
        next[0] = 0;
        int shortestPathSource = 0;
        int shortestPathDestination = 0;
        int shortestPathLength;
        ArrayList<Integer> outputList = new ArrayList<Integer>();
        int currentVertex = 0;

        outputList.add(0);

        toVisit.addAll(vertices);

        visited.add(0);
        availablePaths.put(0, new HashMap<Integer,Integer>());
        for (int vertex : toVisit) {
            availablePaths.get(0).put(vertex, shortestPaths.getPathLength(0, vertex));
        }

        while (toVisit.isEmpty()==false) {
            shortestPathLength = 200;
            for (int source : visited) {
                for (int destination : availablePaths.get(source).keySet()) {
                    if (availablePaths.get(source).get(destination)<shortestPathLength) {
                        shortestPathLength = availablePaths.get(source).get(destination);
                        shortestPathSource = source;
                        shortestPathDestination = destination;
                    }
                }
            }
            next[shortestPathDestination] = next[shortestPathSource];
            next[shortestPathSource] = shortestPathDestination;
            visited.add(shortestPathDestination);
            if (toVisit.contains(shortestPathDestination))
                toVisit.remove(shortestPathDestination);
            for (int vertex : visited) {
                if (availablePaths.containsKey(vertex))
                    if (availablePaths.get(vertex).containsKey(shortestPathDestination))
                        availablePaths.get(vertex).remove(shortestPathDestination);
            }
            availablePaths.put(shortestPathDestination, new HashMap<Integer,Integer>());
            for (int vertex : toVisit) {
                availablePaths.get(shortestPathDestination).put(vertex, shortestPaths.getPathLength(shortestPathDestination, vertex));
            }
            /*int currentV=0;
            ArrayList<Integer> outputL = new ArrayList<Integer>();
            outputL.add(0);
            do {
                
                currentV=next[currentV];
                outputL.add(currentV);
            } while (currentV != 0);
            this.printListOfPlaces(outputL);*/
        }


        do {
            currentVertex=next[currentVertex];
            outputList.add(currentVertex);
        } while (currentVertex != 0);

        return outputList;
    }

    /**
     * drukuje listê kolejnych wierzcho³ków
     * @param list lista wygenerowana metod¹ getOrderList()
     */
    public void printListOfPlaces(ArrayList<Integer> list) {
        System.out.println("");
        for (int i : list) {
            System.out.print(i + " ");
        }
        System.out.println("");
    }



    public static void main(String[] args) {
        Control control = Control.getInstance();
        System.out.println("Utworzy³em control.");
        try {
            control.prepareMap();
            System.out.println("Przygotowa³em mapê.");
            ShortestPaths shortestPaths = new ShortestPaths();
            //shortestPaths.printGrapf();
            //shortestPaths.printPathsTo(7);
            HashSet<Integer> vertices = new HashSet<Integer>();
            vertices.add(3);
            vertices.add(11);
            vertices.add(16);
            vertices.add(8);
            vertices.add(19);
            vertices.add(13);
            System.out.println("Zbiór utworzony.");
            FindBestCircuit best = new FindBestCircuit();
            System.out.println("Obiekt klasy FindBestCircuit stworzony.");
            best.printListOfPlaces(best.getOrderList(shortestPaths, vertices));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Wyjatek przy tworzeniu mapy.");
        }
    }

}
