package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;

/**
 * Created by Agnieszka on 2015-06-08.
 */
import ViewLayer.SettingsFrame;
import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.geneticAlgorithm.PathFinder;
import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.neuralNetwork.StayOrGo;
import java.util.*;
import java.io.IOException;


public class IfWaiterGoThread implements Runnable {

    public void run() {

        StayOrGo stayOrGo = null;
        try {
            stayOrGo = new StayOrGo(0.2, 0.6, 0.12);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PathFinder pathFinder = new PathFinder();

        while (true) {
            double[] x = new double[5];
            OrdersService ordersService = null;
            try {
                ordersService = OrdersService.getInstance();
            } catch (IOException e) {
                e.printStackTrace();
            }

                     //a co jak taca nie jest pusta i list gotowych posi³ków te¿ nie  //Popraw
            if (ordersService.getReadyMeals().size() > 0 ) {
                List<Order> readyMeals = new ArrayList<Order>(); //pomocnicza lista
                for (Order o : ordersService.getReadyMeals()) {
                    ordersService.addMealToTray(o);
                    readyMeals.add(o);
                }
                for (Order o : readyMeals) {
                    ;ordersService.removeReadyMeals(o);
                }
            }

            double y=0;
            x[0]=0;

            for(Order o : ordersService.getTray()){
                Meal meal = o.getMeal();
                y+=meal.getArea();
                if(meal.getTemp()>x[0]) {
                    x[0] = meal.getTemp();
                }
            }
            x[1]=y;
            if(ordersService.getCurrentCreatingMeals().size()>0 && ordersService.getTray().size()>0) {

                //czekanie na jakiœ element
                //List<Order> currentCreatingMeals= ordersService.getCurrentCreatingMeals();
                //Order o = currentCreatingMeals.get(0);
                x[2] = ordersService.getCurrentCreatingMeals().get(0).getMeal().getTemp();
                x[3] = ordersService.getCurrentCreatingMeals().get(0).getMeal().getArea();
                x[4] = ordersService.getCurrentCreatingMeals().get(0).getMeal().getTime();

                stayOrGo.neuralNetwork.setInputs(x);

                double v = stayOrGo.neuralNetwork.getOutput()[0];

                if (v >= 0.5) {
                    List<Integer> stoliki = new ArrayList<Integer>();
                    for(Order o : ordersService.getTray()){
                        if(!stoliki.contains(o.tableNumber)) {
                            stoliki.add(o.tableNumber);
                        }
                    }

                    if (SettingsFrame.getSelectedAlgorithms().equals("Andrzej"))
                    {

                    }
                    else
                    {
                       // System.out.println("Liczba stolikow: "+stoliki.size());
                        pathFinder.goThroughTables(stoliki);
                    }
                }
            }
        }
    }
}
