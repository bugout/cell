package cell.g4_smart.movement;

import cell.g4_smart.Board;
import cell.g4_smart.Sack;
import cell.sim.Player.Direction;

public abstract class MoveAlgo {
	protected Sack sack = null;
	protected Board board = null;
	
	public MoveAlgo(Board board, Sack sack) {
		this.board = board;
		this.sack = sack;
	}
	
	public abstract Direction move(int[] location,
			int[][] players, int[][] traders); 
	
}
