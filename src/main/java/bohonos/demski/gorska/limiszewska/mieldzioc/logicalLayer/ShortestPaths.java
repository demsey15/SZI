package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrzej
 */
public class ShortestPaths {
    
    private ArrayList<ArrayList<ArrayList<Coordinates>>> paths = new ArrayList<ArrayList<ArrayList<Coordinates>>>();
    private ArrayList<Coordinates> places = new ArrayList<Coordinates>();
    private int[][] graph = new int[19][23];
    private int numberOfTables;
    
    
    public ShortestPaths(List<Table> tables) {
        
        numberOfTables = tables.size();
        
        for (int i=1; i<=17; i++) {
            for (int j=1; j<=21; j++) {
                graph[i][j]=1;
            }
        }
        
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
