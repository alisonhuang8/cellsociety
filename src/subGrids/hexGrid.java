/**
 * Written by Gideon Pfeffer
 * Makes a grid of hexagons
 */

package subGrids;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import Unit.Unit;
import cellsociety_team06.Grid;

public class hexGrid extends Grid {
	private static final int[] EVEN_ROW_COL_MOVE = {0, 0, 0, 0, -1, -1};
	private static final int[] ODD_ROW_COL_MOVE = {0, 1, 1, 0, 0, 0};
	private static final Integer[] DEFAULT_NEIGHBORS = {0, 1, 2, 3, 4, 5};
	private double length;
	
	/**
	 * @param rows takes in the number of rows
	 * @param cols takes in the number of cols
	 * @param length takes in the length of the hexagon side
	 * also sets the default neighbors
	 */
	public hexGrid(int rows, int cols, int length) {
		super(rows, cols);
		rowMove = new int[] {-2, -1, 1, 2, 1, -1};
		neighborsAvailable = new ArrayList<>(Arrays.asList(DEFAULT_NEIGHBORS));
		maxNeighbors = 6;
		this.length = length;
		fillGrid();
	}
	
	/**
	 * sets the x,y of all the hexagons
	 * also sets the points
	 */
	public void setPoly(Unit u, int row, int col){
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
	 * checks which neighbor type it needs to use
	 */
	public Map<Integer[], Unit> getNeighbors(int row, int col) {
		if(row % 2 == 0) colMove = EVEN_ROW_COL_MOVE;
		else colMove = ODD_ROW_COL_MOVE;
		if(!toroidal) return getFiniteNeighbors(row, col);
		else return getToroidalNeighbors(row, col);
	}

}
