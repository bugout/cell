package cell.g4.trade;

import java.util.List;

import cell.g4.Board;
import cell.g4.Path;
import cell.g4.Player;
import cell.g4.Sack;
import cell.g4.movement.SafeTraderFinder;

public class MergeTrade extends TradeAlgo {
	private WinningTrade winningTrading;
	private SafeTrade safeTrading;
	private RefillRiskyTrade refillTrading;
	private TradeAlgo current;
	
	public MergeTrade(Board board, Sack sack, Player player) {
		super(board, sack, player);
		safeTrading = new SafeTrade(board, sack, player);
		refillTrading = new RefillRiskyTrade(board, sack, player);
		winningTrading = new WinningTrade(board, sack, player);
		
		current = refillTrading;
	}
		
	@Override
	public void trade(double[] rate, int[] request, int[] give,
			int[] savedLocation, int[][] savedPlayers, int[][] savedTraders) {
		if (winningTrading.canWin(rate))
			current = winningTrading;
		else if (safeTrading.isSafe(savedLocation, savedPlayers, savedTraders))
			current = safeTrading;
		else
			current = refillTrading;
		
		current.trade(rate, request, give, savedLocation, savedPlayers, savedTraders);
		
		totalTradeCount++;
		System.err.println("Safe Ratio: " + safeTradeCount + "," + totalTradeCount);
	}	
}
