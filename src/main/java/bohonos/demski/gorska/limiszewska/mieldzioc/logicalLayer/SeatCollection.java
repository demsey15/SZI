package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;

import java.util.ArrayList;

/**
 * Created by Marta Górska.
 */
public class SeatCollection {

    private ArrayList<Seat> collection;

    public SeatCollection () {
        this.collection = new ArrayList<Seat>();
    }

    //dodanie nowego krzesla na koniec kolekcji
    public ArrayList<Seat> addSeat ( Seat seat ) {
        this.collection.add(seat);
        return this.collection;
    }

    public Seat getSeat ( Integer id ) {
        return this.collection.get(id);
    }

    //zwroc tablice krzesel
    public ArrayList<Seat> getAllSeat() {
        return this.collection;
    }

    //wyczysc tablice krzesel dla danego stolika
    public ArrayList<Seat> resetSeat() {
        this.collection = new ArrayList<Seat>();
        return this.collection;
    }

}
