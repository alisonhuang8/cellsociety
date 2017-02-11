/**
 * Written by Gideon Pfeffer
 * makes the square grid
 */

package subGrids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Unit.Unit;
import cellsociety_team06.Grid;

public class squareGrid extends Grid {
	private double rectWidth, rectHeight;
	
	/**
	 * makes a grid of given rows, cols
	 * height, width, and
	 * with given default neighbors
	 */
	public squareGrid(int rows, int cols, int heightSquare, int widthSquare) {
		super(rows, cols);
		rowMove = new int[] {-1, 0, 0, 1, 1, -1, 1, -1};
		colMove = new int[] {0, 1, -1, 0, 1, -1, -1, 1};
		rectWidth = widthSquare;
		rectHeight = heightSquare;
		fillGrid();
	}
	
	/**
	 * assumes a square if only given one side length
	 */
	public squareGrid(int rows, int cols, int length) {
		this(rows, cols, length, length);
	}

	/**
	 * Fills the grid with the rectangles
	 */
	@Override
	public void fillGrid() {
		for (int i = 0; i < rows(); i++) {
			List<Unit> row = new ArrayList<>();
			for (int j = 0; j < cols(); j++) {
				Unit u = new Unit();
				u.getPoints().addAll(new Double[]{
						0.0, 0.0,
			            rectHeight, 0.0,
			            rectHeight, rectWidth,
			            0.0, rectWidth});
				u.setLayoutX(j * rectWidth);
				u.setLayoutY(i * rectHeight);
				row.add((Unit) u);
			}
			grid.add(row);
		}
	}
	
	/**
	 * checks which neighbor type it needs to look for
	 */
	public Map<Integer[], Unit> getNeighbors(int row, int col) {
		if(!toroidal) return getFiniteNeighbors(row, col);
		else return getToroidalNeighbors(row, col);
	}
	
	/**
	 * @param row
	 * @param col
	 * @return A map of the all local neighbor units to a block given the row and col
	 * <K, V> corresponds to <location, Unit>
	 */
	public Map<Integer[], Unit> getFiniteNeighbors(int row, int col) {
		Map<Integer[], Unit> map = new HashMap<>();
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
	 * returns a map of the neighbors with the
	 * key being the integer [row,col] and
	 * the value being the shape
	 * has a special case
	 */
	public Map<Integer[], Unit> getToroidalNeighbors(int row, int col) {
		Map<Integer[], Unit> map = new HashMap<>();
		Unit u;
		Integer[] place;
		for(int i = 0; i < rowMove.length; i++){
			int newRow = row + rowMove[i];
			int newCol = col + colMove[i];
			if(newRow < 0) newRow = rows() + newRow;
			if(newCol < 0) newCol = rows() + newCol;
			if(newRow >= rows()) newRow = newRow - rows();
			if(newCol >= cols()) newCol = newCol - cols();
				place = new Integer[] {newRow, newCol};
				u = grid.get(newRow).get(newCol);
				map.put(place, u);
		}
		return map;
	}
}
