package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * Kontroler s�u��cy do komunikacji GUI z warstw� logiczn� aplikacji.
 * Jest to singleton, kontroler ten nale�y tworzy� pos�uguj�c si� metod�
 * getInstance(), komunikacja z warstw� logiczn� powinna si� odbywa�
 * TYLKO za pomoc� kontrolera.
 * @author Dominik Demski
 *
 */

public class Control {

	public static final String FILE_PATH = "resources//map.txt";
	
	private Map map;
	private Monitor monitor = Monitor.getInstance();
	private Waiter waiter = Waiter.getInstance();
	
	private static Control instance;
	
	private Control(){}
	
	public static Control getInstance(){
		return (instance == null)? (instance = new Control()) : instance;
	}
	
	/**
	 * Przygotowuje map� (wype�nia j� sto�ami i krzes�ami na podstawie pliku o �cie�ce
	 * ze sta�ej Control.FILE_PATH).
	 * @return false, je�li mapa ma z�y format, inaczej true
	 * @throws IOException wyj�tek wyrzucany, je�li nie ma pliku o podanej �cie�ce (podanej w Control.FILE_PATH)
	 */
	public boolean prepareMap() throws IOException{
		MapCreator creator = new MapCreator();
		creator.loadMapFromFile(Paths.get(FILE_PATH));
		if(!creator.createMapWithChairs()) return false;
		map = new Map(creator.getMap());
		return true;
	}
	
	/**
	 * Zwraca id obiektu znajduj�cego si� pod wskazanymi wsp�rz�dnymi na mapie.
	 * @param coordinates wsp�rzedne obiektu.
	 * @return zwraca id obiektu (patrz sta�e w klasie Map) lub -1, je�li podane wsp�rzedne wykraczaj�
	 * poza map�, lub -2, je�li nie stworzono wcze�niej mapy (za pomoc� metody prepareMap()).
	 */
	public int getObjectId(Coordinates coordinates){
		if(map == null) return -2;
		Object o =  map.getObjectId(coordinates);

		if(o == null) return -1;
		if(o instanceof Table){
			return Map.TABLE;
		}
		else if(o instanceof Seat){
			return  ((Seat) o).getState();
		}
		else return  Map.FREE_FIELD;
	}
	
	/**
	 * Zmienia stan krzes�a na czerwone.
	 * @param coordinates wsp�rz�dne krzes�a
	 * @return false, je�li podane wsp�rz�dne wykraczaj� poza map� albo pod podanymi wsp�rzednymi
	 * nie ma krzes�a, albo nie stworzono wcze�niej mapy (za pomoc� metody prepareMap(),  w przeciwnym przypadku true.
	 */
	public boolean setChairAsRed(Coordinates coordinates){
		if(map == null) return false;
		return map.setChairAs(coordinates, Map.RED_CHAIR);
	}
	
	/**
	 * Zmienia stan krzes�a na zielone.
	 * @param coordinates wsp�rz�dne krzes�a
	 * @return false, je�li podane wsp�rz�dne wykraczaj� poza map� albo pod podanymi wsp�rzednymi
	 * nie ma krzes�a, albo nie stworzono wcze�niej mapy (za pomoc� metody prepareMap(),  w przeciwnym przypadku true.
	 */
	public boolean setChairAsGreen(Coordinates coordinates){
		if(map == null) return false;
		return map.setChairAs(coordinates, Map.GREEN_CHAIR);
	}
	
	/**
	 * Zmienia stan krzes�a na zwyk�e.
	 * @param coordinates wsp�rz�dne krzes�a
	 * @return false, je�li podane wsp�rz�dne wykraczaj� poza map� albo pod podanymi wsp�rzednymi
	 * nie ma krzes�a, albo nie stworzono wcze�niej mapy (za pomoc� metody prepareMap()),  w przeciwnym przypadku true.
	 */
	public boolean setChairAsSimple(Coordinates coordinates){
		if(map == null) return false;
		return map.setChairAs(coordinates, Map.CHAIR);
	}
	
	/**
	 * Zwraca list� wsp�rzednych wszystkich sto��w na mapie.
	 * @return lista wsp�rzednych wszystkich sto��w lub null, je�li (i tylko wtedy) nie stworzono wczesniej mapy
	 * (za pomoc� metody prepareMap()).
	 */
	public List<Coordinates> getAllTablesCoordinates(){
		if(map == null) return null;
		return map.getAllTablesCoordinates();
	}

	public List<Coordinates> getAllCoordinates(){
		if(map == null) return null;
		return map.getAllCoordinates();
	}

	public List<List<Object>> getMap(){
		if(map == null) return null;
		return map.getMap();
	}
	
	
	/**
	 * Rejestruje s�uchacza zdarzenia przemieszczenia si� kelnera (musi on implementowa�
	 * interfejs OnMoveListener). S�uchacz ten zostanie poinformowany o przmieszczeniu si� kelnera,
	 * dostanie �cie�k� (list� koordynat�w), kt�r� ma przej�� kelner, za pomoc� metody onMove z interfejsu
	 * OnMoveListener.
	 * @param listener s�uchacz
	 */
	public void registerOnWaiterMoveListener(OnMoveListener listener){
		monitor.registerListener(listener);
	}
	
	/**
	 * 
	 * @return zwraca aktualne po�o�enie kelnera.
	 */
	public Coordinates getWaitersCurrentPosition() {
		return waiter.getCurrentPosition();
	}

	public static void main(String[] args){
		Control control = Control.getInstance();
		try {
			control.prepareMap();
			System.out.println(control.getObjectId(new Coordinates(2, 2)));
			System.out.println(control.getObjectId(new Coordinates(2, 3)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}