package cell.g4.movement;

import cell.g4.Board;
import cell.g4.Sack;
import cell.sim.Player.Direction;

/*
 * When we are approaching a trader
 * If there are traders nearby
 * Find a best order to reach maximum number of traders
 */
public class LocalBestOrderMove extends MoveAlgo {

	public LocalBestOrderMove(Board board, Sack sack, int playerIndex) {
		super(board, sack, playerIndex);
	}

	@Override
	public Direction move(int[] location, int[][] players, int[][] traders) {
		return null;
	}

}
