/**
 * Written by Gideon Pfeffer
 * Makes a grid of hexagons
 */

package subGrids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Unit.Unit;
import cellsociety_team06.Grid;

public class hexGrid extends Grid {
	private int[] evenRowColMove, oddRowColMove;
	private double length;
	
	/**
	 * @param rows takes in the number of rows
	 * @param cols takes in the number of cols
	 * @param length takes in the length of the hexagon side
	 * also sets the default neighbors
	 */
	public hexGrid(int rows, int cols, int length) {
		super(rows, cols);
		rowMove = new int[] 	   {-2, -1, 1, 2, 1, -1};
		evenRowColMove = new int[] {0,   0, 0, 0, 1,  1};
		oddRowColMove = new int[] {0,   0, 0, 0, -1, -1};
		this.length = length;
		fillGrid();
	}

	/**
	 * fills the grid of the hexagons
	 */
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
	
	/**
	 * sets the x,y of all the hexagons
	 * also sets the points
	 */
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
	
	/**
	 * Sets the polygons for even rows
	 */
	private void setEvenRow(Unit u, int row, int col){
		u.setLayoutX(col * 2 *  length + length/2.0 + length * col);
		u.setLayoutY(row *length);
	}
	
	/**
	 * Sets the polygons for odd rows
	 */
	private void setOddRow(Unit u, int row, int col){
		u.setLayoutX(col * 2 *  length + length * 2 + length * col);
		u.setLayoutY(row * length);
	}
	
	/**
	 * returns a map of the neighbors with the
	 * key being the integer [row,col] and
	 * the value being the shape
	 * has a special case
	 */
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
	
	/**
	 * Changes the relative neighbors for the hexagons because
	 * neighbors are handled differently
	 */
	public void setHexNeighbors(int[] oddRowMoves, int[] evenRowMoves){
		if(oddRowMoves.length != evenRowMoves.length){
			throw new IllegalArgumentException();
		}
		this.oddRowColMove = oddRowMoves;
		this.evenRowColMove = evenRowMoves;
	}

}
