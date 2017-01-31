package cellsociety_team06;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public abstract class Model {
	
	private CellSociety cs = new CellSociety();
	
	private Unit[][] currentGrid = new Unit[10][10];
	private Unit[][] nextGrid = new Unit[10][10];
	
	public abstract Scene getNextScene(); //creates the scene with the new units
	
	public abstract void setNextScene(); //within the subclasses, there will be a current scene and next scene
	
	public abstract void getRules();
	
	private Unit[][] getCurrentGrid(){
		return currentGrid;
	}
	
	private Unit[][] getNextGrid(){
		return nextGrid;
	}
	
	public Button createHowToPlayBtn(String instr){ //instructions for this game read in by a scanner
		
		Button btn_howToPlay = new Button("How to play");
		btn_howToPlay.setOnAction(new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent arg){
				
				
				
				Button btn_back = new Button("Done");
				btn_howToPlay.setOnAction(new EventHandler<ActionEvent>() { 
					public void handle(ActionEvent arg){
						
					}
				});
				
				Label lb_instr = new Label(instr);
				Group root = new Group();
				root.getChildren().add(lb_instr);
				cs.myStage.setScene(new Scene(root));

			}
		});
		
		return btn_howToPlay;
	}
	
	public Button createStartSimBtn(){
		
		Button btn_start = new Button("Start Simulation");
		btn_start.setOnAction(new EventHandler<ActionEvent>() { //if the button is clicked
			public void handle(ActionEvent arg){
				animation.play();
			}
		});
		
		return btn_start;
	}
	
}
