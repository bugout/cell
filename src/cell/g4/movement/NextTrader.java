package cell.g4.movement;

public class NextTrader {
	private int tid;
	private boolean conflict = false;
	
	public NextTrader(int tid) {
		this.tid = tid;
	}
	
	public int getTid() {
		return tid;
	}
	
	public boolean isConflict() {
		return conflict;
	}
	
}
