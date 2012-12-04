package cell.g4.movement;

import java.util.List;

import cell.g4.Board;
import cell.g4.Sack;
import cell.sim.Player.Direction;

public class CenterYieldMove extends YieldMove {

	public CenterYieldMove(Board board, Sack sack, int playerIndex) {
		super(board, sack, playerIndex);
	}

	/*
	 * If there is no trader to approach, move to the center
	 */
	@Override
	public Direction move(int[] location, int[][] players, int[][] traders) {
		int i = board.getBoard().length / 2;
		int j = i;		
		
		List<Direction> dirs = board.nextMove(location, new int[] {i,j});
		
		Direction dir = pickDir(location, dirs);
		
		return dir;
	}

	private Direction pickDir(int[] location, List<Direction> dirs) {
		int maxcolor = 0;
		Direction dir = null;
		for (Direction d : dirs) {
			int[] loc = board.nextLoc(location, d);
			if (sack.getStock(board.getColor(loc)) >= maxcolor) {
				maxcolor = board.getColor(loc);
				dir = d;
			}
		}
		return dir;
	}
	
}
