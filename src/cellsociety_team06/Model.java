/**
 *Co-Written by all
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import subUnits.Alive;

import subUnits.Burning;
import subUnits.Burnt;

public abstract class Model {
	
	private Stage myStage;
	private Timeline animation;
	protected Group root;
//	private double initialRate;
//	private Slider speedSlide;
//	private ResourceBundle myResources;
	protected Grid curGrid, nextGrid;
	
	private final double minSimSpeed = 1;
	private final double maxSimSpeed = 35;
	
	
	//constructor
	public Model (Stage s, Timeline t, ResourceBundle r){
		myStage = s;
		animation = t;
		//myResources = r;
//		initialRate = animation.getCurrentRate();
		root = new Group();
	}
	
	//methods
	
	public Group getRoot(){
		return root;
	}
	
	public abstract void setNextScene(); //within the subclasses, there will be a current scene and next scene
	
	public abstract void reset();
	
	public abstract void updateGrid();
	
	public void step(){
		setNextScene();
	}
	
	protected void resetRoot(){
		root.getChildren().clear();
		root.getChildren().addAll(curGrid.getChildren());
	}
	
	private void stepMode() {
		animation.pause();	
	}
}