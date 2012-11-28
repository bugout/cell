package cell.g4_smart.trade;

import cell.g4_smart.Board;
import cell.g4_smart.Sack;

public class RiskyTrade extends TradeAlgo {
	
	public RiskyTrade(Board board, Sack sack) {
		super(board, sack);
	}

	@Override
	public void trade(double[] rate, int[] request, int[] give) {
		//find the cheapest marble
		int invaluable = invaluableColor(rate);
		
		//then buy everything else --> give very everyhing
		for (int i = 0 ; i != 6 ; ++i)
			request[i] = give[i] = 0;
		
		for (int i=0; i != 6; ++i){
			if(i == invaluable)
				continue;
			else {
				int amount = maxAmount(i, invaluable);
				give[i] = amount;
				request[invaluable] = request[invaluable] + (int)(amount * rate[i] / rate[invaluable]);
			}
		}
		
	}
	
	private int valuableColor(double[] rate) {
		double maxrate = 0;
		int color = -1;
		for (int i = 0; i < rate.length; i++) {
			if (rate[i] > maxrate) {
				maxrate = rate[i];
				color = i;
			}
		}
		return color;
	}
	
	private int invaluableColor(double[] rate) {
		double minrate = Double.MAX_VALUE;
		int color = -1;
		for (int i = 0; i < rate.length; i++) {
			if (rate[i] < minrate) {
				minrate = rate[i];
				color = i;
			}
		}
		return color;
	}
	
	private int maxAmount(int valuable, int invaluable) {
		return Math.max(0, sack.getStock(valuable) - sack.getReserve(valuable));
		//TODO: need to consider the future...
	}
}
