package cellsociety_team06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import subUnits.Blank;

public abstract class Model {
	
	private Stage myStage;
	private Timeline animation;
	private Group root;
	
	private double initialRate;
	private Slider speedSlide;
	
	private final double minSimSpeed = 1;
	private final double maxSimSpeed = 35;
	
	
	//constructor
	public Model (Stage s, Timeline t){
		myStage = s;
		animation = t;
		myResources = r;
		initialRate = animation.getCurrentRate();
	}
	
	//methods
	
	public abstract Group getRoot(); //creates the scene with the new units
	
	public abstract void setNextScene(); //within the subclasses, there will be a current scene and next scene
	
	public abstract void reset();
	
	public abstract void updateGrid();
	
	public void step(){
		setNextScene();
	}
	
	public Button createHowToPlayBtn(String instr, Scene lastScene){ //pass in the text file of the rules and current scene to go back to
		
		Pane rulesRoot = new Pane();
		Scene rulesScene = new Scene(rulesRoot);
		
		Button btn_howToPlay = new Button("How to play");
		btn_howToPlay.setOnAction(new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent arg){
				
				Text tx_instr = new Text(instr);
				try (Scanner scanner = new Scanner(new File(instr))){
					while (scanner.hasNextLine()) {
						String line = scanner.nextLine() + "\n";
						tx_instr.setText(tx_instr.getText()+ line);
					}
					rulesRoot.getChildren().add(tx_instr);
					System.out.print(tx_instr);
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				Button btn_done = new Button("Done!");
				btn_howToPlay.setOnAction(new EventHandler<ActionEvent>() { 
					public void handle(ActionEvent arg){
						myStage.setScene(lastScene);
					}
				});
				
				rulesRoot.getChildren().addAll(tx_instr, btn_done);
				myStage.setScene(rulesScene);

			}
		});
		
		return btn_howToPlay;
	}
	
	public Button createResetBtn(){
		
		Button btn_reset = new Button("Reset");
		btn_reset.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg){
				animation.pause();
				reset();
			}
		});
		return btn_reset;
	}
	
	public Button createStartSimBtn(){
		
		Button btn_start = new Button("Start");
		btn_start.setOnAction(new EventHandler<ActionEvent>() { //if the button is clicked
			public void handle(ActionEvent arg){
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
	
	public Button createHomeBtn(Scene homeScene){
		
		Button btn_home = new Button("Return Home");
		btn_home.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg){
				animation.stop();
				reset();
				myStage.setScene(homeScene);
			}
		}); 
		
		return btn_home;
	}
	
	public Button createStepBtn() {
		Button btn_step = new Button("Step Through");
		btn_step.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg) {
				animation.pause();
				btn_step.setOnKeyPressed(new EventHandler<KeyEvent>() {
					public void handle(KeyEvent code) {
						//if(code == KeyCode.SPACE){
							updateGrid();
						//}
					}
				});
			}
		});
		return btn_step;
	}
	
	private void stepMode() {
		animation.pause();
		
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