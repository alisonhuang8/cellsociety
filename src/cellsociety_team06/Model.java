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
		System.out.println(curGrid.getInstances(new Alive()).size());
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
		System.out.print(curGrid.getInstances(new Alive()).size() + " ");
		System.out.println("reseting root");
		root.getChildren().addAll(curGrid.getChildren());
	}
}