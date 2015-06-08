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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

/**
 *Klasa obs³uguj¹ca zamówienia klientów w restauracji
 * @author Delirus
 */
public class OrdersService {
    private final List<Meal> menu = new ArrayList<Meal>(); // menu naszej restauracji
    //private final List<Entry<Integer, Meal>> orders = new ArrayList<Entry<Integer, Meal>>(); //lista zamówieñ, kluczem jest numer stolika - Integer, a wartoœci¹ zamówienie
    private final List<Entry<Integer, Order>> orders = new ArrayList<Entry<Integer, Order>>(); //lista zamówieñ, kluczem jest numer stolika - Integer, a wartoœci¹ zamówienie

    //private final List<Entry<Integer, Meal>> orders = new ArrayList<Entry<Integer, Meal>>(); //lista zamówieñ, kluczem jest numer stolika - Integer, a wartoœci¹ zamówienie
    private final List<Entry<Integer, Meal>> tray = new ArrayList<Entry<Integer, Meal>>(); //taca z posi³kami kelnera
    private final List<Entry<Integer, Meal>> readyMeals = new ArrayList<Entry<Integer, Meal>>(); // zrobione posi³ki przez kuchniê
    public static final String FILE_PATH = "menu.txt";
    public static OrdersService instance;
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
     * Metoda zwraca obecn¹ listê zamówieñ.
     * @return zwraca liste zamówieñ
     */
    /*public List<Entry<Integer, Meal>> getOrders(){
        return orders;
    }*/
    public List<Entry<Integer, Order>> getOrders(){
        return orders;
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
            table = tableRandom.nextInt(maxTables-1)+1;
            boolean vip;
            /*if (table%2 == 0 && table > 15 ) {
                vip = true;
            }else {
                vip = false;
            }*/
            vip = true;

            Calendar cal = Calendar.getInstance();
            long currentTime = cal.getTimeInMillis();

            Order order = new Order(menu.get(a), table, currentTime, vip);
            orders.add(new AbstractMap.SimpleEntry<Integer, Order>(table, order));

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
        orders.remove(index);
        OrdersPanel orders = MainFrame.getInstance().getNewOrdersPanel();
        orders.setOrdersList(this.getOrdersToDisplay());
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

    public String[][] getOrdersToDisplay(){
        String[][] orderList = new String [orders.size()][2];
        System.out.print("Orders list: ");
        for (int i=0; i<orders.size();i++){
            orderList[i][1] = Integer.toString(i+1);
            orderList[i][0] = orders.get(i).getValue().meal.getName() +" "+ orders.get(i).getValue().meal.getIngredients() ;
            System.out.print(orderList[i][0]);
        }

        return orderList;
    }
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Metoda dodaje posi³ek do listy, która zawiera obecn¹ zawartoœæ posi³ków kelnera.
     * @param food
     */
    public void addMealToTray(Entry<Integer, Meal> food){
        tray.add(food);
    }

    /**
     * Metoda usuwa i-ty posi³ek z listy posi³ków znajduj¹cych siê na tacy. Wywo³ywana po dostarczeniu odpowiedniego posi³ku do odpowiedniego stolika.
     * @param i
     */
    public void removeMealFromtray(int i){
        tray.remove(i);
    }

    /**
     * Metoda dodaje posi³ek do listy przygotwanych posi³ków, które czekaj¹ na dostarczenie.
     * @param food
     */
    public void addReadyMeals(Entry<Integer, Meal> food){
        readyMeals.add(food);
    }

    /**
     * Metoda usuwa wybrany posi³ek z listy przygotwanych posi³ków, po tym jak kelner umieœci³ go na tacy.
     * @param food
     */
    public void removeReadyMeals(Entry<Integer, Meal> food){
        readyMeals.remove(food);
    }

}
