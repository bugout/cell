package cell.g4.movement;

import cell.g4.Board;
import cell.g4.Game;

public class PredictClosestTraderFinder extends ClosestTraderFinder {
	
	public PredictClosestTraderFinder(Board board, int playerIndex) {
		super(board, playerIndex);
	}
	

	@Override
	protected boolean isClosest(int[] dists) {
		int ourdist = dists[Game.getGame().getOurIndex()];
		for (int i = 0; i < dists.length; i++) {
			if (dists[i] < ourdist)
				return false;
		}
		return true;
	}
}
