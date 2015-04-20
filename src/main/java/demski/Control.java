package demski;

import java.io.IOException;
import java.nio.file.Paths;

public class Control {

	public static final String FILE_PATH = "resources//map.txt";
	
	private Map map;
	
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
		return map.getObjectId(coordinates);
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
	 * nie ma krzes�a, albo nie stworzono wcze�niej mapy (za pomoc� metody prepareMap(),  w przeciwnym przypadku true.
	 */
	public boolean setChairAsSimple(Coordinates coordinates){
		if(map == null) return false;
		return map.setChairAs(coordinates, Map.CHAIR);
	}
}