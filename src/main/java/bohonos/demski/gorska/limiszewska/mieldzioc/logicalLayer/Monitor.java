/**
 * 
 */
package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dominik Demski
 * klasa rejestruj¹ca s³uchaczy zdarzenia przemieszczenia siê kelnera
 * i informuj¹ca ich o przemieszczeniu siê.
 */
public class Monitor {
	private List<OnMoveListener> listeners = new ArrayList<OnMoveListener>();
	
	/**
	 * Rejestruje s³uchacza zdarzenia przemieszczenia siê kelnera (musi on implementowaæ
	 * interfejs listener).
	 * @param listener s³uchacz
	 */
	public void registerListener(OnMoveListener listener){
		listeners.add(listener);
	}
	
	/**
	 * Zawiadamia wszystkich s³uchaczy o przemieszczeniu siê kelnera
	 * (podaje œcie¿kê przejœcia).
	 * @param path œcie¿ka, któr¹ pokona³ kelner.
	 */
	public void callListenersOnMove(List<Coordinates> path){
		for(OnMoveListener listener : listeners){
			listener.onMove(path);
		}
	}
	
	private static Monitor instance;
	private Monitor(){}
	
	public static Monitor getInstance(){
		if (instance == null) instance = new Monitor();
		return instance;
	}
}
