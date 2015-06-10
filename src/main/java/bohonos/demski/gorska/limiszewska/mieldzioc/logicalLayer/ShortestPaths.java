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
        //System.out.println("liczba stolików: " + numberOfTables);

        places.add(0, new Coordinates(0,0));
        for (int i=1; i<=numberOfTables; i++) {
            places.add(i, new Coordinates(control.getCoordinatesForTableNumber(i).getRow()-1, control.getCoordinatesForTableNumber(i).getColumn()-1));
        }

        for (int i=0; i<17; i++) {
            for (int j=0; j<21; j++) {
                graph[i][j]=1;
            }
        }

        //System.out.println("miejsca uzupe³nione");

        for (int i=1; i<=numberOfTables; i++) {
            graph[places.get(i).getRow()+1][places.get(i).getColumn()+1] = 0;
            graph[places.get(i).getRow()+1][places.get(i).getColumn()] = 0;
            graph[places.get(i).getRow()+1][places.get(i).getColumn()+2] = 0;
            graph[places.get(i).getRow()][places.get(i).getColumn()+1] = 0;
            graph[places.get(i).getRow()+2][places.get(i).getColumn()+1] = 0;
        }

        //System.out.println("graf uzupe³niony");
        //this.printGrapf();

        for (int i=0; i<=numberOfTables; i++) {
            paths.add(i, dijkstraAlgorithm(i));
        }
        System.out.println("Obiekt klasy ShortestPaths zosta³ utworzony.\n");
    }

    /**
     * Zmodyfikowany Algorytm Dijkstry
     * @param source stolik pocz¹tkowy
     * @return najkrótsze œcie¿ki
     */
    private ArrayList<ArrayList<Coordinates>> dijkstraAlgorithm(int source) {

        //System.out.println("wywo³ujê algorytm Dijkstry dla stolika " + source);

        int[][] localGraph = new int[17][21];
        Set<Coordinates> checked = new HashSet<Coordinates>(400);
        Set<Coordinates> toCheck = new HashSet<Coordinates>(400);
        Set<Coordinates> nextToCheck = new HashSet<Coordinates>(400);
        checked.clear();
        toCheck.clear();
        nextToCheck.clear();
        int currentPathLength = 0;
        ArrayList<ArrayList<Coordinates>> localPaths = new ArrayList<ArrayList<Coordinates>>();
        localPaths.clear();

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

        //System.out.println("graf lokalny dla stolika " + source + " stworzony");

        localGraph[places.get(source).getRow()][places.get(source).getColumn()] = 0;
        toCheck.add(places.get(source));

        //while (checked.size()<357) {
        while (toCheck.size()!=0) {
            //System.out.println("jestem w while");
            currentPathLength++;
            for (Coordinates coord : toCheck) {
                //System.out.println("sprawdzam wsp");
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
            toCheck.clear();
            toCheck.addAll(nextToCheck);
            //System.out.println("rozmiar toCheck: " + toCheck.size());
            nextToCheck.clear();
            //System.out.println("rozmiar toCheck: " + toCheck.size());
        }
        /*System.out.println("graf lokalny dla stolika " + source + " uzupe³niony");
        for (int i=0; i<17; i++) {
            for (int j=0; j<21; j++) {
                if (localGraph[i][j]<10 && localGraph[i][j]>-1)
                    System.out.print(" " + localGraph[i][j] + " ");
                else
                    System.out.print(localGraph[i][j] + " ");
            }
            System.out.println("");
        }*/

        for (int j=0; j<places.size(); j++) {
            //System.out.println("rozpoczynam zczytywanie œcie¿ki");
            Coordinates coord = places.get(j);
            int pathLength = localGraph[coord.getRow()][coord.getColumn()];
            ArrayList<Coordinates> localPath = new ArrayList<Coordinates>();
            localPath.clear();
            localPath.add(coord);
            //System.out.println("d³ugoœæ œcie¿ki: " + pathLength);
            for (int i = pathLength; i>0; i--) {
                //System.out.println("wchodzê do for");
                boolean foundNext = false;
                if (coord.getTop()!=null && foundNext==false){
                    if (localGraph[coord.getTop().getRow()][coord.getTop().getColumn()]==i-1) {
                        coord = coord.getTop();
                        localPath.add(coord);
                        foundNext = true;
                    }
                }

                if (coord.getLeft()!=null && foundNext==false){
                    if (localGraph[coord.getLeft().getRow()][coord.getLeft().getColumn()]==i-1) {
                        coord = coord.getLeft();
                        localPath.add(coord);
                        foundNext = true;
                    }
                }

                if (coord.getBottom()!=null && foundNext==false){
                    if (localGraph[coord.getBottom().getRow()][coord.getBottom().getColumn()]==i-1) {
                        coord = coord.getBottom();
                        localPath.add(coord);
                        foundNext = true;
                    }
                }

                if (coord.getRight()!=null && foundNext==false){
                    if (localGraph[coord.getRight().getRow()][coord.getRight().getColumn()]==i-1) {
                        coord = coord.getRight();
                        localPath.add(coord);
                        foundNext = true;
                    }
                }
            }
            localPaths.add(j, localPath);
            //System.out.println("œcie¿ka dodana");
        }
        //System.out.println("koniec dzia³ania alg Dijkstry dla " + source);
        return localPaths;
    }

    /**
     * zwraca najkrótsz¹ œcie¿kê pomiêdzy dwoma stolikami
     * @param source stolik pocz¹tkowy
     * @param destination stolik koñcowy
     * @return œcie¿ka w postaci listy kolejnych wspó³rzêdnych
     */
    public ArrayList<Coordinates> getPath(int source, int destination) {
        return paths.get(destination).get(source);
    }

    /**
     * zwraca d³ugoœæ najkrótszej œcie¿ki pomiêdzy dwoma stolikami
     * @param source stolik pocz¹tkowy
     * @param destination stolik koñcowy
     * @return d³ugoœæ œcie¿ki
     */
    public int getPathLength(int source, int destination) {
        return paths.get(destination).get(source).size();
    }

    /**
     * drukuje wszystkie œcie¿ki z podanego miejsca pocz¿tkowego i ich d³ugoœci
     * @param destination miejsce koñcowe
     */
    public void printPathsTo(int destination) {
        int placesNumber = places.size();
        int pathLength;
        for (int i = 0; i < placesNumber; i++) {
            pathLength = paths.get(destination).get(i).size();
            System.out.print("Sciezka z " + i + " do " + destination + " : ");
            for (int j = 0; j < pathLength; j++) {
                System.out.print(" (" + paths.get(destination).get(i).get(j).getColumn() + "," + paths.get(destination).get(i).get(j).getRow() + ")");
            }
            System.out.println(" dlugosc sciezki: " + pathLength);
        }
    }

    /**
     * drukuje wszystkie œcie¿ki
     */
    public void printAllPaths() {
        int placesNumber = places.size();
        for (int i = 0; i < placesNumber; i++) {
            printPathsTo(i);
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
        System.out.println("Utworzy³em control.");
        try {
            control.prepareMap();
            System.out.println("Przygotowa³em mapê.");
            ShortestPaths shortestPaths = new ShortestPaths();
            shortestPaths.printGrapf();
            shortestPaths.printPathsTo(7);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Wyjatek przy tworzeniu mapy.");
        }

    }

}