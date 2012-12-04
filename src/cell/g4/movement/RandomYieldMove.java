package cell.g4.movement;

import java.util.Random;

import cell.g4.Board;
import cell.g4.Sack;
import cell.sim.Player.Direction;

public class RandomYieldMove extends YieldMove {
	private Random rnd = new Random();
	
	public RandomYieldMove(Board board, Sack sack, int playerIndex) {
		super(board, sack, playerIndex);
	}

	@Override
	public Direction move(int[] location, int[][] players, int[][] traders) {
		Direction dirs[] =
			{Direction.W,  Direction.E,
			 Direction.NW, Direction.N,
			 Direction.S, Direction.SE};
		
		return dirs[rnd.nextInt(6)];
	}

}
