package cell.dumb;

import java.util.Random;

public class Player implements cell.sim.Player {

	private Random gen = new Random();
	private int[] savedSack;
	private static int versions = 0;
	private int version = ++versions;

	public String name() { return "Dumb" + (versions > 1 ? " v" + version : ""); }

	public Direction move(int[][] board, int[] location, int[] sack,
	                      int[][] players, int[][] traders)
	{
		savedSack = copyI(sack);
		for (;;) {
			Direction dir = randomDirection();
			int[] new_location = move(location, dir);
			int color = color(new_location, board);
			if (color >= 0 && sack[color] != 0) {
				savedSack[color]--;
				return dir;
			}
		}
	}

	private Direction randomDirection()
	{
		switch(gen.nextInt(6)) {
			case 0: return Direction.E;
			case 1: return Direction.W;
			case 2: return Direction.SE;
			case 3: return Direction.S;
			case 4: return Direction.N;
			case 5: return Direction.NW;
			default: return null;
		}
	}

	public void trade(double[] rate, int[] request, int[] give)
	{
		for (int r = 0 ; r != 6 ; ++r)
			request[r] = give[r] = 0;
		double rv = 0.0, gv = 0.0;
		for (int i = 0 ; i != 10 ; ++i) {
			int j = gen.nextInt(6);
			if (give[j] == savedSack[j]) break;
			give[j]++;
			gv += rate[j];
		}
		for (;;) {
			int j = gen.nextInt(6);
			if (rv + rate[j] >= gv) break;
			request[j]++;
			rv += rate[j];
		}
	}

	/**
	 * Returns the next location where Player will be located
	 * 
	 * In the world of orestis..
	 * 
	 * How moves are represented
	 *   (j axis)
	 *      E SE
	 *      |/
	 * N --------S (i axis)
	 *     /|
	 *   NW W
	 *   
	 * @param location
	 * @param dir
	 * @return
	 */
	private static int[] move(int[] location, Player.Direction dir)
	{
		int di, dj;
		int i = location[0];
		int j = location[1];
		if (dir == Player.Direction.W) {
			di = 0;
			dj = -1;
		} else if (dir == Player.Direction.E) {
			di = 0;
			dj = 1;
		} else if (dir == Player.Direction.NW) {
			di = -1;
			dj = -1;
		} else if (dir == Player.Direction.N) {
			di = -1;
			dj = 0;
		} else if (dir == Player.Direction.S) {
			di = 1;
			dj = 0;
		} else if (dir == Player.Direction.SE) {
			di = 1;
			dj = 1;
		} else return null;
		int[] new_location = {i + di, j + dj};
		return new_location;
	}

	/**
	 * Validates the values in the location, and returns the color in the board.
	 * Returns -1 if the values in the location array doesn't make sense(Negative values or greater than the size of the board)
	 * @param location
	 * @param board
	 * @return
	 */
	private static int color(int[] location, int[][] board)
	{
		int i = location[0];
		int j = location[1];
		int dim2_1 = board.length;
		if (i < 0 || i >= dim2_1 || j < 0 || j >= dim2_1)
			return -1;
		return board[i][j];
	}

	/**
	 * Clones the array
	 * 
	 * @param a
	 * @return
	 */
	private int[] copyI(int[] a)
	{
		int[] b = new int [a.length];
		for (int i = 0 ; i != a.length ; ++i)
			b[i] = a[i];
		return b;
	}
}
