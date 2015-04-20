/**
 * 
 */
package demski;

import java.util.List;

/**
 * Klasa reprezentuj�ca map� z r�nymi krzes�ami i sto�ami.
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

	
	private List<List<Integer>> map;
	
	public Map(List<List<Integer>> map){
		this.map = map;
	}
	
	/**
	 * Zwraca id obiektu znajduj�cego si� pod wskazanymi wsp�rz�dnymi na mapie.
	 * @param coordinates wsp�rzedne obiektu.
	 * @return zwraca id obiektu (patrz sta�e w klasie Map) lub -1, je�li podane wsp�rzedne wykraczaj�
	 * poza map�.
	 */
	public int getObjectId(Coordinates coordinates){
		if(!checkIfCoordinatesAreInMap(coordinates)) return -1;
		
		return map.get(coordinates.getRow()).get(coordinates.getColumn());
	}
	/**
	 * Zmienia stan krzes�a na zielone, czerwone albo zwyk�e.
	 * @param coordinates wsp�rz�dne krzes�a
	 * @param chairState stan, w kt�ry wej�� ma krzes�o (patrz sta�e w klasie Map).
	 * @return false, je�li podane wsp�rz�dne wykraczaj� poza map�, albo pod podanymi wsp�rzednymi
	 * nie ma krzes�a, w przeciwnym przypadku true.
	 */
	public boolean setChairAs(Coordinates coordinates, int chairState){
		int id = getObjectId(coordinates);
		if(id == -1) return false;
		if(id != GREEN_CHAIR && id != RED_CHAIR && id != CHAIR) return false;
		if(chairState != GREEN_CHAIR && chairState != RED_CHAIR && chairState != CHAIR) return false;
		map.get(coordinates.getRow()).remove(coordinates.getColumn());
		map.get(coordinates.getRow()).add(coordinates.getColumn(), chairState);
		return true;
	}
	
	private boolean checkIfCoordinatesAreInMap(Coordinates coordinates){
		return (coordinates.getRow() >= 0 && coordinates.getRow() < MAP_HEIGHT &&
				coordinates.getColumn() >= 0 && coordinates.getColumn() > MAP_WIDTH);
	}
	
}