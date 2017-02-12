/**
 * Written by Gideon Pfeffer
 * Is the super class for all grid models (rectangular, triangular, and hexagonal)
 */

package cellsociety_team06;


import java.util.List;
import java.util.Map;

import Unit.Unit;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Grid{
	protected int[] rowMove, colMove;
	protected List<List<Unit>> grid;
	private int rows;
	private int cols;
	protected boolean toroidal;
	
	/**
	 * Makes a grid of Units
	 * @param rows stores the numbers of rows for the grid 
	 * @param cols cols stroes the number of columns for the grid
	 */
	public Grid(int rows, int cols){
		grid = new ArrayList<>();
		this.rows = rows;
		this.cols = cols;
	}
	
	/**
	 * Fills the grids with their respective shapes
	 */
	public abstract void fillGrid();
	
	/**
	 * @param row
	 * @param col
	 * @return A map of the all local neighbor units to a block given the row and col
	 * <K, V> corresponds to <location, Unit>
	 */
	public abstract Map<Integer[], Unit> getNeighbors(int row, int col);
	
	/**
	 * @param U unit passing in
	 * @return all <locations, Units> of units with the same type as U in a map. 
	 */
	public Map<Integer[], Unit> getInstances(Unit U){
		Map<Integer[], Unit> map = new HashMap<>();
		for(int i = 0; i < rows(); i++){
			for(int j = 0; j < cols(); j++){
				Unit unit = grid.get(i).get(j);
				if(unit.getState() == U.getState()){
					Integer[] place = new Integer[]{i, j};
					map.put(place, unit);
				}
			}
		}
		return map;
	}
	
	/**
	 * @return rows of the grid
	 */
	public int rows(){
		return rows;
	}
	
	/**
	 * @return cols of the grid
	 */
	public int cols(){
		return cols;
	}

	/**
	 * @return the unit at a given row and column
	 */
	public Unit getUnit(int row, int col){
		return grid.get(row).get(col);
	}
	
	/**
	 * Sets the unit u passed in at the given row and col
	 */
	public void setUnit(int row, int col, Unit u){
		grid.get(row).set(col, u);
	}
	
	/**
	 * @return a list of all units stored in the grid
	 */
	public List<Unit> getChildren(){
		List<Unit> list = new ArrayList<>();
		for(int i = 0; i < rows(); i++){
			for(int j = 0; j < cols(); j++){
				list.add(grid.get(i).get(j));
			}
		}
		return list;
	}
	
	/**
	 * @return the grid
	 */
	public List<List<Unit>> getGrid(){
		return grid;
	}
	
	/**
	 * @param newGrid set the grid
	 */
	public void setGrid(List<List<Unit>> newGrid){
		grid = newGrid;
	}
	
	/**
	 * 
	 * @param rowA row of Unit A
	 * @param colA col of Unit A
	 * @param rowB row of Unit B
	 * @param colB col of Unit B
	 * swaps the locations of the units on the grid and in space (x,y)
	 */
	public void swap(int rowA, int colA, int rowB, int colB){
		Unit a = grid.get(rowA).get(colA);
		Unit b = grid.get(rowB).get(colB);
		switchLayouts(a, b);
		grid.get(rowA).set(colA, b);
		grid.get(rowB).set(colB, a);		
	}
	
	/**
	 * Swaps the x and y coordinates of two given units
	 */
	private void switchLayouts(Unit a, Unit b){
		double x = a.getLayoutX();
		double y = a.getLayoutY();
		a.setLayoutX(b.getLayoutX());
		a.setLayoutY(b.getLayoutY());
		b.setLayoutX(x);
		b.setLayoutY(y);
	}
	
	/**
	 * Allows the user to set new
	 * neighbor arguments
	 * throws an illegal argument exception if
	 * the arrays aren't the same length
	 */
	public void setNeighbors(int[] rowMoves, int[] colMoves){
		if(rowMoves.length != colMoves.length){
			throw new IllegalArgumentException();
		}
		this.rowMove = rowMoves;
		this.colMove = colMoves;
	}
	
	/**
	 * sets the boundary condition to be toroidal
	 */
	public void makeToroidal(){
		toroidal = true;
	}
}
