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
	
	public SimulationGUI(Timeline t) {
		animation = t;
		initialRate = animation.getCurrentRate();
	}
	
	public Button createResetBtn(Model model){
		Button btn_reset = new Button("Reset");
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
		
		Button btn_start = new Button("Start");
		btn_start.setOnAction(new EventHandler<ActionEvent>() { //if the button is clicked
			public void handle(ActionEvent arg){
				animation.setRate(initialRate);
				animation.play();
			}
		});
		
		return btn_start;
	}
	
	public Button createPauseBtn(){
		Button btn_pause = new Button("Pause");
		btn_pause.setOnAction(new EventHandler<ActionEvent>() { //if the button is clicked
			public void handle(ActionEvent arg){
				animation.pause();
			}
		});
		
		return btn_pause;
		
	}
	
	public Button createHomeBtn(Scene homeScene, Model model, Stage s){
		
		Button btn_home = new Button("Return Home");
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
		Button btn_step = new Button("Step Through");
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
