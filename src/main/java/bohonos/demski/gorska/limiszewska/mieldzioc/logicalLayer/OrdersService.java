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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

/**
 *Klasa obs�uguj�ca zam�wienia klient�w w restauracji 
 * @author Delirus
 */
public class OrdersService {
    private final List<Meal> menu = new ArrayList<Meal>(); // menu naszej restauracji
    private final List<Entry<Integer, Meal>> orders = new ArrayList<Entry<Integer, Meal>>(); //lista zam�wie�, kluczem jest numer stolika - Integer, a warto�ci� zam�wienie
    public static final String FILE_PATH = "menu.txt";
    public OrdersService() throws IOException{
        initList();
    }
    
    /**
     * Zwraca nasze menu.
     * @return menu 
     */
    public List<Meal> getMenu(){
        return menu;
    }
    
    /**
     * Metoda zwraca obecn� list� zam�wie�.
     * @return zwraca liste zam�wie�
     */
    public List<Entry<Integer, Meal>> getOrders(){
        return orders;
    }
    
    
    /**
     * Inicjalizuje odczyt da� z pliku tekstowego do menu.
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
     * Dodaje posi�ek do menu naszej wy�mienitej restauracji.
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
     * Metoda sk�ada zam�wienia, jest wykonywana dla pojedynczej osoby. Losuje liczb� zam�wie� pojedynczej osoby, a nast�pnie losuje r�ne dania dla tej osoby. 
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
            orders.add(new SimpleEntry<Integer, Meal>(table, menu.get(a)));
            //System.out.println("Doda�em potraw� nr: "+a);
        }
    }

    /**
     * Metoda usuwa zam�wienie o indeksie index z listy zam�wie�, tzn., �e zam�wienie o numerze index zosta�o zrealizowane.
     * @param index 
     */
    public void removeOrder(int index){
        orders.remove(index);
    }
    
    /**
     * Metoda zwraca tablic� dwuwymiarow�, kt�ra zawiera informacje o numerze zam�wienia i zawarto�ci zam�wienia
     * @return orderList;
     */
    public String[][] getOrdersList(){
        String[][] orderList = new String [orders.size()][2];
        for (int i=0; i<orders.size();i++){
            orderList[i][0] = Integer.toString(i+1);
            orderList[i][1] = orders.get(i).getValue().getName() + orders.get(i).getValue().getIngredients() ;
        }
        return orderList;
    }

    /**
     *  Metoda zwraca tablic� dwuwymiarow�, kt�ra zawiera informacje o pozycji posi�ku w menu i zawarto�ci tej pozycji
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
}
