package cell.g4_smart.trade;

import cell.g4_smart.Board;
import cell.g4_smart.Sack;

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
