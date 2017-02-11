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
	
//	public Button createResetBtn(){
//		Button btn_reset = new Button("Reset");
//		btn_reset.setOnAction(new EventHandler<ActionEvent>() {
//			public void handle(ActionEvent arg){
//				speedSlide.setValue(minSimSpeed);
//				animation.setRate(initialRate);
//				animation.pause();
//				reset();
//			}
//		});
//		return btn_reset;
//	}
	
//	public Button createStartSimBtn(){
//		
//		Button btn_start = new Button("Start");
//		btn_start.setOnAction(new EventHandler<ActionEvent>() { //if the button is clicked
//			public void handle(ActionEvent arg){
//				animation.setRate(initialRate);
//				animation.play();
//			}
//		});
//		
//		return btn_start;
//	}
	
//	public Button createPauseBtn(){
//		Button btn_pause = new Button("Pause");
//		btn_pause.setOnAction(new EventHandler<ActionEvent>() { //if the button is clicked
//			public void handle(ActionEvent arg){
//				animation.pause();
//			}
//		});
//		
//		return btn_pause;
//		
//	}
	
//	public Button createHomeBtn(Scene homeScene){
//		
//		Button btn_home = new Button("Return Home");
//		btn_home.setOnAction(new EventHandler<ActionEvent>() {
//			public void handle(ActionEvent arg){
//				animation.setRate(initialRate);
//				animation.stop();
//				reset();
//				myStage.setScene(homeScene);
//			}
//		}); 
//		
//		return btn_home;
//	}
//	
//	public Button createStepBtn() {
//		Button btn_step = new Button("Step Through");
//		btn_step.setOnAction(new EventHandler<ActionEvent>() {
//			public void handle(ActionEvent arg) {
//				animation.pause();
//				btn_step.setOnKeyPressed(new EventHandler<KeyEvent>() {
//					public void handle(KeyEvent code) {
//							updateGrid();
//					}
//				});
//			}
//		});
//		return btn_step;
//	}
	
	private void stepMode() {
		animation.pause();	
	}

//	public Slider createSpeedSlider() {
//		speedSlide = new Slider();
//		speedSlide.setMin(minSimSpeed);
//		speedSlide.setMax(maxSimSpeed);
//		speedSlide.valueProperty().addListener(e -> {
//			animation.setRate(speedSlide.getValue());
//		});
//		return speedSlide;
//	}
//	
}