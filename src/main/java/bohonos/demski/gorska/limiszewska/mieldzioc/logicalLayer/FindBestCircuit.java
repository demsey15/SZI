/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Andrzej
 */
public class FindBestCircuit {
    
    public ArrayList<Integer> getOrderList(ShortestPaths sPaths, Set<Integer> vert) {
        
        ShortestPaths shortestPaths = sPaths;
        Set<Integer> vertices = vert;
        int numberOfVertices = vertices.size();
        HashMap<Integer,HashMap<Integer,Integer>> availablePaths = new HashMap<Integer,HashMap<Integer,Integer>>();
        Set<Integer> visited = new HashSet<Integer>();
        Set<Integer> toVisit = new HashSet<Integer>();
        
        visited.add(0);
        availablePaths.put(0, new HashMap<Integer,Integer>());
        for (int i=1; i<=numberOfVertices; i++) {
            availablePaths.get(0).put(i, shortestPaths.getPathLength(0, i));
        }
        
        toVisit.addAll(vertices);
        toVisit.remove(0);
        
        
        
        return null; //to do
    }
    
    
}
