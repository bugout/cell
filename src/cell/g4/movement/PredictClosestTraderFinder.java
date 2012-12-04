package cell.g4.movement;

import cell.g4.Board;
import cell.g4.Game;


/*
 * Even if we are not the closest
 * As long as there are a closer trader to that player, we are safe
 */
public class PredictClosestTraderFinder extends ClosestTraderFinder {
	
	public PredictClosestTraderFinder(Board board, int playerIndex) {
		super(board, playerIndex);
	}
	

	// TODO: revise isClosest
	@Override
	protected boolean isClosest(int[] dists) {
		int ourdist = dists[playerIndex];
		for (int i = 0; i < dists.length; i++) {
			if (dists[i] < ourdist)
				return false;
		}
		return true;
	}
}
