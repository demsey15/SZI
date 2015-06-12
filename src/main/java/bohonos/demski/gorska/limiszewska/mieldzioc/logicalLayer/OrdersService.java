/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;

/**
 *
 * @author Delirus
 */
import ViewLayer.MainFrame;
import ViewLayer.OrdersPanel;
import com.rits.cloning.Cloner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 *Klasa obs³uguj¹ca zamówienia klientów w restauracji
 * @author Delirus
 */
public class OrdersService {
    private final List<Meal> menu = new ArrayList<Meal>(); // menu naszej restauracji



    public static final String FILE_PATH = "menu.txt";
    public static OrdersService instance;

    private final List<Order> orders = Collections.synchronizedList(new ArrayList<Order>()); //lista zamówieñ - zrobione przez Dominika
    private final List<Order> readyMeals = Collections.synchronizedList(new ArrayList<Order>()); // zrobione posi³ki przez kuchniê - zrobione przez Dominika
    private final List<Order> currentCreatingMeals = Collections.synchronizedList(new ArrayList<Order>()); // aktualnie przygotowywane posi³ki
    private final List<Order> tray = Collections.synchronizedList(new ArrayList<Order>()); //taca z posi³kami kelnera - zrobione przez Dominika

    private OrdersService() throws IOException{
        initList();
        instance = this;
    }

    public static OrdersService getInstance() throws IOException {
        if (instance == null){
            return new OrdersService();
        }else{
            return instance;
        }
    }

    /**
     * Zwraca nasze menu.
     * @return menu
     */
    public List<Meal> getMenu(){
        return menu;
    }

    /**
     * Dodaje posi³ek do menu naszej wyœmienitej restauracji.
     * @param line
     */
    private void addMeal(String line){
        String text[] = null;
        text = line.split(";");
        Meal meal;
        int w = Integer.parseInt(text[2]);
        int x = Integer.parseInt(text[3]);
        int y = Integer.parseInt(text[4]);
        int z = Integer.parseInt(text[5]);
        meal = new Meal(text[0],text[1],w,x,y,z);
        menu.add(meal);
    }

    /**
     *  Metoda zwraca tablicê dwuwymiarow¹, która zawiera informacje o pozycji posi³ku w menu i zawartoœci tej pozycji
     * @return menuList;
     */
    public String[][] getMenuList(){
        String[][] menuList = new String [menu.size()][2];
        for (int i=0; i<menu.size();i++){
            menuList[i][0] = Integer.toString(i+1);
            menuList[i][1] = menu.get(i).getName() +" "+ menu.get(i).getIngredients() ;
        }
        return menuList;
    }


    //------------------------------------------------------------------------------------------------------------------
    /*Dopisane na potrzeby wyswietlania menu i orderow w widoku */
    /* AL 2015-06-06 */
    public String[][] getMenuToDisplay(){
        String[][] menuList = new String [menu.size()][2];
        for (int i=0; i<menu.size();i++){
            menuList[i][1] = Integer.toString(i+1);
            menuList[i][0] = menu.get(i).getName() +" "+ menu.get(i).getIngredients();
        }
        return menuList;
    }


    /**
     * @author Dominik Demski
     * @return Zwraca kopiê listy zamówieñ.
     */
    public List<Order> getOrders(){
        synchronized (orders){
            return (new Cloner()).deepClone(orders);
        }
    }

    /**
     * @author Dominik Demski
     * Dodaje zamówienie do listy zamówieñ.
     * @param order
     */
    public void addOrder(Order order){
        synchronized (orders){
            orders.add(order);
        }
        OrdersPanel orders = MainFrame.getInstance().getNewOrdersPanel();
        orders.setOrdersList(this.getOrdersToDisplay());
    }
    public void addCurrentCreatingMeal(Order order) {
        synchronized (currentCreatingMeals){
            currentCreatingMeals.add(order);
        }
    }
    public void removeCurrentCreatingMeal(Order order) {synchronized (currentCreatingMeals){
        currentCreatingMeals.remove(order);
    }
    }
    public List<Order> getCurrentCreatingMeals(){
        synchronized (currentCreatingMeals){
            return (new Cloner()).deepClone(currentCreatingMeals);
        }
    }

    /**
     * Metoda sk³ada zamówienia, jest wykonywana dla pojedynczej osoby. Losuje liczbê zamówieñ pojedynczej osoby, a nastêpnie losuje ró¿ne dania dla tej osoby.
     * @param table
     */
    public void makeOrder(int table){
        Random r = new Random();
        int number = r.nextInt(4)+1;
        List<Integer> num = new ArrayList<Integer>();
        int a;
        for(int i =0 ; i<number; i++){
            do{
                //Random r = new Random();
                a = r.nextInt(menu.size());
            }while(num.contains(a));
            num.add(a);
            //orders.add(new SimpleEntry<Integer, Meal>(table, menu.get(a)));

            //System.out.println("Doda³em potrawê nr: "+a);

            //----------------------------------------------------------------------------------------------------------
            /*dopisane na potrzeby wyswietlania listy zamowien
             * AL 2015-06-06 */
            Random tableRandom = new Random();
            Control control = Control.getInstance();
            int maxTables = control.getAllTablesCoordinates().size();
            table = tableRandom.nextInt(maxTables)+1;
            boolean vip;
            if (table%2 == 0 && table > 15 ) {
                vip = true;
            }else {
                vip = false;
            }
            //vip = true;

            Calendar cal = Calendar.getInstance();
            long currentTime = cal.getTimeInMillis();

            Order order = new Order(menu.get(a), table, currentTime, vip);
            addOrder(order);

            OrdersPanel orders = MainFrame.getInstance().getNewOrdersPanel();
            orders.setOrdersList(this.getOrdersToDisplay());
            //----------------------------------------------------------------------------------------------------------
        }
    }

    /**
     * Metoda usuwa zamówienie o indeksie index z listy zamówieñ, tzn., ¿e zamówienie o numerze index zosta³o zrealizowane.
     * @param index
     */
    public void removeOrder(int index){
        synchronized (orders) {
            orders.remove(index);
            OrdersPanel orders = MainFrame.getInstance().getNewOrdersPanel();
            orders.setOrdersList(this.getOrdersToDisplay());
        }
    }

    public void removeOrder(Order order){
        synchronized (orders) {
            orders.remove(order);
            OrdersPanel orders = MainFrame.getInstance().getNewOrdersPanel();
            orders.setOrdersList(this.getOrdersToDisplay());
        }
    }



    public String[][] getOrdersToDisplay(){
        synchronized (orders) {
            if(!orders.isEmpty()) {
                String[][] orderList = new String[orders.size()][2];
                synchronized (orders){
                    for (int i = 0; i < orders.size(); i++) {
                        orderList[i][1] = String.valueOf(orders.get(i).tableNumber);
                        orderList[i][0] = orders.get(i).meal.getName() + " " + orders.get(i).meal.getIngredients();
                    }
                }

                return orderList;
            }
            return new String[][]{{"", ""}};

        }
    }

    /**
     * Metoda dodaje zamówienie do listy przygotwanych posi³ków, które czekaj¹ na dostarczenie.
     * @param food
     */
    public void addReadyMeals(Order food){

        synchronized (readyMeals){
            readyMeals.add(food);
            OrdersPanel readyMealsPanel = MainFrame.getInstance().getReadyMealPanel();
            readyMealsPanel.setOrdersList(getReadyMealsToDisplay());
        }

    }

    public List<Order> getReadyMeals(){
        synchronized (readyMeals){
            return (new Cloner()).deepClone(readyMeals);
        }
    }

    public List<Order> getTray(){
            return (new Cloner()).deepClone(tray);
    }

    /**
     * Metoda usuwa wybrany posi³ek z listy przygotwanych posi³ków, po tym jak kelner umieœci³ go na tacy.
     * @param food
     */
    public void removeReadyMeals(Order food){
        readyMeals.remove(food);
        MainFrame.getInstance().getReadyMealPanel().setOrdersList(getReadyMealsToDisplay());
    }

    public String[][] getReadyMealsToDisplay() {

        synchronized (readyMeals) {
            if (!readyMeals.isEmpty()){
                String[][] readyMealsList = new String[readyMeals.size()][2];
                for (int i = 0; i < readyMeals.size(); i++) {
                    readyMealsList[i][1] = String.valueOf(readyMeals.get(i).tableNumber);
                    readyMealsList[i][0] = readyMeals.get(i).meal.getName() + " " + readyMeals.get(i).meal.getIngredients();
                }
                return readyMealsList;
            }

            return new String[][]{{"",""}};
        }
    }


    /**
     * Metoda dodaje zamówienie do listy, która zawiera obecn¹ zawartoœæ posi³ków kelnera.
     * @param food
     */
    public void addMealToTray(Order food){
        tray.add(food);
        MainFrame.getInstance().getHandedOnPlatePanel().setOrdersList(getTrayMealsToDisplay());
    }

    /**
     * @author Dominik Demski
     * Metoda usuwa wszystkie zamówienia z listy zamówieñ znajduj¹cych siê na tacy.
     * Wywo³ywana po dostarczeniu odpowiedniego posi³ku do odpowiedniego stolika.
     * @param tableNumber numer stolika, dla którego potrawy z tacy maj¹ zostaæ usuniête.
     */
    public synchronized void removeMealForTableFromTray(int tableNumber){
            synchronized (tray) {
               /* for (Order order : tray) {
                    if (order.tableNumber == tableNumber) {
                        tray.remove(order);
                    }
                }
                */
                for(int i = 0; i < tray.size(); i++)
                {
                    Order order = tray.get(i);
                    synchronized (order){
                        if(order.tableNumber == tableNumber){
                            tray.remove(i);
                        }
                    }
                }
                MainFrame.getInstance().getHandedOnPlatePanel().setOrdersList(getTrayMealsToDisplay());
            }
        //System.out.print("Wywolalem");
    }

    public String[][] getTrayMealsToDisplay() {
        synchronized (tray) {
            if(!tray.isEmpty()){
                String[][] trayList = new String[tray.size()][2];
                for (int i = 0; i < tray.size(); i++) {
                    trayList[i][1] = String.valueOf(tray.get(i).tableNumber);
                    trayList[i][0] = tray.get(i).meal.getName() + " " + tray.get(i).meal.getIngredients();
                    System.out.print(trayList[i][0]);
                }
                return trayList;
            }

            return new String[][]{{"",""}};
        }
    }


    /**
     * Inicjalizuje odczyt dañ z pliku tekstowego do menu.
     * @throws IOException
     */
    private void initList() throws IOException{
        FileReader fileReader = new FileReader(FILE_PATH);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        try {
            String textLine = bufferedReader.readLine();
            do {
                //System.out.println(textLine);
                addMeal(textLine);
                textLine = bufferedReader.readLine();
            } while(textLine != null);
        }
        finally{
            bufferedReader.close();
        }
    }






    /**
     * Metoda zwraca tablicê dwuwymiarow¹, która zawiera informacje o sk³adzie zamówienia oraz o numerze stolika, do którego ma byæ dostarczone to zamówienie
     * @return orderList;
     */
    /*public String[][] getOrdersList(){
        String[][] orderList = new String [orders.size()][2];
        for (int i=0; i<orders.size();i++){

            orderList[i][0] = Integer.toString(i+1);
            orderList[i][1] = orders.get(i).getValue().meal.getName() + orders.get(i).getValue().meal.getIngredients() ;

            orderList[i][1] = Integer.toString(orders.get(i).getKey());
            orderList[i][0] = orders.get(i).getValue().getName() + orders.get(i).getValue().getIngredients() ;

        }
        return orderList;
    }*/




    //------------------------------------------------------------------------------------------------------------------





}
