package cellsociety_team06;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CellSociety extends Application {
	public static final int SIZE = 500;
	public static final Paint BACKGROUND = Color.WHITE;
	
	private Stage myStage;
	private Timeline animation = new Timeline();
	private Model currentModel;
	
	
	private Scene myHomeScene;
	
	private int FRAMES_PER_SECOND = 1;
	private int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private double SECOND_DELAY = 0.75 / FRAMES_PER_SECOND;

	private Button myLifeButton;
	private Button myFireButton;
	private Button myWatorButton;
	private Button mySegregationButton;
	private String fileName = "a";
	private String lifeFile = "lifeinfo.txt";
	private String fireFile = "fireinfo.txt";
	private String watorFile = "watorinfo.txt";
	private String segregationFile = "segregationinfo.txt";
	
	private BorderPane bp = new BorderPane();
	private HBox panel = new HBox();
	private Group root = new Group();
	private Scene myScene = new Scene(root, SIZE, SIZE, Color.WHITE);
	
	private Boolean hasBegun = false;
	
	private String fireInstr = "fireinstr.txt";
	
	
	public void start(Stage s) throws Exception {
		myStage = s;
		myHomeScene = homeScene(SIZE, SIZE, BACKGROUND);
		myStage.setScene(myHomeScene);
        myStage.setTitle("Home Screen");
        myStage.show();
	}
	
	private Scene homeScene(int width, int height, Paint background) {
		Pane homeSceneRoot = new StackPane();
		
		myLifeButton = createSimButton(homeSceneRoot,"Game of Life Simulation", lifeFile);
		myLifeButton.setTranslateY(-SIZE/4);
		
		myFireButton = createSimButton(homeSceneRoot, "Spreading Fire Simulation", fireFile);
		myFireButton.setTranslateY(-SIZE/8);
		
		myWatorButton = createSimButton(homeSceneRoot, "Predator Prey Simulation", watorFile);
		
		mySegregationButton = createSimButton(homeSceneRoot, "Segregation Simulation", segregationFile);
		mySegregationButton.setTranslateY(SIZE/8);
        
		myHomeScene = new Scene(homeSceneRoot, width, height, background);
		return myHomeScene;
	}
	
	private Scene infoScene(int width, int height, Paint background) {
		Pane infoRoot = new Pane();
		Scene myInfoScene = new Scene(infoRoot, SIZE, SIZE, background);			
		readFile(infoRoot);
		buttonSetup(infoRoot);
		myStage.setScene(myInfoScene);
		return myInfoScene;
	}

	private void buttonSetup(Pane infoRoot) {
		Button btn_play = new Button("Let's Play");
		btn_play.setOnAction(new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent arg){
				createModelAndFrames();	
			}
		});
		btn_play.setLayoutX(SIZE/2);
		btn_play.setLayoutY(4*SIZE/5);
		infoRoot.getChildren().add(btn_play);
		
		Button btn_back = new Button("Go Back");
		btn_back.setOnAction(new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent arg){
				try {
					start(myStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btn_back.setLayoutX(3*SIZE/10);
		btn_back.setLayoutY(4*SIZE/5);
		infoRoot.getChildren().add(btn_back);
	}

	private void readFile(Pane infoRoot) {
		Text simulationInfo = new Text(SIZE/20, SIZE/20, "");	
		simulationInfo.setWrappingWidth(SIZE*9/10);
		try (Scanner scanner = new Scanner(new File(fileName))){
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine() + "\n";
				simulationInfo.setText(simulationInfo.getText()+ line);
			}
			infoRoot.getChildren().add(simulationInfo);	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void createModelAndFrames(){
		Group r = (Group) myScene.getRoot();
		r.getChildren().clear();
		bp.getChildren().clear();
		panel.getChildren().clear();
		
		if (fileName.equals(lifeFile)){
			currentModel = new lifeModel(myStage, animation, SIZE - 50, SIZE - 50);
		} else if (fileName.equals(fireFile)){
			currentModel = new fireModel(myStage, animation, SIZE - 50, SIZE - 50);
		} else if (fileName.equals(watorFile)){
			currentModel = new watorModel(myStage, animation, SIZE - 50, SIZE - 50);
		} else if (fileName.equals(segregationFile)){
			FRAMES_PER_SECOND = 8;
			currentModel = new segregationModel(myStage, animation, SIZE - 50, SIZE - 50);
		}
		
		currentModel.reset();
		
		KeyFrame frame = new KeyFrame(Duration.millis(1000/FRAMES_PER_SECOND),
				e -> step(SECOND_DELAY, myStage));
		if (animation != null){
			animation.stop();
			
		}
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
		
		
		
		myStage.setScene(myScene);
		myStage.show();
		myStage.setTitle("Simulation");


			setButtons();
			root.getChildren().add(bp);
			
			Group rt = currentModel.getRoot();
			bp.setCenter(rt);

		
	}
	
	private void step (double elapsedTime, Stage stage) { 
		currentModel.step();
		
	}
	
	private void setButtons(){
		panel.getChildren().addAll(currentModel.createPauseBtn(), currentModel.createStartSimBtn(), currentModel.createStepBtn(),
				currentModel.createHomeBtn(myHomeScene), currentModel.createResetBtn(), currentModel.createSpeedSlider());
		bp.setTop(panel);
	}
	
	private void setInfoScene() {
		Scene myInfoScene = infoScene(SIZE,SIZE, BACKGROUND);
		myStage.setTitle("Info Screen");
		myStage.show();
	}
	
	private Button createSimButton(Pane root, String label, String nextFile) {
		Button simButton = new Button(label); 
    	simButton.setOnAction(e-> ButtonClicked(e, nextFile));
    	root.getChildren().add(simButton);
    	return simButton;
	}
	
    public void ButtonClicked(ActionEvent e, String nextFile) {
        if (e.getSource() == myLifeButton || e.getSource() == myFireButton || 
        	e.getSource() == myWatorButton || e.getSource() == mySegregationButton) {
    		fileName = nextFile;
        	setInfoScene();
        }
    }
    
	public static void main (String[] args) {
	        launch(args);
	    }
}