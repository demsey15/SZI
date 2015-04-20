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
	 * W tej metodzie zaimplementuj reakcj� na ruch kelnera.
	 * @param path �cie�ka, kt�r� ma przej�� kelner.
	 */
	public void onMove(List<Coordinates> path);
}
