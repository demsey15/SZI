package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Marta Górska.
 */
public class Table {

    //numer stolika
    protected Integer table_number;
    //Koordynaty
    protected Coordinates coords;
    //lista krzeseł
    protected SeatCollection seat_collection;
    //lista miejsc do ktorych moze podejsc kelner
    protected ArrayList<Coordinates> waiter_place;
    //status stolika (do wyświetlania koloru stolika) (dopuszczalne wartości normal, order, waiting)
    protected String state;

    //konstruktor
    public Table(Integer table_nr, Coordinates coords /*SeatCollection seat_collection*/) {
        this.table_number = table_nr;
        this.coords = coords;
        //this.seat_collection = seat_collection;
        this.waiter_place = new ArrayList<Coordinates>();
        this.state = "normal";
    }

    //metoda ustawiajaca numer stolika
    public void setTableNumber(Integer number) {
        this.table_number = number;
    };

    //metoda ustawiająca koordynaty stolika
    public void setCoords(Coordinates coords) {
        this.coords = coords;
    }

    //metoda ustawiająca liste krzesel dla stolika
    public void setSeatCollection( SeatCollection seat_collection) {
        this.seat_collection = seat_collection;
    };

    //metoda ustawiająca stan stolika
    public void setState (String a) {
        this.state = a;
    }

    //metoda zwracająca numer stolika
    public Integer getTableNumber() {
        return this.table_number;
    };

    //metoda zwracająca koordynaty stolika
    public Coordinates getCoords() {
        return this.coords;
    }

    //metoda zwracajaca liste krzesel wokol stolika
    public SeatCollection getSeatCollection() {
        return this.seat_collection;
    };

    //metoda zwracajaca liste miejsc do ktorych moze podejsc kelner
    public ArrayList<Coordinates> getWaiterPlace() {
        if ( this.waiter_place.size() == 0 ) {
            this.prepareWaiterPlace();
        }

        return this.waiter_place;
    }

    //przygotowanie listy współrzędnych czterech narożników do których może podejść kelner
    private ArrayList<Coordinates> prepareWaiterPlace() {
        Integer x = this.coords.getColumn();
        Integer y = this.coords.getRow();
        Coordinates a = new Coordinates(x-1, y-1);
        Coordinates b = new Coordinates(x-1, y+1);
        Coordinates c = new Coordinates(x+1, y-1);
        Coordinates d = new Coordinates(x+1, y+1);
        ArrayList<Coordinates> list = new ArrayList<Coordinates>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        return list;
    }

    //metoda zwracająca stan stolika
    public String getState() {
        return this.state;
    }

    //generuje losowe zamówienie zamówienie dla danego stolika używając metody makeOrder na obiekcie OrdersService
    public void randOrder() throws IOException {
        Integer m =100;
        Random generator = new Random();
        m = generator.nextInt(100);

        if (m<80) {
            this.state = "order";
            //for(Integer i=0, i<5, i++){
            //this.seat_collection.get(i).setChairAsRed } //nie zadziała dopóki stoliki na mapie też nie będą obiektami
            // OrdersService order = new OrdersService(); // nie mogę stworzyć Ordersa bo wywala błąd z wyjątkami
            //order.makeOrder(this.table_number); // korzystanie z gotowej funkcji z klasy OrdersService
            this.state = "waiting";
            //dodaj order do listy order, która ma być wyświetlana
            System.out.println("Złożono zamówienie");
            //for(Integer i=0, i<5, i++){
            //this.seat_collection.get(i).setChairAsGreen }
        } else {
            System.out.println("Nie chce nic zamówić");
        };


    }

}
