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
	 * Wczytuje map� z pliku dla zadanej �cie�ki do pliku.
	 * @param path �cie�ka do pliku
	 * @throws IOException wyj�tek zostaje wyrzucony, je�li nie ma pliku o podanej �cie�ce.
	 */
	public void loadMapFromFile(Path path) throws IOException{
		Scanner in = new Scanner(path);
		
		for(int i = 0; i < Map.MAP_HEIGHT; i++){
			List<Integer> columns = new ArrayList<Integer>(Map.MAP_WIDTH);
			if(! in.hasNextLine()){ 
				in.close();
				throw new IllegalArgumentException("Za ma�o wierszy, mapa powinna mie�"
					+ "wysoko�� = " + Map.MAP_HEIGHT);
			}
			for(int j = 0; j < Map.MAP_WIDTH; j++){
				if(! in.hasNextInt()){
					in.close();
					throw new IllegalArgumentException("Za ma�o kolumn w wierszu "
							+ (i + 1) + " powinno by� " + Map.MAP_WIDTH + " kolumn!");
				}
				int number = in.nextInt();
				if(number != TABLE && number!= NOTHING){
					in.close();
					throw new IllegalArgumentException("Plik z map� ma z�y format powinien zawiera�"
							+ "tylko " + TABLE + " i " + NOTHING + "(b��d w wierszu: " + (i + 1) +
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
