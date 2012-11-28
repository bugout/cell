package cell.g4;

import cell.g4.movement.MoveAlgo;
import cell.g4.movement.ShortestPathMove;
import cell.g4.trade.MergeTrade;
import cell.g4.trade.TradeAlgo;

public class Player implements cell.sim.Player {
	private static int versions = 0;
	private int version = ++versions;
	public static int PlayerIndex = -1;
	
	// the map
	private Board board = null;
	// our sack
	private Sack sacks;
	// our location
	private int[] loc = new int[2];
	// movement algorithm
	private MoveAlgo movement;
	// trading algorithm
	private TradeAlgo trading;	

	@Override
	public Direction move(int[][] map, int[] location, int[] sack,
			int[][] players, int[][] traders) {
		// create a map object the first time we move 
		if (board == null) {
			board = new Board(map);
			sacks = new Sack(sack);
			
			trading = new MergeTrade(board, sacks);
			movement = new ShortestPathMove(board, sacks);
			
			Game.initGame(players.length);
			
			PlayerIndex = findPlayerIndex(location, players);
		}
		// routines
		loc[0] = location[0]; loc[1] = location[1];		
		sacks.update(sack);
		
		
		Direction dir = movement.move(location, players, traders);
		int[] new_location = board.nextLoc(location, dir);		
		int color = board.getColor(new_location);		
		sacks.decrease(color);
		
		return dir;
	}
	
	private int findPlayerIndex(int[] location, int[][] players) {
		int index = -1;
		for (int i = 0; i < players.length; i++) {
			if (location[0] == players[i][0] && location[1] == players[i][1]) {
				index = i;
				break;
			}
		}
		assert(index >= 0);
		return index;
	}

	@Override
	public void trade(double[] rate, int[] request, int[] give) {
		trading.trade(rate, request, give);
	}
	
	@Override
	public String name() {
		return "G4" + (versions > 1 ? " v" + version : "");
	}
}