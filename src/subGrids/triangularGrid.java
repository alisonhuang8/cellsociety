/**
 * Written by Gideon Pfeffer
 * Makes a grid of triangles
 */
package subGrids;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import Unit.Unit;
import cellsociety_team06.Grid;

public class triangularGrid extends Grid {
	private double length;
	private static final int[] UP_TRI_ROW = {-1, -1, 0, 0, 1, 1, 1, 1, 1, 0, 0, -1};
	private static final int[] UP_TRI_COL = {0, 1, 1, 2, 2, 1, 0, -1, -2, -2, -1, -1};
	private static final int[] DOWN_TRI_ROW = {1, 1, 0, 0, -1, -1, -1, -1, -1, 0, 0, 1};
	private static final int[] DOWN_TRI_COL = {0, -1, -1, -2, -2, -1, 0, 1, 2, 2, 1, 1};
	private static final Integer[] DEFAULT_NEIGHBORS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
	
	/**
	 * @param rows rows of the grid
	 * @param cols of the grid
	 * @param length length of the triangle
	 * sets the default neighbors
	 */
	public triangularGrid(int rows, int cols, int length) {
		super(rows, cols);
		this.length = length;
		neighborsAvailable = new ArrayList<>(Arrays.asList(DEFAULT_NEIGHBORS));
		maxNeighbors = 12;
		fillGrid();
	}
	
	/**
	 * determines whether a triangle needs to be pointing up or down
	 * and sets the points
	 */
	public void setPoly(Unit u, int row, int col){
		int orientation = 1;
		if((row + col) % 2 != 0){
			orientation = -1;
			setLayoutDown(u, row, col);
		}
		else{
			setLayoutUp(u, row, col);
		}
		u.getPoints().addAll(new Double[]{
				0.0, 0.0,
	            length, 0.0,
	            length/2.0, orientation * Math.pow(3, 0.5) * length/2.0});
	}
	
	/**
	 * sets the passed triangle to point down
	 */
	private void setLayoutDown(Unit u, int row, int col){
		u.setLayoutX(((double)(length * (col - 1))) / 2.0 + (double) length/2.0);
		u.setLayoutY(((double)((Math.pow(3, 0.5) * length))/2.0) * row + 2 * Math.pow(3, 0.5) * length/2);
	}
	
	/**
	 * sets the passed triangle to point up
	 */
	private void setLayoutUp(Unit u, int row, int col){
		u.setLayoutX(((double)(length * col)) / 2.0);
		u.setLayoutY(((double)((Math.pow(3, 0.5) * length))/2.0) * row + Math.pow(3, 0.5) * length/2);
	}
	
	/**
	 * checks which neighbor type it needs to look for
	 */
	public Map<Integer[], Unit> getNeighbors(int row, int col) {
		if(row + col % 2 != 0){
			rowMove = DOWN_TRI_ROW;
			colMove = DOWN_TRI_COL;
		}
		else{
			rowMove = UP_TRI_ROW;
			colMove = UP_TRI_COL;
		}
		if(!toroidal) return getFiniteNeighbors(row, col);
		else return getToroidalNeighbors(row, col);
	}
}
