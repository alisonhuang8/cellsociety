/**
 * Written by Gideon Pfeffer
 */

package cellsociety_team06;

import javafx.scene.Group;

public abstract class Model {
	protected Group root;
	protected Grid curGrid, nextGrid, initialGrid;
	
	public Model (){
		root = new Group();
	}
	
	public Model(Grid curr, Grid next, Grid init){
		this();
		curGrid = curr;
		nextGrid = next;
		initialGrid = init;
		root = new Group();
		resetRoot();
	}
	
	//methods
	public Group getRoot(){
		return root;
	}
	
	public void reset() {
		root.getChildren().clear();
		for (int i=0; i<initialGrid.rows(); i++){
			for (int j=0; j<initialGrid.cols(); j++){
				curGrid.setUnit(i, j, initialGrid.getUnit(i, j));
			}
		}
		resetRoot();
	}
	
	public abstract void setNextScene();
	
	public abstract void updateGrid();
	
	public void step(){
		setNextScene();
	}
	
	protected void resetRoot(){
		root.getChildren().clear();
		root.getChildren().addAll(curGrid.getChildren());
	}
	
	public abstract int getType1Units();
	
	public abstract int getType2Units();
}