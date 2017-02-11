package cellsociety_team06;

import java.util.List;

public class GridGenerator {
	
	private int simType = 0;
	private int unitShape = 0;
	private int gridSize = 0;
	private List<Integer[]> neighborConfig;
	private int boundaryStyle = 0;
	private int inputStyle = 0;
	
	private ReadXMLFile files;
	
	public GridGenerator(int st, int us, int gs, List<Integer[]> nc, int bs, int is){
		simType = st;
		unitShape = us;
		gridSize = gs;
		neighborConfig = nc;
		boundaryStyle = bs;
		inputStyle = is;
		
		files = new ReadXMLFile();
	}
	
	private int getDown(){
		int down = 0;
		
		if (gridSize == 1){
			if (unitShape == 3){
				down = 20;
			} else {
				down = 10;
			}
		} else if (gridSize == 2){
			if (unitShape == 3){
				down = 40;
			} else {
				down = 20;
			}
		} else {
			if (unitShape == 3){
				down = 60;
			} else {
				down = 30;
			}
		}
		
		return down;
	}
	
	private int getAcross(){
		int across = 0;
		
		if (gridSize == 1){
			across = 10;
		} else if (gridSize == 2){
			across = 20;
		} else {
			across = 30;
		}
		
		return across;
	}
	
	
	
	
}
