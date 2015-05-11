package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;

/**
 * Created by Marta Górska.
 */

import java.io.IOException;
import java.util.ArrayList;

/** Klasa odpowiedzialna za zarządzenie zamówieniami na kuchni oraz
 * losowe generowanie zamówień. Generowanie zamówień jest randomowym wywoływaniem metody
 * getOrder
 *
 */
public class Kitchen {

    protected ArrayList<Meal> ordered;
    protected ArrayList<Meal> doing;
    protected ArrayList<Meal> done;

    //settery
    public void setOrdered(ArrayList<Meal> meals) { this.ordered =meals; }

    public void setDoing(ArrayList<Meal> meals) { this.doing =meals; }

    public void setDone(ArrayList<Meal> meals) { this.done =meals; }

    //dodawanie posiłka do listy zamówionych
    public void addOrdered(Meal meal){
        this.ordered.add(meal);
    }

    //dodawanie posiłka do listy robionych
    public void addDoing(Meal meal) {
        this.doing.add(meal);
    }

    //dodawanie posiłka do listy zrobionych oczekujących na dostarczenie do stolika
    public void addDone(Meal meal) {
        this.done.add(meal);
    }


    //zwróć całą listę obecnie zamówionych
    public ArrayList<Meal> getOrdered() {
        return this.ordered;
    }

    //zwróć całą listę obecnie robionych w kuchni
    public ArrayList<Meal> getDoing() {
        return this.doing;
    }

    //zwróc całą listę oczekujących na dostarczenie
    public ArrayList<Meal> getDone() {
        return this.done;
    }

    //usun zamowienie z listy zamówionych
    public void removeOrdered(Meal meal) {
        this.ordered.remove(meal);
    }

    //usun zamowienie z listy robionych w kuchni
    public void removeDoing(Meal meal) {
        this.doing.remove(meal);
    }

    //usun zamowienie z listy zrobionych
    public void removeDone(Meal meal) {
        this.done.remove(meal);
    }

    //na każdym obiekcie Table z TableCollection zrób randOrder() z klasy Table
    //w mainie trzeba będzie wywoływać tę funkcję co jakiś czas np. co trzy sekundy
    public void doOrder(TableCollection tables) {
       for (Integer i=0; i<20; i++)
        {
           Coordinates a = new Coordinates(2,3); //a to nie będzie wgl potrzebne bo koordynaty będą elementem pobieranego z mapy stolika

           Table m = new Table(1, a); // tu ogólnie nie będzie tworzenie nowego stolika tylko pobieranie go z mapy
           try {
               m.randOrder();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }



    }

}
