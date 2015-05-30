package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Andrzej
 */
public class ShortestPaths {
    
    private ArrayList<ArrayList<ArrayList<Coordinates>>> paths = new ArrayList<ArrayList<ArrayList<Coordinates>>>();
    private ArrayList<Coordinates> places = new ArrayList<Coordinates>();
    private int[][] graph = new int[17][21];
    private int numberOfTables;
    
    
    public ShortestPaths(List<Table> tables) {
        
        numberOfTables = tables.size();
        
        for (int i=0; i<17; i++) {
            for (int j=0; j<21; j++) {
                graph[i][j]=1;
            }
        }
        
        places.add(0, new Coordinates(0,0));
        
        for (int i=0; i<tables.size(); i++) {
            graph[tables.get(i).getCoords().getRow()][tables.get(i).getCoords().getColumn()] = 0;
            graph[tables.get(i).getCoords().getRow()-1][tables.get(i).getCoords().getColumn()-1] = 0;
            graph[tables.get(i).getCoords().getRow()-1][tables.get(i).getCoords().getColumn()+1] = 0;
            graph[tables.get(i).getCoords().getRow()+1][tables.get(i).getCoords().getColumn()-1] = 0;
            graph[tables.get(i).getCoords().getRow()+1][tables.get(i).getCoords().getColumn()+1] = 0;
            places.add(tables.get(i).getTableNumber(), tables.get(i).getSeatCollection().getSeat(0).getCoords());
        }
        
        for (int i=0; i<=numberOfTables; i++) {
            paths.add(i, dijkstraAlgorithm(i));
        }
        
    }
    
    /**
     * Zmodyfikowany Algorytm Dijkstry
     * @param source stolik pocz¹tkowy
     * @return najkrótsze œcie¿ki
     */
    private ArrayList<ArrayList<Coordinates>> dijkstraAlgorithm(int source) {
        
        int[][] localGraph = new int[17][21];
        Set<Coordinates> checked = new HashSet<Coordinates>(400);
        Set<Coordinates> toCheck = new HashSet<Coordinates>(400);
        Set<Coordinates> nextToCheck = new HashSet<Coordinates>(400);
        int currentPathLength = 0;
        
        for (int i=0; i<17; i++) {
            for (int j=0; j<21; j++) {
                if (graph[i][j]==0) {
                   localGraph[i][j]=-1;
                   checked.add(new Coordinates(i,j));
                }
                if (graph[i][j]==1) {
                    localGraph[i][j]=200;
                }
            }
        }
        
        localGraph[places.get(source).getRow()][places.get(source).getColumn()] = 0;
        toCheck.add(places.get(source));
        
        while (checked.size()<=357) {
            currentPathLength++;
            for (Coordinates coord : toCheck) {
                if (coord.getTop()!=null) {
                    if (localGraph[coord.getTop().getRow()][coord.getTop().getColumn()]==200) {
                        localGraph[coord.getTop().getRow()][coord.getTop().getColumn()]=currentPathLength;
                        nextToCheck.add(coord.getTop());
                    }     
                }
                if (coord.getBottom()!=null) {
                    if (localGraph[coord.getBottom().getRow()][coord.getBottom().getColumn()]==200) {
                        localGraph[coord.getBottom().getRow()][coord.getBottom().getColumn()]=currentPathLength;
                        nextToCheck.add(coord.getBottom());
                    }     
                }
                if (coord.getLeft()!=null) {
                    if (localGraph[coord.getLeft().getRow()][coord.getLeft().getColumn()]==200) {
                        localGraph[coord.getLeft().getRow()][coord.getLeft().getColumn()]=currentPathLength;
                        nextToCheck.add(coord.getLeft());
                    }     
                }
                if (coord.getRight()!=null) {
                    if (localGraph[coord.getRight().getRow()][coord.getRight().getColumn()]==200) {
                        localGraph[coord.getRight().getRow()][coord.getRight().getColumn()]=currentPathLength;
                        nextToCheck.add(coord.getRight());
                    }     
                }
            }
            checked.addAll(toCheck);
            toCheck = nextToCheck;
            nextToCheck.clear();
        }
        return null; //to do
    }
    
    /**
     * zwraca najkrótsz¹ œcie¿kê pomiêdzy dwoma stolikami
     * @param source stolik pocz¹tkowy
     * @param destination stolik koñcowy
     * @return œcie¿ka w postaci listy kolejnych wspó³rzêdnych
     */
    public ArrayList<Coordinates> getPath(int source, int destination) {
        return paths.get(source).get(destination);
    }
    
    /**
     * zwraca d³ugoœæ najkrótszej œcie¿ki pomiêdzy dwoma stolikami
     * @param source stolik pocz¹tkowy
     * @param destination stolik koñcowy
     * @return d³ugoœæ œcie¿ki
     */
    public int getPathLength(int source, int destination) {
        return paths.get(source).get(destination).size();
    }
    
}
