package cell.g4_old.trade;

import cell.g4_old.Board;
import cell.g4_old.Color;
import cell.g4_old.Sack;

public class WinningTrade extends TradeAlgo {

	public WinningTrade(Board board, Sack sack) {
		super(board, sack);
	}

	@Override
	public void trade(double[] rate, int[] request, int[] give) {
		for (int i = 0; i < Color.values().length; i++) {
			request[i] = 0;
			give[i]	= 0;
		}
			
		for (int i = 0; i < Color.values().length; i++) {
			int remain = sack.getStock(i) - Sack.WinningStock;
			if (remain > 0) {
				give[i] = remain;
			}
			else {
				request[i] = -remain;
			}
		}
	}
	
	public boolean canWin(double rate[]) {
		int giveValue = 0;
		int requestValue = 0;
		for (int i = 0; i < Color.values().length; i++) {
			int remain = sack.getStock(i) - Sack.WinningStock;
			if (remain > 0) {
				giveValue += remain * rate[i];
			}
			else {
				requestValue += -remain * rate[i];
			}
		}
		
		if (giveValue >= requestValue)
			return true;
		else
			return false;
	}

}
