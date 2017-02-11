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
}
