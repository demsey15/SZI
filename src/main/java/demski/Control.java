package demski;

import java.io.IOException;
import java.nio.file.Paths;

public class Control {

	public static final String FILE_PATH = "resources//map.txt";
	
	private Map map;
	
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
		return map.getObjectId(coordinates);
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
	 * nie ma krzes³a, albo nie stworzono wczeœniej mapy (za pomoc¹ metody prepareMap(),  w przeciwnym przypadku true.
	 */
	public boolean setChairAsSimple(Coordinates coordinates){
		if(map == null) return false;
		return map.setChairAs(coordinates, Map.CHAIR);
	}
}