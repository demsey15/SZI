/**
 * 
 */
package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;

import java.util.List;

/**
 * @author Dominik Demski
 *
 */
public class Waiter {

	public void setCurrentPosition(Coordinates currentPosition) {
		this.currentPosition = currentPosition;
	}

	private Coordinates currentPosition;
	private Monitor monitor = Monitor.getInstance();

	public Coordinates getCurrentPosition() {
		return currentPosition;
	}

	public Waiter(Coordinates currentPosition) {
		this.currentPosition = currentPosition;
	}

	public void move(List<Coordinates> path){
		monitor.callListenersOnMove(path);
	}
	
	private Waiter(){}
	
	private static Waiter waiter;
	
	public static Waiter getInstance(){
		return (waiter == null)? (waiter = new Waiter()) : waiter;
	}
}
