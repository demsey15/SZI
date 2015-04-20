/**
 * 
 */
package demski;

import java.util.List;

/**
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
	
	
	
}