package cell.g4.movement;

import java.util.Random;

import cell.g4.Board;
import cell.g4.Sack;

public class YieldDispatcher {
	private RandomYieldMove random;
	private CenterYieldMove center;
	
	private Random rnd = new Random();
	
	public YieldDispatcher(Board board, Sack sack, int playerIndex) {
		random = new RandomYieldMove(board, sack, playerIndex);
		center = new CenterYieldMove(board, sack, playerIndex);
	}
	
	public YieldMove pickYieldMove() {
		if (rnd.nextInt(2) == 0)
			return random;
		else
			return center;
	}
}
