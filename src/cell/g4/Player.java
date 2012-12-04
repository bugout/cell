package cell.g4;

import java.util.ArrayList;
import java.util.Random;

import cell.g4.movement.MoveAlgo;
import cell.g4.movement.ShortestPathMove;
import cell.g4.movement.TraderQueue;
import cell.g4.trade.TradeAlgo;
import cell.g4.trade.TradingDispatcher;


// TODO: find all of our players
// use static variable to record the initial locations of our players
// In the next round, find the indices of all of them
public class Player implements cell.sim.Player {
	public static int versions = 0;
	private int version = ++versions;
	private Random rnd = new Random();

	// the map
	private Board board = null;
	// our sack
	private Sack sacks;
	
	private int playerIndex;
	private int[][] savedTraders;
	private int[][] savedPlayers;
	// NOTE: EQUAL TO THE LOCATION AFTER TRADING
	private int[] savedLocation;

	// movement algorithm
	private MoveAlgo movement;
	
	private TradingDispatcher tradeDispatcher;
	
	private TraderQueue queue;
	
	private Path savedPath = null;
	
	private Game game = null;

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
	public Direction move(int[][] map, int[] location, int[] sack,
			int[][] players, int[][] traders) {
		if (board == null) {
			board = new Board(map);
			sacks = new DynamicWeightedSack(sack,board);
			playerIndex = findPlayerIndex(location, players);
			
			tradeDispatcher = new TradingDispatcher(board, sacks, this);
			
			//trading = new MergeTrade(board, sacks, this);
			movement = new ShortestPathMove(board, sacks, playerIndex);
		
			game = Game.initGame(location, players);
		}
		// routines
		savedTraders = traders;
		savedPlayers = players;
		
		sacks.update(sack, location);
		game.updateTrades(players, traders);
		
		Direction dir = null;
		if (savedPath == null) {		
			 dir = movement.move(location, players, traders);			
		}
		else {
			dir = savedPath.popFirst();
			if (savedPath.length() == 0)
				savedPath = null;
		}
		dir = validateMove(location, dir);
		
		int[] new_location = board.nextLoc(location, dir);		
		int color = board.getColor(new_location);		
		sacks.decrease(color);
		
		savedLocation = new_location;
	
		return dir;
	}

	// TODO
	// instead of choosing a random direction, choose a direction that maximize our survival rate
	private Direction validateMove(int[] location, Direction nextdir) {
		Direction dirs[] =
			{Direction.W,  Direction.E,
			 Direction.NW, Direction.N,
			 Direction.S, Direction.SE};
		ArrayList<Direction> possible = new ArrayList<Direction>();
		
		// check if player can move
		int possible_moves = 0;
		for (Direction dir : dirs) {
			int[] new_location = board.nextLoc(location, dir);
			int color = board.getColor(new_location);
			if (color >= 0 && sacks.getStock(color) > 0) {
				possible_moves++;
				if (nextdir == dir)
					return nextdir;
				else
					possible.add(dir);
			}
		}
		if (possible_moves == 0) {
			return nextdir;
		}
		else
			return possible.get(rnd.nextInt(possible.size()));
	}	
	
	public int getOurIndex() {
		return playerIndex;
	}
	
	@Override
	public void trade(double[] rate, int[] request, int[] give) {
		TradeAlgo trading = 
				tradeDispatcher.pickTradingAlgo(rate, request, give, savedLocation, savedPlayers, savedTraders);
		
		trading.trade(rate, request, give, savedLocation, savedPlayers, savedTraders);
	}
	
	@Override
	public String name() {
		return "G4" + (versions > 1 ? " v" + version : "");
	}

	public void setSavedPath(Path path) {
		this.savedPath = path;
	}
}