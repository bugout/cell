package cell.g4;

public class DynamicSack extends Sack {
	double[] dist;
	int dimension;
	public DynamicSack(int[] sack,Board board){
		super(sack,board);

		dist = board.getColorDistribution();
		dimension = board.dimension();
		
		updateReserve();
	}

	private void updateReserve(){
		int[] reserve = new int[6];
		for(int i=0; i < reserve.length; i++){
			reserve[i]= (int) (dist[i] * getStock(i) * 3);
		}
		super.reserves = reserve;
	}
}


