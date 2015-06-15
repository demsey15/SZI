package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
/**
 * Created by Delirus on 2015-06-14.
 */
public class GoThroughTables {

    private Monitor monitor = Monitor.getInstance();

    public void goTables(ShortestPaths shortestPaths, List<Integer> tablesList){

        HashSet<Integer> tablesSet = new HashSet<Integer>();
        for (int table : tablesList) {
            if (table != 0) {
                tablesSet.add(table);
            }
        }
        FindBestCircuit bestCircuit = new FindBestCircuit();
        ArrayList<Integer> orderList = bestCircuit.getOrderList(shortestPaths, tablesSet);
        for (int i=0; i<orderList.size()-1; i++) {
            ArrayList<Coordinates> path = shortestPaths.getPath(orderList.get(i), orderList.get(i+1));
            monitor.callListenersOnMove(path);
            try {
                Thread.sleep(3000);
                try {
                    if(i < orderList.size()-1)
                        OrdersService.getInstance().removeMealForTableFromTray(orderList.get(i+1));  //zdejmij dostarczone potrawy z listy potraw na tacy kelnera
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
