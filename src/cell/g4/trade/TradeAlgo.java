package cell.g4.trade;

import cell.g4.Board;
import cell.g4.Player;
import cell.g4.Sack;

/**
 * An interface for trading algorithm
 */
public abstract class TradeAlgo {
	protected Sack sack = null;
	protected Board board = null;
	protected Player player = null;
	
	protected static int safeTradeCount = 0;
	protected static int totalTradeCount = 0;
	
	public TradeAlgo(Board board, Sack sack, Player player) {
		this.board = board;
		this.sack = sack;
		this.player = player;
	}
	
	public abstract void trade(double[] rate, int[] request, int[] give, 
			int[] savedLocation, int[][] savedPlayers, int[][] savedTraders);

}
