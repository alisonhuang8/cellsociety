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
	protected List<Integer> neighborsAvailable;
	protected int maxNeighbors;
	private int rows, cols;
	protected boolean toroidal;
	
	/**
	 * Makes a grid of Units
	 * @param rows stores the numbers of rows for the grid 
	 * @param cols stores the number of columns for the grid
	 */
	public Grid(int rows, int cols){
		grid = new ArrayList<>();
		this.rows = rows;
		this.cols = cols;
	}
	
	/**
	 * Fills the grids with their respective shapes
	 */
	public void fillGrid(){
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
	
	public abstract void setPoly(Unit u, int row, int col);
	
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
	 * Allows the user to set new
	 * neighbor specs
	 */
	public void setNeighbors(List<Integer> list){
		if(list.size() > maxNeighbors){
			throw new IllegalArgumentException();
		}
		neighborsAvailable = list;
	}
	
	/**
	 * sets the boundary condition to be toroidal
	 */
	public void makeToroidal(){
		toroidal = true;
	}
	
	/**
	 * resets toroidal to false
	 */
	public void undoToroidal(){
		toroidal = false;
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
		for(int i:neighborsAvailable){
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
		for(int i:neighborsAvailable){
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
