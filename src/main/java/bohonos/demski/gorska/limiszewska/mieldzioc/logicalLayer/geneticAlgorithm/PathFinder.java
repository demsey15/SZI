package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.geneticAlgorithm;

import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.Control;
import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.Coordinates;
import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.Monitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 2015-06-05.
 */
public class PathFinder {
    private FindProperOrder properOrderFinder = new FindProperOrder();
    private Monitor monitor = Monitor.getInstance();
    private Control control = Control.getInstance();

    public List<Coordinates> getPathForTables(List<Integer> tablesToGoThrow){
        List<Integer> properOrderTabels = properOrderFinder.findProperOrder(tablesToGoThrow);
        List<Coordinates> path = new ArrayList<Coordinates>();

        Coordinates currentPosition = new Coordinates(0, 0);   //zaczynamy od wspó³rzêdnych (0, 0)

        for(int i = 0; i < properOrderTabels.size(); i++){

        }

    }
}
