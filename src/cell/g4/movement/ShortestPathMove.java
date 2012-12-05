package cell.g4.movement;

import java.util.List;
import java.util.Random;

import cell.g4.Board;
import cell.g4.Player;
import cell.g4.Sack;
import cell.sim.Player.Direction;

public class ShortestPathMove extends MoveAlgo {
	private TraderFinder traderFinder;	
	private YieldMove yield;
	
	private boolean lastYield = true;
	
	private Random rnd = new Random();
	
	public ShortestPathMove(Board board, Sack sack, Player player) {
		super(board, sack, player);
		traderFinder = new ClosestTraderFinder(board, player);
		yield = new MaxControlYieldMove(board, sack, player);
	}
	
	private boolean yieldOrNot() {
		return rnd.nextBoolean();
	}
	
	@Override
	public Direction move(int[] location, int[][] players, int[][] traders) {
		
		assert(board != null);
		int nextTrader = traderFinder.findBestTrader(location, players, traders);
		
		if (nextTrader < 0) {
			nextTrader = -nextTrader;
			if (lastYield == true) {
				if (yieldOrNot()) {
					return yield.move(location, players, traders);
				}
				else {
					lastYield = false;
				}
			}
		}
		
		List<Direction> dirs = board.nextMove(location, traders[nextTrader]);
		Direction dir = pickDir(location, dirs);

		// reset = true
		if (board.mindist(location, traders[nextTrader]) == 1)
			lastYield = true;
		
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
