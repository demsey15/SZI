package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.geneticAlgorithm;

import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dominik on 2015-06-05.
 */
public class PathFinder implements IWalker{
    private FindProperOrder properOrderFinder = new FindProperOrder();
    private Monitor monitor = Monitor.getInstance();
    private Control control = Control.getInstance();

    public void goThroughTables(List<Integer> tablesToGoThrow){
        List<Integer> properOrderTabels = properOrderFinder.findProperOrder(tablesToGoThrow);


        Coordinates currentPosition = new Coordinates(0, 0);   //zaczynamy od współrzędnych (0, 0)

        for(int i = 0; i < properOrderTabels.size() + 1; i++){ //+1 bo jeszcze powrot do (0,0)
            Coordinates cornerToGo;
            if(i < properOrderTabels.size()) {
                Coordinates tableToGo = control.getCoordinatesForTableNumber(properOrderTabels.get(i));
                cornerToGo = getTheClosestCorner(tableToGo, currentPosition);
            }
            else{
                cornerToGo = new Coordinates(0, 0);
            }
            List<Coordinates> path = new ArrayList<Coordinates>();
            while(!(currentPosition.getRow() == cornerToGo.getRow() &&  //jesli nie jestesmy jeszcze na miejscu
                    currentPosition.getColumn() == cornerToGo.getColumn())) {
                boolean wasStep = false;
                if (currentPosition.getRow() != cornerToGo.getRow()) {   //jesli jestesmy na zlej wysokosci
                    Coordinates toGo;
                    if (currentPosition.getRow() > cornerToGo.getRow()) { //jesli powinnismy isc w gore
                        toGo = new Coordinates(currentPosition.getRow() - 1, currentPosition.getColumn()); //spróbuj iść w górę
                    } else {    //jesli powinnismy isc w dol
                        toGo = new Coordinates(currentPosition.getRow() + 1, currentPosition.getColumn()); //spróbuj iść w dół
                    }
                    if (Map.checkIfCoordinatesAreInMap(toGo) && control.getObjectId(toGo) == Map.FREE_FIELD) {
                        path.add(toGo);
                        currentPosition = toGo;
                        wasStep = true;
                    }
                }
                if(!wasStep && currentPosition.getColumn() != cornerToGo.getColumn()){ //nie bylo ruchu i jestesmy na zlej pozycji w poziomie
                    Coordinates toGo;
                    if (currentPosition.getColumn() > cornerToGo.getColumn()) { //nalezy isc w lewo
                        toGo = new Coordinates(currentPosition.getRow(), currentPosition.getColumn() - 1); //spróbuj iść w lewo
                    } else {
                        toGo = new Coordinates(currentPosition.getRow(), currentPosition.getColumn() + 1); //spróbuj iść w prawo
                    }
                    if (Map.checkIfCoordinatesAreInMap(toGo) && control.getObjectId(toGo) == Map.FREE_FIELD) {
                        path.add(toGo);
                        currentPosition = toGo;
                        wasStep = true;
                    }
                }
                if(!wasStep) {   //standardowy ruch sie nie udal - wykonaj ruch awaryjny - po skosie
                    boolean wasHelpingMove = false;
                    if (currentPosition.getColumn() > cornerToGo.getColumn()) { //należy poruszać się w lewo
                        if(currentPosition.getRow() > cornerToGo.getRow()){ //należy poruszać się górę
                            Coordinates toGo = new Coordinates(currentPosition.getRow() + 1,
                                    currentPosition.getColumn() - 1);  //spróbuj iść po skosie w dół i w lewo
                            if(Map.checkIfCoordinatesAreInMap(toGo) && control.getObjectId(toGo) == Map.FREE_FIELD){
                                path.add(toGo);
                                currentPosition = toGo;
                                wasHelpingMove = true;
                            }
                            else{   //należy poruszac sie w gore i w lewo
                                toGo = new Coordinates(currentPosition.getRow() - 1,
                                        currentPosition.getColumn() + 1);  //spróbuj iść po skosie w górę i w prawo
                                if(Map.checkIfCoordinatesAreInMap(toGo) && control.getObjectId(toGo) == Map.FREE_FIELD){
                                    path.add(toGo);
                                    currentPosition = toGo;
                                    wasHelpingMove = true;
                                }
                            }
                        }
                        else if(currentPosition.getRow() < cornerToGo.getRow()){       //należy poruszać się w dół i w lewo
                            Coordinates toGo = new Coordinates(currentPosition.getRow() - 1,
                                    currentPosition.getColumn() - 1);  //spróbuj iść po skosie w górę i w lewo
                            if(Map.checkIfCoordinatesAreInMap(toGo) && control.getObjectId(toGo) == Map.FREE_FIELD){
                                path.add(toGo);
                                currentPosition = toGo;
                                wasHelpingMove = true;
                            }
                            else{
                                toGo = new Coordinates(currentPosition.getRow() + 1,
                                        currentPosition.getColumn() + 1);  //spróbuj iść po skosie w dół i w prawo
                                if(Map.checkIfCoordinatesAreInMap(toGo) && control.getObjectId(toGo) == Map.FREE_FIELD){
                                    path.add(toGo);
                                    currentPosition = toGo;
                                    wasHelpingMove = true;
                                }
                            }
                        }
                        else{       //tylko w lewo
                            Coordinates toGo = new Coordinates(currentPosition.getRow() - 1,
                                    currentPosition.getColumn() - 1);  //spróbuj iść po skosie w górę i w lewo
                            if (Map.checkIfCoordinatesAreInMap(toGo) && control.getObjectId(toGo) == Map.FREE_FIELD) {
                                path.add(toGo);
                                currentPosition = toGo;
                                wasHelpingMove = true;
                            } else {
                                toGo = new Coordinates(currentPosition.getRow() + 1,
                                        currentPosition.getColumn() - 1);  //spróbuj iść po skosie w dol i w lewo
                                if (Map.checkIfCoordinatesAreInMap(toGo) && control.getObjectId(toGo) == Map.FREE_FIELD) {
                                    path.add(toGo);
                                    currentPosition = toGo;
                                    wasHelpingMove = true;
                                }
                            }
                        }
                    }
                    else if(currentPosition.getColumn() < cornerToGo.getColumn()){  //należy poruszać się w prawo
                        if(currentPosition.getRow() > cornerToGo.getRow()){ //należy poruszać się górę
                            Coordinates toGo = new Coordinates(currentPosition.getRow() + 1,
                                    currentPosition.getColumn() + 1);  //spróbuj iść po skosie w dół i w prawo
                            if(Map.checkIfCoordinatesAreInMap(toGo) && control.getObjectId(toGo) == Map.FREE_FIELD){
                                path.add(toGo);
                                currentPosition = toGo;
                                wasHelpingMove = true;
                            }
                            else{
                                toGo = new Coordinates(currentPosition.getRow() - 1,
                                        currentPosition.getColumn() - 1);  //spróbuj iść po skosie w górę i w lewo
                                if(Map.checkIfCoordinatesAreInMap(toGo) && control.getObjectId(toGo) == Map.FREE_FIELD){
                                    path.add(toGo);
                                    currentPosition = toGo;
                                    wasHelpingMove = true;
                                }
                            }
                        }
                        else if(currentPosition.getRow() < cornerToGo.getRow()){       //należy poruszać się w dół i w prawo
                            Coordinates toGo = new Coordinates(currentPosition.getRow() - 1,
                                    currentPosition.getColumn() + 1);  //spróbuj iść po skosie w górę i w prawo
                            if(Map.checkIfCoordinatesAreInMap(toGo) && control.getObjectId(toGo) == Map.FREE_FIELD){
                                path.add(toGo);
                                currentPosition = toGo;
                                wasHelpingMove = true;
                            }
                            else{
                                toGo = new Coordinates(currentPosition.getRow() + 1,
                                        currentPosition.getColumn() - 1);  //spróbuj iść po skosie w dół i w lewo
                                if(Map.checkIfCoordinatesAreInMap(toGo) && control.getObjectId(toGo) == Map.FREE_FIELD){
                                    path.add(toGo);
                                    currentPosition = toGo;
                                    wasHelpingMove = true;
                                }
                            }
                        }
                        else{       //tylko w prawo
                            Coordinates toGo = new Coordinates(currentPosition.getRow() - 1,
                                    currentPosition.getColumn() + 1);  //spróbuj iść po skosie w górę i w prawo
                            if (Map.checkIfCoordinatesAreInMap(toGo) && control.getObjectId(toGo) == Map.FREE_FIELD) {
                                path.add(toGo);
                                currentPosition = toGo;
                                wasHelpingMove = true;
                            } else {
                                toGo = new Coordinates(currentPosition.getRow() + 1,
                                        currentPosition.getColumn() + 1);  //spróbuj iść po skosie w dol i w prawo
                                if (Map.checkIfCoordinatesAreInMap(toGo) && control.getObjectId(toGo) == Map.FREE_FIELD) {
                                    path.add(toGo);
                                    currentPosition = toGo;
                                    wasHelpingMove = true;
                                }
                            }
                        }
                    }
                    else{  //nalezy poruszac sie tylko w gore / dol
                        if(currentPosition.getRow() > cornerToGo.getRow()){ //nalezy isc tylko w gore
                            Coordinates toGo = new Coordinates(currentPosition.getRow() - 1,
                                    currentPosition.getColumn() + 1);  //spróbuj iść po skosie w górę i w prawo
                            if (Map.checkIfCoordinatesAreInMap(toGo) && control.getObjectId(toGo) == Map.FREE_FIELD) {
                                path.add(toGo);
                                currentPosition = toGo;
                                wasHelpingMove = true;
                            } else {
                                toGo = new Coordinates(currentPosition.getRow() - 1,
                                        currentPosition.getColumn() - 1);  //spróbuj iść po skosie w gore i w lewo
                                if (Map.checkIfCoordinatesAreInMap(toGo) && control.getObjectId(toGo) == Map.FREE_FIELD) {
                                    path.add(toGo);
                                    currentPosition = toGo;
                                    wasHelpingMove = true;
                                }
                            }
                        }
                        else{ //nalezy isc tylko w dol
                            Coordinates toGo = new Coordinates(currentPosition.getRow() + 1,
                                    currentPosition.getColumn() + 1);  //spróbuj iść po skosie w dol i w prawo
                            if (Map.checkIfCoordinatesAreInMap(toGo) && control.getObjectId(toGo) == Map.FREE_FIELD) {
                                path.add(toGo);
                                currentPosition = toGo;
                                wasHelpingMove = true;
                            } else {
                                toGo = new Coordinates(currentPosition.getRow() + 1,
                                        currentPosition.getColumn() - 1);  //spróbuj iść po skosie w dol i w lewo
                                if (Map.checkIfCoordinatesAreInMap(toGo) && control.getObjectId(toGo) == Map.FREE_FIELD) {
                                    path.add(toGo);
                                    currentPosition = toGo;
                                    wasHelpingMove = true;
                                }
                            }
                        }

                    }

                    if(!wasHelpingMove){
                        System.out.println("Nie mogę znaleźć ścieżki!");
                        break;
                    }
                }
                /* //Wypisywanie z opoznieniem sciezki ktora znajduje
                System.out.print(currentPosition + ", ");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                */
            }
            monitor.callListenersOnMove(path);
            if(i < properOrderTabels.size())
            System.out.println("Idę do stolika nr: " + properOrderTabels.get(i));
            else  System.out.println("Wracam do (0, 0)");
            System.out.println("Sciezka: " + Arrays.toString(path.toArray()));
            try {
                Thread.sleep(3000);
                //TUTAJ DODAJ ZDEJMOWANIE POTRAW Z LISTY STOLIKA Z i-tej POZYCJI
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Coordinates getTheClosestCorner(Coordinates tableCoordinates, Coordinates currentPosition){
        Coordinates leftUp = new Coordinates(tableCoordinates.getRow() - 1, tableCoordinates.getColumn() - 1);
        Coordinates leftDown = new Coordinates(tableCoordinates.getRow() + 1, tableCoordinates.getColumn() - 1);
        Coordinates rightUp =  new Coordinates(tableCoordinates.getRow() + 1, tableCoordinates.getColumn() + 1);
        Coordinates rightDown = new Coordinates(tableCoordinates.getRow() - 1, tableCoordinates.getColumn() + 1);

        List<Coordinates> correctCoordinates = new ArrayList<Coordinates>(4);
        if(Map.checkIfCoordinatesAreInMap(leftUp)) correctCoordinates.add(leftUp);
        if(Map.checkIfCoordinatesAreInMap(leftDown)) correctCoordinates.add(leftDown);
        if(Map.checkIfCoordinatesAreInMap(rightUp)) correctCoordinates.add(rightUp);
        if(Map.checkIfCoordinatesAreInMap(rightDown)) correctCoordinates.add(rightDown);

        if(correctCoordinates.size() > 0){
            Coordinates theBest = correctCoordinates.get(0);
            int bestDistance;
            for(int i = 1; i < correctCoordinates.size(); i++){
                bestDistance = getDistanceBetweenCoordinates(theBest, currentPosition);
                Coordinates coord = correctCoordinates.get(i);
                int distance = getDistanceBetweenCoordinates(coord, currentPosition);
                if(distance < bestDistance) theBest = coord;
            }
            return theBest;
        }
        else return null;
    }

    private int getDistanceBetweenCoordinates(Coordinates coordinates1, Coordinates coordinates2){
        return Math.abs(coordinates1.getColumn() - coordinates2.getColumn()) + Math.abs(coordinates1.getRow()
                - coordinates2.getRow());
    }

    public static void main(String[] args) {
        Control control = Control.getInstance();
        try {
            control.prepareMap();
            PathFinder f = new PathFinder();
            f.goThroughTables(Arrays.asList(new Integer[]{1, 2, 15, 18, 4, 6, 8, 10, 7}));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
