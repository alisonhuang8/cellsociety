package cellsociety_team06;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class hexGrid extends Grid {
	int[] rowMove;
	int[] colMove;
	double length;
	

	public hexGrid(int rows, int cols, int length) {
		super(rows, cols);
		rowMove = new int[] {-1, 0, 0, 1};
		colMove = new int[] {0, 1, -1, 0};
		this.length = length;
		fillGrid();
	}

	@Override
	public void fillGrid() {
		for (int i = 0; i < rows(); i++) {
			List<Unit> cols = new ArrayList<>();
			for (int j = 0; j < cols(); j++) {
				Unit u = new Unit();
				setPoly(u, i, j);
				cols.add((Unit) u);
			}
			grid.add(cols);
		}
	}
	
	private void setPoly(Unit u, int row, int col){
		setLayoutUp(u, row, col);
		u.getPoints().addAll(new Double[]{
				0.0, 0.0,
	            length, 0.0,
	            (3.0/2.0) * length, length,
	            length, 2.0 * length,
	            0.0, 2 * length,
	            - (1.0/2.0) * length, length});
	}
	
	private void setLayoutUp(Unit u, int row, int col){
		u.setLayoutX(row * 2 * length + length/2.0 + row * length);
		u.setLayoutY(col * 2 * length);
	}
	
	@Override
	public Map<Integer[], Unit> getNeighbors(int row, int col) {
		Map<Integer[], Unit> map = new HashMap<>();
		Unit u;
		Integer[] place;
		for(int i = 0; i < rowMove.length; i++){
			int newRow = row + rowMove[i];
			int newCol = col + colMove[i];
			if(newRow >= 0 && newRow < rows() && newCol >= 0 && newCol < cols()){
				place = new Integer[] {newRow, newCol};
				u = grid.get(newCol).get(newRow);
				map.put(place, u);
			}
		}
		return map;
	}
}
