package cellsociety_team06;


import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public abstract class Grid{
	protected List<List<Unit>> grid;
	private int rows;
	private int cols;
	
	public Grid(int rows, int cols){
		grid = new ArrayList<>();
		this.rows = rows;
		this.cols = cols;
	}
	
	public abstract void fillGrid();
	
	public abstract Map<Integer[], Unit> getNeighbors(int row, int col);
	
	public int rows(){
		return rows;
	}
	
	public int cols(){
		return cols;
	}

	public Unit getUnit(int row, int col){
		return grid.get(col).get(row);
	}
	
	public void setUnit(int row, int col, Unit u){
		grid.get(col).set(row, u);
	}
	
	public List<Unit> getChildren(){
		List<Unit> list = new ArrayList<>();
		for(int i = 0; i < rows(); i++){
			for(int j = 0; j < cols(); j++){
				list.add(grid.get(i).get(j));
			}
		}
		return list;
	}
	
	public List<List<Unit>> getGrid(){
		return grid;
	}
	
	public void setGrid(List<List<Unit>> newGrid){
		grid = newGrid;
	}
}
