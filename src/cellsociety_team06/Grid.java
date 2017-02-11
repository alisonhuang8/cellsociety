package cellsociety_team06;


import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

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
	
	public int rows(){
		return rows;
	}
	
	public int cols(){
		return cols;
	}

	public Unit getUnit(int row, int col){
		return grid.get(row).get(col);
	}
	
	public void setUnit(int row, int col, Unit u){
		grid.get(row).set(col, u);
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
	
	public void swap(int rowA, int colA, int rowB, int colB){
		Unit a = grid.get(rowA).get(colA);
		Unit b = grid.get(rowB).get(colB);
		switchLayouts(a, b);
		grid.get(rowA).set(colA, b);
		grid.get(rowB).set(colB, a);		
	}
	
	private void switchLayouts(Unit a, Unit b){
		double x = a.getLayoutX();
		double y = a.getLayoutY();
		a.setLayoutX(b.getLayoutX());
		a.setLayoutY(b.getLayoutY());
		b.setLayoutX(x);
		b.setLayoutY(y);
		
	}
}
