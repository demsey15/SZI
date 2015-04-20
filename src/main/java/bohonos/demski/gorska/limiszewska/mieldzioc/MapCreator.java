/**
 * 
 */
package bohonos.demski.gorska.limiszewska.mieldzioc;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Dominik Demski
 *
 */
public class MapCreator {
	
	List<List<Integer>> map = new ArrayList<List<Integer>>(Map.MAP_HEIGHT); //numer wiersza <numery kolumn>
	private final int TABLE = 1;
	private final int NOTHING = 0;
	
	/**
	 * Wczytuje mapê z pliku dla zadanej œcie¿ki do pliku.
	 * @param path œcie¿ka do pliku
	 * @throws IOException wyj¹tek zostaje wyrzucony, jeœli nie ma pliku o podanej œcie¿ce.
	 */
	public void loadMapFromFile(Path path) throws IOException{
		Scanner in = new Scanner(path);
		
		for(int i = 0; i < Map.MAP_HEIGHT; i++){
			List<Integer> columns = new ArrayList<Integer>(Map.MAP_WIDTH);
			if(! in.hasNextLine()){ 
				in.close();
				throw new IllegalArgumentException("Za ma³o wierszy, mapa powinna mieæ"
					+ "wysokoœæ = " + Map.MAP_HEIGHT);
			}
			for(int j = 0; j < Map.MAP_WIDTH; j++){
				if(! in.hasNextInt()){
					in.close();
					throw new IllegalArgumentException("Za ma³o kolumn w wierszu "
							+ (i + 1) + " powinno byæ " + Map.MAP_WIDTH + " kolumn!");
				}
				int number = in.nextInt();
				if(number != TABLE && number!= NOTHING){
					in.close();
					throw new IllegalArgumentException("Plik z map¹ ma z³y format powinien zawieraæ"
							+ "tylko " + TABLE + " i " + NOTHING + "(b³¹d w wierszu: " + (i + 1) +
							" kolumnie: " + (j + 1));
				}
				else columns.add(number);
			}
		}
		in.close();
	}
	
	public void createMapWithChairs(){
		
	}

}
