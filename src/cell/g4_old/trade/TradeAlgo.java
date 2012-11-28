package cell.g4_old.trade;

import cell.g4_old.Board;
import cell.g4_old.Sack;

/**
 * An interface for trading algorithm
 */
public abstract class TradeAlgo {
	protected Sack sack = null;
	protected Board board = null;
	
	public TradeAlgo(Board board, Sack sack) {
		this.board = board;
		this.sack = sack;
	}
	
	public abstract void trade(double[] rate, int[] request, int[] give);
}
