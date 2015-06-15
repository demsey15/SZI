package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.decisionTree;

import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.Order;
import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.OrdersService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Agnieszka on 2015-06-06.
 */
public class Tree {

    public List<TreeElem> tree;
    private int iterator;
    public static int slowDownWorking = 5;

    public Tree(){

        tree = new ArrayList<TreeElem>();
        iterator = 0;

        RootIfVIP root = new RootIfVIP();
        tree.add(root); //0
        tree.add(new IfMoreThanOneVIP()); //1
        tree.add(new IfThereAreLongWaitedOrders()); //2
        tree.add(new IfDoMoreThan3VIPOrder()); //3
        tree.add(new DoVIPOrder()); //4
        tree.add(new DoLongWaitedOrder()); //5
        tree.add(new IfThereAreLongDoingOrders()); //6
        tree.add(new IfIDoMoreThan3LongDoingOrders()); //7
        tree.add(new DoQuickDoingOrder()); //8
        tree.add(new DoLongDoingOrder()); //9


        while(true){
            try {
                //System.out.println("Aktualny nr to: " + iterator);
                tree.get(iterator).visitElem(this);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    }
    public void setIterator(int i){
        iterator = i;
    }
    public void incrementIterator(){
        iterator ++;
    }
    public int getIterator(){
        return iterator;
    }

    //------------------------------------------------------------------------------------------------------------------
    private class RootIfVIP extends TreeElem implements TreeFunctions{

        public RootIfVIP(){
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public boolean question() throws IOException {
            //czy mam zamowienie VIP
            OrdersService ordersService = OrdersService.getInstance();
            List<Order> orders = ordersService.getOrders();
            //System.out.println("RootIfVIP");

            for (int i = 0; i < orders.size(); i++){
                if (orders.get(i).isVIP()) return true;
            }

            return  false;
        }

        public void yes(Tree tree){
            tree.setIterator(1);
        }
        public void no(Tree tree){
            tree.setIterator(2);
        }

    }
    //------------------------------------------------------------------------------------------------------------------

    private class IfMoreThanOneVIP extends TreeElem{

        public boolean question() throws IOException {
            OrdersService ordersService = OrdersService.getInstance();
            List<Order> orders = ordersService.getOrders();
            //System.out.println("If more than one VIP");
            int VIPcounter = 0;
            for (int i = 0; i < orders.size(); i++){
                if (orders.get(i).isVIP()) VIPcounter ++;
                if (VIPcounter >= 2) return true;
            }

            return  false;
        }

        public void yes(Tree tree) {
            tree.setIterator(3);
        }

        public void no(Tree tree) {
            tree.setIterator(4);
        }
    }
    //------------------------------------------------------------------------------------------------------------------

    class IfDoMoreThan3VIPOrder extends TreeElem{

        public boolean question() throws IOException {
            OrdersService ordersService = OrdersService.getInstance();
            //List<Order> doing = ordersService.getCurrentCreatingMeals();
            //System.out.println("If do more than 3 vip orders");
            int vipCounter = 0;
            synchronized (OrdersService.getInstance().getCurrentCreatingMeals()){
                for (Order o : ordersService.getCurrentCreatingMeals()){
                    if (o.isVIP()) {
                        vipCounter++;
                        if (vipCounter > 3) return true;
                    }
                }
            }

            return false;
        }

        public void yes(Tree tree) {
            tree.setIterator(2);
        }

        public void no(Tree tree) {
            tree.setIterator(4);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    private class DoVIPOrder extends TreeElem{

        public boolean question() throws IOException {
            OrdersService ordersService = OrdersService.getInstance();
            //List<Order> orders = OrdersService.getInstance().getOrders();
            //System.out.print("Do vip order");

            synchronized (OrdersService.getInstance().getOrders()){
                for (Order o : OrdersService.getInstance().getOrders()) {
                    if (o.isVIP()) {
                        Order doingOrder = o;
                        int timeToFinish = doingOrder.getMeal().getTime();
                        ordersService.addCurrentCreatingMeal(doingOrder);
                        //orders.remove(doingOrder);
                        ordersService.removeOrder(doingOrder);
                        try {
                            TimeUnit.SECONDS.sleep(timeToFinish+Tree.slowDownWorking);

                            //System.out.println("After waiting for doing order");
                            ordersService.addReadyMeals(doingOrder);
                            //System.out.println("After adding ready meals");
                            ordersService.removeCurrentCreatingMeal(doingOrder);
                            //System.out.println("After removing current creating meal");
                            return true;

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return false;
            }

        }

        public void yes(Tree tree) {
            tree.setIterator(0);
        }

        public void no(Tree tree) {
            tree.setIterator(0);
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    private class IfThereAreLongWaitedOrders extends TreeElem {

        public boolean question() throws IOException {
            OrdersService ordersService = OrdersService.getInstance();
            List<Order> orders = ordersService.getOrders();
            //System.out.println("If there are long waited orders");

            for (int i = 0; i < orders.size(); i++){
                if (orders.get(i).isWaitedLong()) return true;
            }
            return false;
        }

        public void yes(Tree tree) {
            tree.setIterator(5);
        }

        public void no(Tree tree) {
            tree.setIterator(6);
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    private class DoLongWaitedOrder extends TreeElem{

        public boolean question() throws IOException {
            OrdersService ordersService = OrdersService.getInstance();
            //List<Order> orders = OrdersService.getInstance().getOrders();
            //System.out.print("Do long waited order");
            synchronized (OrdersService.getInstance().getOrders()){
                for (Order o: OrdersService.getInstance().getOrders()) {
                    if (o.isWaitedLong()) {
                        Order doingOrder = o;
                        int timeToFinish = doingOrder.getMeal().getTime();
                        ordersService.addCurrentCreatingMeal(doingOrder);
                        //orders.remove(doingOrder);
                        ordersService.removeOrder(doingOrder);
                        try {
                            TimeUnit.SECONDS.sleep(timeToFinish+Tree.slowDownWorking);

                            //System.out.println("After waiting for doing order");
                            ordersService.addReadyMeals(doingOrder);
                            //System.out.println("After adding ready meals");
                            ordersService.removeCurrentCreatingMeal(doingOrder);
                            //System.out.println("After removing current creating meal");
                            return true;

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return false;
            }

        }

        public void yes(Tree tree) {
            tree.setIterator(0);
        }

        public void no(Tree tree) {
            tree.setIterator(0);
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    private class IfThereAreLongDoingOrders extends TreeElem{

        public boolean question() throws IOException {
            OrdersService ordersService = OrdersService.getInstance();
            List<Order> orders = ordersService.getOrders();
            //System.out.println("If there are long waited orders");

            for (int i = 0; i < orders.size(); i++){
                if (orders.get(i).isLongPreparedTime()) return true;
            }
            return false;
        }

        public void yes(Tree tree) {
            tree.setIterator(7);
        }

        public void no(Tree tree) {
            tree.setIterator(8);
        }

    }
    //------------------------------------------------------------------------------------------------------------------
    private class IfIDoMoreThan3LongDoingOrders extends TreeElem {

        public boolean question() throws IOException {
            OrdersService ordersService = OrdersService.getInstance();
            List<Order> doing = ordersService.getCurrentCreatingMeals();
            //System.out.println("If do more than 3 long doing orders");
            int longPreparedCounter = 0;
            for (Order o : doing){
                if (o.isVIP()) {
                    longPreparedCounter++;
                    if (longPreparedCounter > 3) return true;
                }
            }
            return false;
        }

        public void yes(Tree tree) {
            tree.setIterator(8);
        }

        public void no(Tree tree) {
            tree.setIterator(9);
        }

    }
    //------------------------------------------------------------------------------------------------------------------
    private class DoQuickDoingOrder extends TreeElem{

        public boolean question() throws IOException {
            OrdersService ordersService = OrdersService.getInstance();
            //List<Order> orders = OrdersService.getInstance().getOrders();
            //System.out.print("Do quick doing order");
            synchronized (OrdersService.getInstance().getOrders()){
                for (Order o : OrdersService.getInstance().getOrders()) {
                    if (!o.isLongPreparedTime()) {
                        Order doingOrder = o;
                        int timeToFinish = doingOrder.getMeal().getTime();
                        ordersService.addCurrentCreatingMeal(doingOrder);
                        //orders.remove(doingOrder);
                        ordersService.removeOrder(doingOrder);
                        try {
                            TimeUnit.SECONDS.sleep(timeToFinish+Tree.slowDownWorking);

                            //System.out.println("After waiting for doing order");
                            ordersService.addReadyMeals(doingOrder);
                            //System.out.println("After adding ready meals");
                            ordersService.removeCurrentCreatingMeal(doingOrder);
                            //System.out.println("After removing current creating meal");
                            return true;

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            return false;
        }

        public void yes(Tree tree) {
            tree.setIterator(0);
        }

        public void no(Tree tree) {
            tree.setIterator(0);
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    private class DoLongDoingOrder extends TreeElem{

        public boolean question() throws IOException {
            OrdersService ordersService = OrdersService.getInstance();
            //List<Order> orders = OrdersService.getInstance().getOrders();
            System.out.print("Do quick doing order");

            synchronized (OrdersService.getInstance().getOrders()){
                for (Order o : OrdersService.getInstance().getOrders()) {
                    if (o.isLongPreparedTime()) {
                        Order doingOrder = o;
                        int timeToFinish = doingOrder.getMeal().getTime();
                        ordersService.addCurrentCreatingMeal(doingOrder);
                        //orders.remove(doingOrder);
                        ordersService.removeOrder(doingOrder);
                        try {
                            TimeUnit.SECONDS.sleep(timeToFinish+Tree.slowDownWorking);

                            //System.out.println("After waiting for doing order");
                            ordersService.addReadyMeals(doingOrder);
                            //System.out.println("After adding ready meals");
                            ordersService.removeCurrentCreatingMeal(doingOrder);
                            //System.out.println("After removing current creating meal");
                            return true;

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            return false;
        }

        public void yes(Tree tree) {
            tree.setIterator(0);
        }

        public void no(Tree tree) {
            tree.setIterator(0);
        }
    }

}
