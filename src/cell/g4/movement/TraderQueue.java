package cell.g4.movement;

import java.util.ArrayList;
import java.util.List;

// trader queue keeps the traders that are "controlled" by us
public class TraderQueue {
	private List<Integer> nextTraders;
	
	public TraderQueue() {
		nextTraders = new ArrayList<Integer>();
	}
	
	public boolean isEmpty() {
		return nextTraders.size() == 0;				
	}
}
