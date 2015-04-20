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
}
