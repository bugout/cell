package cell.g4.movement;

import cell.g4.Board;
import cell.g4.Player;
import cell.g4.Sack;
import cell.sim.Player.Direction;

public abstract class MoveAlgo {
	protected Sack sack = null;
	protected Board board = null;
	protected int playerIndex = -1;
	
	public MoveAlgo(Board board, Sack sack, int playerIndex) {
		this.board = board;
		this.sack = sack;
		this.playerIndex = playerIndex;
	}
	
	public abstract Direction move(int[] location,
			int[][] players, int[][] traders); 

}
