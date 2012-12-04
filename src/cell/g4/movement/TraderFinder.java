package cell.g4.movement;

import cell.g4.Board;

public abstract class TraderFinder {
	protected int playerIndex;
	
	protected Board board;
	
	public TraderFinder(Board board, int playerIndex) {
		this.board = board;
		this.playerIndex = playerIndex;
	}
	
	public abstract int findBestTrader(int[] location, int[][] teams, int[][] traders);
}
