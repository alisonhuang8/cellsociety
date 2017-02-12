/**
 * Written by Gideon Pfeffer
 */

package cellsociety_team06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;
import java.util.Scanner;


import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import subUnits.Alive;

public abstract class Model {
	protected Group root;
	protected Grid curGrid, nextGrid;
	
	public Model (){
		root = new Group();
	}
	
	public Model(Grid curr, Grid next){
		this();
		curGrid = curr;
		nextGrid = next;
		root = new Group();
		resetRoot();
	}
	
	//methods
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
	
	public abstract int getType1Units();
	
	public abstract int getType2Units();
}