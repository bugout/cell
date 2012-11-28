package cell.g4_old.trade;

import cell.g4_old.Board;
import cell.g4_old.Sack;

public class MergeTrade extends TradeAlgo {
	private MaxRateDiffTrade diffTrading;
	private WinningTrade winningTrading;
	private RiskyTrade riskTrading;
	private TradeAlgo current;
	
	
	public MergeTrade(Board board, Sack sack) {
		super(board, sack);
		diffTrading = new MaxRateDiffTrade(board, sack);
		riskTrading = new RiskyTrade(board, sack);
		winningTrading = new WinningTrade(board, sack);
		
		current = diffTrading;
	}

	@Override
	public void trade(double[] rate, int[] request, int[] give) {
		if (winningTrading.canWin(rate)) {
			current = winningTrading;
		}
		
		current.trade(rate, request, give);
	}	
}