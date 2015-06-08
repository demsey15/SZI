package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;

import java.util.Calendar;

/**
 * Created by Marta Górska.
 */
public class Order {

    //------------------------------------------------------------------------------------------------------------------
    //AL 2015-06-06
    int tableNumber; //nr stolika
    Meal meal;  //danie z menu
    long orderTime; //czas zamowienia, jako int, podlagajacy inkrementacji
    int prepareTime; //czas przygotowania, wywodzi sie z menu
    boolean VIP; //czy zamowienie jest dla VIPA
    boolean liquid; //czy jest to napoj (ma znaczenie w drzewie decyzyjnym)


    public Order(Meal meal, int tableNumber, long orderTime, boolean VIP){
        this.tableNumber = tableNumber;
        this.meal = meal;
        prepareTime = meal.getTime();
        liquid = meal.getType() == 0;
        this.orderTime = orderTime;
        this.VIP = VIP;
    }

    public boolean isLiquid(){
        return liquid;
    }

    public boolean isVIP(){
        return VIP;
    }

    public boolean isLongPreparedTime(){
        return prepareTime > 5;
    }

    public boolean isWaitedLong(){
        Calendar cal = Calendar.getInstance();
        long now = cal.getTimeInMillis();
        if ((orderTime - now)/8000 > 0) return true;
        return false;
    }
    public Meal getMeal(){
        return meal;
    }

    public boolean equals(Order order){
        if ((this.tableNumber == order.tableNumber) &&
                (this.meal.equals(order.meal)) &&
                (this.orderTime == order.orderTime) &&
                (this.VIP == order.VIP)) {
            return true;
        }
        return false;
    }

}
