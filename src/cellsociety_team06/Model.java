/**
 * Written by Gideon Pfeffer
 */
package cellsociety_team06;
import javafx.scene.Group;

public abstract class Model {
	protected Group root;
	protected Grid curGrid, nextGrid, initialGrid;
	
	/**
	 * sets the grids and makes a new root
	 */
	public Model(Grid curr, Grid next, Grid init){
		curGrid = curr;
		nextGrid = next;
		initialGrid = init;
		root = new Group();
		resetRoot();
	}
	
	/**
	 * returns the root
	 */
	public Group getRoot(){
		return root;
	}
	
	/**
	 * resets the curgrid to whatever the initial grid was
	 */
	public void reset() {
		root.getChildren().clear();
		for (int i=0; i<initialGrid.rows(); i++){
			for (int j=0; j<initialGrid.cols(); j++){
				curGrid.setUnit(i, j, initialGrid.getUnit(i, j));
			}
		}
		resetRoot();
	}

	/**
	 * ticks based off of CA's rules
	 */
	public abstract void updateGrid();
	
	/**
	 * runs through a tick of the CA
	 */
	public void step(){
		updateGrid();
	}
	
	/**
	 * resets the root to the curGrid
	 */
	protected void resetRoot(){
		root.getChildren().clear();
		root.getChildren().addAll(curGrid.getChildren());
	}
	
	/**
	 * returns the number of a certain unit Unit A from models
	 */
	public abstract int getUnitA();
	
	/**
	 * returns the number of a certain unit Unit B from models
	 */
	public abstract int getUnitB();
}