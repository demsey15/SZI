package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * Kontroler s³u¿¹cy do komunikacji GUI z warstw¹ logiczn¹ aplikacji.
 * Jest to singleton, kontroler ten nale¿y tworzyæ pos³uguj¹c siê metod¹
 * getInstance(), komunikacja z warstw¹ logiczn¹ powinna siê odbywaæ
 * TYLKO za pomoc¹ kontrolera.
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
	 * Przygotowuje mapê (wype³nia j¹ sto³ami i krzes³ami na podstawie pliku o œcie¿ce
	 * ze sta³ej Control.FILE_PATH).
	 * @return false, jeœli mapa ma z³y format, inaczej true
	 * @throws IOException wyj¹tek wyrzucany, jeœli nie ma pliku o podanej œcie¿ce (podanej w Control.FILE_PATH)
	 */
	public boolean prepareMap() throws IOException{
		MapCreator creator = new MapCreator();
		creator.loadMapFromFile(Paths.get(FILE_PATH));
		if(!creator.createMapWithChairs()) return false;
		map = new Map(creator.getMap());
		return true;
	}
	
	/**
	 * Zwraca id obiektu znajduj¹cego siê pod wskazanymi wspó³rzêdnymi na mapie.
	 * @param coordinates wspó³rzedne obiektu.
	 * @return zwraca id obiektu (patrz sta³e w klasie Map) lub -1, jeœli podane wspó³rzedne wykraczaj¹
	 * poza mapê, lub -2, jeœli nie stworzono wczeœniej mapy (za pomoc¹ metody prepareMap()).
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
	 * Zmienia stan krzes³a na czerwone.
	 * @param coordinates wspó³rzêdne krzes³a
	 * @return false, jeœli podane wspó³rzêdne wykraczaj¹ poza mapê albo pod podanymi wspó³rzednymi
	 * nie ma krzes³a, albo nie stworzono wczeœniej mapy (za pomoc¹ metody prepareMap(),  w przeciwnym przypadku true.
	 */
	public boolean setChairAsRed(Coordinates coordinates){
		if(map == null) return false;
		return map.setChairAs(coordinates, Map.RED_CHAIR);
	}
	
	/**
	 * Zmienia stan krzes³a na zielone.
	 * @param coordinates wspó³rzêdne krzes³a
	 * @return false, jeœli podane wspó³rzêdne wykraczaj¹ poza mapê albo pod podanymi wspó³rzednymi
	 * nie ma krzes³a, albo nie stworzono wczeœniej mapy (za pomoc¹ metody prepareMap(),  w przeciwnym przypadku true.
	 */
	public boolean setChairAsGreen(Coordinates coordinates){
		if(map == null) return false;
		return map.setChairAs(coordinates, Map.GREEN_CHAIR);
	}
	
	/**
	 * Zmienia stan krzes³a na zwyk³e.
	 * @param coordinates wspó³rzêdne krzes³a
	 * @return false, jeœli podane wspó³rzêdne wykraczaj¹ poza mapê albo pod podanymi wspó³rzednymi
	 * nie ma krzes³a, albo nie stworzono wczeœniej mapy (za pomoc¹ metody prepareMap()),  w przeciwnym przypadku true.
	 */
	public boolean setChairAsSimple(Coordinates coordinates){
		if(map == null) return false;
		return map.setChairAs(coordinates, Map.CHAIR);
	}
	
	/**
	 * Zwraca listê wspó³rzednych wszystkich sto³ów na mapie.
	 * @return lista wspó³rzednych wszystkich sto³ów lub null, jeœli (i tylko wtedy) nie stworzono wczesniej mapy
	 * (za pomoc¹ metody prepareMap()).
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
	 * Rejestruje s³uchacza zdarzenia przemieszczenia siê kelnera (musi on implementowaæ
	 * interfejs OnMoveListener). S³uchacz ten zostanie poinformowany o przmieszczeniu siê kelnera,
	 * dostanie œcie¿kê (listê koordynatów), któr¹ ma przejœæ kelner, za pomoc¹ metody onMove z interfejsu
	 * OnMoveListener.
	 * @param listener s³uchacz
	 */
	public void registerOnWaiterMoveListener(OnMoveListener listener){
		monitor.registerListener(listener);
	}
	
	/**
	 * 
	 * @return zwraca aktualne po³o¿enie kelnera.
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