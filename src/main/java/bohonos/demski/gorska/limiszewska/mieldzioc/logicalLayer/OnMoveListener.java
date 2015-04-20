/**
 * 
 */
package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;

import java.util.List;

/**
 * @author Dominik Demski
 *
 */
public interface OnMoveListener {
	/**
	 * W tej metodzie zaimplementuj reakcjê na ruch kelnera.
	 * @param path œcie¿ka, któr¹ ma przejœæ kelner.
	 */
	public void onMove(List<Coordinates> path);
}
