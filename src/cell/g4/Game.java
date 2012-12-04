package cell.g4;


// DONOT use static class
// When there are multiple instances, their might be some problem
public class Game {
	private static Game thegame;
	
	private Team[] teams;
	private int[][] tradersLast = null;
	
	public static Game getGame() {
		assert(thegame != null);
		return thegame;
	}
	
	private Game(int[] location, int[][] players) {
		teams = new Team[players.length];
		for (int i = 0; i < teams.length; i++)
			teams[i] = new Team(); 
	}
		
	
	public static Game initGame(int[] location, int[][] players) {
		thegame = new Game(location, players);
		return thegame;
	}

	// VERY TRICKY !!!
	// NEED TO KEEP TRACK OF LAST LOCATION OF A TRADER
	public void updateTrades(int[][] players, int[][] traders) {
		if (tradersLast == null) {
			tradersLast = traders; 
			return;
		}
		
		for (int i = 0; i < traders.length; i++) {
			if (traders[i][0] != tradersLast[i][0] || traders[i][1] != tradersLast[i][1]) {
				for (int j = 0; j < players.length; j++) {
					if (players[j] == null)
						continue;
					if (players[j][0] == tradersLast[i][0] && players[j][1] == tradersLast[i][1]) {
						teams[j].addTrade();
						break;
					}
				}
			}
		}
		tradersLast = traders;
	}
	
	public Team[] getTeams() {
		return teams;
	}
}
