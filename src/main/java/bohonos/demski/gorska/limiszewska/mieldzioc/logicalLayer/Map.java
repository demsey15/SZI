/**
 * 
 */
package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentuj¹ca mapê z ró¿nymi krzes³ami i sto³ami.
 * @author Dominik Demski
 *
 */
public class Map {

	public static final int FREE_FIELD = 0;
	public static final int TABLE = 1;
	public static final int GREEN_CHAIR = 2;
	public static final int RED_CHAIR = 3;
	public static final int CHAIR = 4;
	
	public static final int MAP_WIDTH = 21;
	public static final int MAP_HEIGHT = 17;

	
	private List<List<Object>> map;
	
	public Map(List<List<Object>> map){
		this.map = map;
	}
	
	/**
	 * Zwraca id obiektu znajduj¹cego siê pod wskazanymi wspó³rzêdnymi na mapie.
	 * @param coordinates wspó³rzedne obiektu.
	 * @return zwraca id obiektu (patrz sta³e w klasie Map) lub -1, jeœli podane wspó³rzedne wykraczaj¹
	 * poza mapê.
	 */
	public Object getObjectId(Coordinates coordinates){
		if(!checkIfCoordinatesAreInMap(coordinates)) return null;
		
		return map.get(coordinates.getRow()).get(coordinates.getColumn());
	}
	/**
	 * Zmienia stan krzes³a na zielone, czerwone albo zwyk³e.
	 * @param coordinates wspó³rzêdne krzes³a
	 * @param chairState stan, w który wejœæ ma krzes³o (patrz sta³e w klasie Map).
	 * @return false, jeœli podane wspó³rzêdne wykraczaj¹ poza mapê, albo pod podanymi wspó³rzednymi
	 * nie ma krzes³a, w przeciwnym przypadku true.
	 */
	public boolean setChairAs(Coordinates coordinates, int chairState){
		Object id = getObjectId(coordinates);
		if(id == null) return false;
		if(! (id instanceof Seat)) return false;
	//	if(id != GREEN_CHAIR && id != RED_CHAIR && id != CHAIR) return false;
		if(chairState != GREEN_CHAIR && chairState != RED_CHAIR && chairState != CHAIR) return false;
		//map.get(coordinates.getRow()).remove(coordinates.getColumn());
		//map.get(coordinates.getRow()).add(coordinates.getColumn(), chairState);
		((Seat) id).setState(chairState);
		return true;
	}
	
	/**
	 * Zwraca listê wspó³rzednych wszystkich sto³ów na mapie.
	 * @return lista wspó³rzednych wszystkich sto³ów.
	 */
	public List<Coordinates> getAllTablesCoordinates(){
		List<Coordinates> list = new ArrayList<Coordinates>();
		for(int i = 0; i < MAP_HEIGHT; i++){
			for(int j = 0; j < MAP_WIDTH; j++){
				if(map.get(i).get(j) instanceof Table)
					list.add(new Coordinates(i, j));
			}
		}
		return list;
	}

	public List<Coordinates> getAllCoordinates(){
		List<Coordinates> list = new ArrayList<Coordinates>();
		for(int i = 0; i < MAP_HEIGHT; i++){
			for(int j = 0; j < MAP_WIDTH; j++){
				//if(map.get(i).get(j) == TABLE)
					list.add(new Coordinates(i, j));
			}
		}
		return list;
	}
	
	private boolean checkIfCoordinatesAreInMap(Coordinates coordinates){
		return (coordinates.getRow() >= 0 && coordinates.getRow() < MAP_HEIGHT &&
				coordinates.getColumn() >= 0 && coordinates.getColumn() < MAP_WIDTH);
	}

	public List<List<Object>> getMap(){
		return map;
	}


	
}