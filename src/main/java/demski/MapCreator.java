/**
 * 
 */
package demski;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Dominik Demski
 * klasa wczytuj¹ca mapê z pliku i przekszta³acj¹ca j¹ na mapê z krzes³ami
 */
public class MapCreator {
	
	private List<List<Integer>> map; //numer wiersza <numery kolumn>
	private final int TABLE = 1;
	private final int NOTHING = 0;
	
	/**
	 * Wczytuje mapê z pliku dla zadanej œcie¿ki do pliku.
	 * @param path œcie¿ka do pliku
	 * @throws IOException wyj¹tek zostaje wyrzucony, jeœli nie ma pliku o podanej œcie¿ce.
	 */
	public void loadMapFromFile(Path path) throws IOException{
		 map = new ArrayList<List<Integer>>(Map.MAP_HEIGHT);
		Scanner in = new Scanner(path);
		
		for(int i = 0; i < Map.MAP_HEIGHT; i++){
			List<Integer> columns = new ArrayList<Integer>(Map.MAP_WIDTH);
			if(! in.hasNextLine()){ 
				in.close();
				map = null;
				throw new IllegalArgumentException("Za ma³o wierszy, mapa powinna mieæ"
					+ "wysokoœæ = " + Map.MAP_HEIGHT);
			}
			for(int j = 0; j < Map.MAP_WIDTH; j++){
				if(! in.hasNextInt()){
					in.close();
					map = null;
					throw new IllegalArgumentException("Za ma³o kolumn w wierszu "
							+ (i + 1) + " powinno byæ " + Map.MAP_WIDTH + " kolumn!");
				}
				int number = in.nextInt();
				if(number != TABLE && number!= NOTHING){
					in.close();
					map = null;
					throw new IllegalArgumentException("Plik z map¹ ma z³y format powinien zawieraæ"
							+ "tylko " + TABLE + " i " + NOTHING + "(b³¹d w wierszu: " + (i + 1) +
							" kolumnie: " + (j + 1));
				}
				else columns.add(number);
			}
		}
		in.close();
	}
	
	public List<List<Integer>> getMap() {
		return map;
	}

	/**
	 * Dodaje do wczytanej wczeœniej mapy krzes³a.
	 * @return false, jeœli nie wczytano wczeœniej mapy (za pomoc¹ metody loadMapFromFile)
	 */
	public boolean createMapWithChairs(){
		if(map == null) return false;
		
		for(int i = 0; i < map.size(); i++){
			for(int j = 0; j < map.get(i).size(); j++){
				if(map.get(i).get(j) == TABLE){
					if(i - 1 >= 0){    //je¿eli istnieje mapa w górê
						if(map.get(i - 1).get(j) != TABLE){  //je¿eli tam nie ma sto³u
							map.get(i - 1).remove(j);
							map.get(i - 1).add(j, Map.CHAIR);
						}
						if(i + 1 < Map.MAP_HEIGHT){  //w dó³
							if(map.get(i + 1).get(j) != TABLE){  //je¿eli tam nie ma sto³u
								map.get(i + 1).remove(j);
								map.get(i + 1).add(j, Map.CHAIR);
							}
						}
						if(j - 1 >= 0){   // w lewo
							if(map.get(i).get(j - 1) != TABLE){  //je¿eli tam nie ma sto³u
								map.get(i).remove(j - 1);
								map.get(i).add(j - 1, Map.CHAIR);
							}
						}
						if(j + 1 < Map.MAP_WIDTH){   // w prawo
							if(map.get(i).get(j + 1) != TABLE){  //je¿eli tam nie ma sto³u
								map.get(i).remove(j + 1);
								map.get(i).add(j + 1, Map.CHAIR);
							}
						}
					}
				}
			}
		}
		return true;	
	}
	
	

}
