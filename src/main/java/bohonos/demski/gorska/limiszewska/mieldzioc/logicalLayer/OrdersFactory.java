package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;

import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.OrdersService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Agnieszka on 2015-06-06.
 */
public class OrdersFactory implements Runnable {
    public void run() {
        int counter = 0;
        while(true/*counter < 3*/){
            try {
                TimeUnit.SECONDS.sleep(5);
                OrdersService ordersService = null;
                try {
                    ordersService = OrdersService.getInstance();
                    ordersService.makeOrder(10);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter ++;
        }
    }
}
