package subGrids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Unit.Unit;
import cellsociety_team06.Grid;

public class hexGrid extends Grid {
	int[] rowMove, evenRowColMove, oddRowColMove;
	double length;
	

	public hexGrid(int rows, int cols, int length) {
		super(rows, cols);
		rowMove = new int[] 	   {-2, -1, 1, 2, 1, -1};
		evenRowColMove = new int[] {0,   0, 0, 0, 1,  1};
		oddRowColMove = new int[] {0,   0, 0, 0, -1, -1};
		this.length = length;
		fillGrid();
	}

	@Override
	public void fillGrid() {
		for (int i = 0; i < rows(); i++) {
			List<Unit> row = new ArrayList<>();
			for (int j = 0; j < cols(); j++) {
				Unit u = new Unit();
				setPoly(u, i, j);
				row.add((Unit) u);
			}
			grid.add(row);
		}
	}
	
	private void setPoly(Unit u, int row, int col){
		if(row % 2 == 0) setEvenRow(u, row, col);
		else setOddRow(u, row, col);
		u.getPoints().addAll(new Double[]{
				0.0, 0.0,
	            length, 0.0,
	            (3.0/2.0) * length, length,
	            length, 2.0 * length,
	            0.0, 2 * length,
	            - (1.0/2.0) * length, length});
	}
	
	private void setEvenRow(Unit u, int row, int col){
		u.setLayoutX(col * 2 *  length + length/2.0 + length * col);
		u.setLayoutY(row *length);
	}
	
	private void setOddRow(Unit u, int row, int col){
		u.setLayoutX(col * 2 *  length + length * 2 + length * col);
		u.setLayoutY(row * length);
	}
	
	@Override
	public Map<Integer[], Unit> getNeighbors(int row, int col) {
		Map<Integer[], Unit> map = new HashMap<>();
		int[] colMove = oddRowColMove;
		if(row % 2 != 0) colMove = evenRowColMove;
		Unit u;
		Integer[] place;
		for(int i = 0; i < rowMove.length; i++){
			int newRow = row + rowMove[i];
			int newCol = col + colMove[i];
			if(newRow >= 0 && newRow < rows() && newCol >= 0 && newCol < cols()){
				place = new Integer[] {newRow, newCol};
				u = grid.get(newRow).get(newCol);
				map.put(place, u);
			}
		}
		return map;
	}

}
