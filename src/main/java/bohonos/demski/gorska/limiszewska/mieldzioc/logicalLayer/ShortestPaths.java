package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;

import java.io.IOException;
import java.util.*;

/**
 *
 * @author Andrzej
 */
public class ShortestPaths {
    
    private ArrayList<ArrayList<ArrayList<Coordinates>>> paths = new ArrayList<ArrayList<ArrayList<Coordinates>>>();
    private ArrayList<Coordinates> places = new ArrayList<Coordinates>();
    private int[][] graph = new int[17][21];
    private int numberOfTables;
    private List<Coordinates> listOfTablesCoord;
    
    
    public ShortestPaths() {
        
        Control control = Control.getInstance();
        listOfTablesCoord = control.getAllTablesCoordinates();
        numberOfTables = listOfTablesCoord.size();
        
        places.add(0, new Coordinates(0,0));
        for (int i=1; i<=numberOfTables; i++) {
            places.add(i, new Coordinates(control.getCoordinatesForTableNumber(i).getRow()-1, control.getCoordinatesForTableNumber(i).getColumn()-1));
        }
        
        for (int i=0; i<17; i++) {
            for (int j=0; j<21; j++) {
                graph[i][j]=1;
            }
        }
        
        
        
        for (int i=1; i<=numberOfTables; i++) {
            graph[places.get(i).getRow()][places.get(i).getColumn()] = 0;
            graph[places.get(i).getRow()][places.get(i).getColumn()-1] = 0;
            graph[places.get(i).getRow()][places.get(i).getColumn()+1] = 0;
            graph[places.get(i).getRow()-1][places.get(i).getColumn()] = 0;
            graph[places.get(i).getRow()+1][places.get(i).getColumn()] = 0;
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
        ArrayList<ArrayList<Coordinates>> localPaths = new ArrayList<ArrayList<Coordinates>>();
        
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
        
        while (checked.size()<357) {
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
        for (int j=0; j<=places.size(); j++) {
            Coordinates coord = places.get(j);
            int pathLength = localGraph[coord.getRow()][coord.getColumn()];
            ArrayList<Coordinates> localPath = new ArrayList<Coordinates>();
            localPath.add(pathLength, coord);
            for (int i = pathLength; i>0; i--) {
                if (coord.getTop()!=null){
                    if (localGraph[coord.getTop().getRow()][coord.getTop().getColumn()]==i-1) {
                        coord = coord.getTop();
                        localPath.add(i-1, coord);
                    }
                }
                else {
                    if (coord.getLeft()!=null){
                        if (localGraph[coord.getLeft().getRow()][coord.getLeft().getColumn()]==i-1) {
                            coord = coord.getLeft();
                            localPath.add(i-1, coord);
                        }
                    }
                    else {
                        if (coord.getBottom()!=null){
                            if (localGraph[coord.getBottom().getRow()][coord.getBottom().getColumn()]==i-1) {
                                coord = coord.getBottom();
                                localPath.add(i-1, coord);
                            }
                        }
                        else {
                            if (coord.getRight()!=null){
                                if (localGraph[coord.getRight().getRow()][coord.getRight().getColumn()]==i-1) {
                                    coord = coord.getRight();
                                    localPath.add(i-1, coord);
                                }
                            }
                        }
                    }
                }
            }
            localPaths.add(j, localPath);
        }
        return localPaths;
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
    
    /**
     * drukuje wszystkie œcie¿ki z podanego miejsca pocz¿tkowego i ich d³ugoœci
     * @param source miejsce pocz¹tkowe
     */
    public void printPathsFrom(int source) {
        int placesNumber = places.size();
        int pathLength;
        for (int i = 0; i <= placesNumber; i++) {
            pathLength = paths.get(source).get(i).size();
            System.out.print("Sciezka z " + source + " do " + i + " : ");
            for (int j = 0; j <= pathLength; j++) {
                System.out.print(" (" + paths.get(source).get(i).get(j).getColumn() + "," + paths.get(source).get(i).get(j).getRow() + ")");
            }
            System.out.println(" dlugosc sciezki: " + pathLength);
        }
    }
    
    /**
     * drukuje wszystkie œcie¿ki
     */
    public void printAllPaths() {
        int placesNumber = places.size();
        for (int i = 0; i <= placesNumber; i++) {
            printPathsFrom(i);
        }
    }

    /**
     * drukuje ca³y graf
     */
    public void printGrapf() {
        for (int i=0; i<17; i++) {
            for (int j=0; j<21; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        Control control = Control.getInstance();
        try {
            control.prepareMap();
            ShortestPaths shortestPaths = new ShortestPaths();
            shortestPaths.printGrapf();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Wyjatek przy tworzeniu mapy.");
        }

    }
    
}
