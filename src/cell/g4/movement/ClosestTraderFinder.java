package cell.g4.movement;

import cell.g4.Board;
import cell.g4.movement.TraderFinder;


/*
 *  in top 3 nearest traders, find a trader that we are the closest
 *  if we cannot find any, take the nearest one
 *  
 *  Tested against NearestTradeFinder, better
 */

/*
 * TODO: check if it is feasible to reach that trader
 */

public class ClosestTraderFinder extends TraderFinder {
	private final static int topK = 3;
	
	private final static boolean yielding = false;

	public ClosestTraderFinder(Board board, int playerIndex) {
		super(board, playerIndex);
	}
		
	protected boolean isClosest(int[] dists) {
		int ourdist = dists[playerIndex];
		for (int i = 0; i < dists.length; i++) {
			if (dists[i] < ourdist)
				return false;
			
			if (i != playerIndex && dists[i]==ourdist) {
				if (yielding) {
					System.out.println("We are yielding");
					return false;
				}
				else
					return true;
			}
		}
		return true;
	}
	
	protected void sort(int[] dists, int[] indices) {
		for (int i = 1; i < dists.length; i++) {
			int value = dists[i];
			int j = i - 1;
			while (j >=0 && dists[j] > value) {
				dists[j+1] = dists[j];
				indices[j+1] = indices[j];
				j--;
			}
			dists[j+1] = value;
			indices[j+1] = i;
		}
	}
	
	@Override
	public int findBestTrader(int[] location, int[][] teams, int[][] traders) {
		int[][] dists = new int[traders.length][teams.length];
		int[] ourdists = new int[traders.length];
		int[] indices = new int[traders.length];

		for (int i = 0; i < traders.length; i++) {
			indices[i] = i;
			for (int j = 0; j < teams.length; j++) {
				// skip out team
				if (teams[j] == null)
					dists[i][j] = Integer.MAX_VALUE;
				else
					dists[i][j] = board.mindist(traders[i], teams[j]);
			}
			ourdists[i] = board.mindist(traders[i], location);
		}
		
		sort(ourdists, indices);
		
		// TODO:
		// Based on the number of traders and size of map, use different strategies
		// e.g. There is only one trader, go for it!
		for (int k = 0; k < Math.min(topK, traders.length); k++) {
			int index = indices[k];
			if (isClosest(dists[index]))
				return index;
		}

		// Otherwise we we choose our nearest
		return indices[0];
	}
}