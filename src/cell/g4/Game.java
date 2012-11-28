package cell.g4;


// A class to access global information
public class Game {
	private static Game thegame;
	
	private Team[] teams;
	
	public static Game getGame() {
		assert(thegame != null);
		return thegame;
	}
	
	private Game(int teamCount) {
		teams = new Team[teamCount];
		for (int i = 0; i < teams.length; i++)
			teams[i] = new Team(); 
	}
	
	public static void initGame(int teamCount) {
		thegame = new Game(teamCount);
	}
	
	public Team[] getTeams() {
		return teams;
	}
	
}
