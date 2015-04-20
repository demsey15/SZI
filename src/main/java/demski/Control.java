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
}
