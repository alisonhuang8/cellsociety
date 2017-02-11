/**
 * Written by Gideon Pfeffer
 */

package cellsociety_team06;

import javafx.scene.Group;

public abstract class Model {
	protected Group root;
	protected Grid curGrid, nextGrid;
	
	public Model (){
		root = new Group();
	}
	
	public Group getRoot(){
		return root;
	}
	
	public abstract void setNextScene();
	
	public abstract void reset();
	
	public abstract void updateGrid();
	
	public void step(){
		setNextScene();
	}
	
	protected void resetRoot(){
		root.getChildren().clear();
		root.getChildren().addAll(curGrid.getChildren());
	}
}