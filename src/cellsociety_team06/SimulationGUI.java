package cellsociety_team06;

import java.util.ResourceBundle;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class SimulationGUI {

	private double minSimSpeed = 1;
	private double maxSimSpeed = 35;
	private double initialRate;
	
	private Slider speedSlide;

	private Timeline animation;
	private ResourceBundle myResources;
	
	public SimulationGUI(Timeline t, ResourceBundle r) {
		animation = t;
		initialRate = animation.getCurrentRate();
		myResources = r;
	}
	
	public Button createResetBtn(Model model){
		Button btn_reset = new Button(myResources.getString("ResetButton"));
		btn_reset.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg){
				speedSlide.setValue(minSimSpeed);
				animation.setRate(initialRate);
				animation.pause();
				model.reset();
			}
		});
		return btn_reset;
	}
	
	public Button createStartSimBtn(){		
		Button btn_start = new Button(myResources.getString("StartButton"));
		btn_start.setOnAction(new EventHandler<ActionEvent>() { //if the button is clicked
			public void handle(ActionEvent arg){
				animation.setRate(initialRate);
				animation.play();
			}
		});
		return btn_start;
	}
	
	public Button createPauseBtn(){
		Button btn_pause = new Button(myResources.getString("PauseButton"));
		btn_pause.setOnAction(new EventHandler<ActionEvent>() { //if the button is clicked
			public void handle(ActionEvent arg){
				animation.pause();
			}
		});
		return btn_pause;
	}
	
	public Button createHomeBtn(Scene homeScene, Model model, Stage s){
		Button btn_home = new Button(myResources.getString("HomeButton"));
		btn_home.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg){
				animation.setRate(initialRate);
				animation.stop();
				model.reset();
				s.setScene(homeScene);
			}
		}); 	
		return btn_home;
	}
	
	public Button createStepBtn(Model model) {
		Button btn_step = new Button(myResources.getString("StepButton"));
		btn_step.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg) {
				animation.pause();
				btn_step.setOnKeyPressed(new EventHandler<KeyEvent>() {
					public void handle(KeyEvent code) {
							model.updateGrid();
					}
				});
			}
		});
		return btn_step;
	}
	
	public Slider createSpeedSlider() {
		speedSlide = new Slider();
		speedSlide.setMin(minSimSpeed);
		speedSlide.setMax(maxSimSpeed);
		speedSlide.valueProperty().addListener(e -> {
			animation.setRate(speedSlide.getValue());
		});
		return speedSlide;
	}	
}
