package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;

/**
 * Created by Marta on 2015-05-10.
 */
public class Seat {

    //Koordynaty
    protected Coordinates coords;

    public Seat ( Coordinates coords ) {
        this.coords = coords;
    }

    //ustawia koordynaty krzesla
    public void setCoords(Coordinates coords) {
        this.coords = coords;
    }

    //zwraca koordynaty krzesla
    public Coordinates getCoords() {
        return this.coords;
    }


}
