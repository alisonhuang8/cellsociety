package cellsociety_team06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
	private double SECOND_DELAY = 0.75 / FRAMES_PER_SECOND;
	private int gridSize = 0;

	private Button myLifeButton;
	private Button myFireButton;
	private Button myWatorButton;
	private Button mySegregationButton;
	private Button btn_small;
	private Button btn_medium;
	private Button btn_large;
	
	private String fileName = "b";
	private String lifeFile = "lifeinfo.txt";
	private String fireFile = "fireinfo.txt";
	private String watorFile = "watorinfo.txt";
	private String segregationFile = "segregationinfo.txt";
	private String DEFAULT_RESOURCE_PACKAGE = "resources/";

	private HBox panel = new HBox();
	private Group root = new Group();
	private Scene myScene = new Scene(root, SIZE, SIZE, Color.WHITE);
	private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");

	SceneSetup setup = new SceneSetup();
			
	public void start(Stage s) throws Exception {
		myStage = s;
		myHomeScene = homeScene(SIZE, SIZE, BACKGROUND);
		myStage.setScene(myHomeScene);
		myStage.setTitle(myResources.getString("HomeTitle"));
		myStage.show();
		setAnimation();
	}
	
	private Scene homeScene(int width, int height, Paint background) {
		Pane homeSceneRoot = new FlowPane(Orientation.VERTICAL);

		myLifeButton = setup.createSimButton(homeSceneRoot, myResources.getString("GoLSimulation"), SIZE);
		myLifeButton.setOnAction(e -> simButtonClicked(e, lifeFile));

		myFireButton = setup.createSimButton(homeSceneRoot, myResources.getString("SpreadingFireSimulation"), SIZE);
		myFireButton.setOnAction(e -> simButtonClicked(e, fireFile));

		myWatorButton = setup.createSimButton(homeSceneRoot, myResources.getString("PredatorPreySimulation"), SIZE);
		myWatorButton.setOnAction(e -> simButtonClicked(e, watorFile));

		mySegregationButton = setup.createSimButton(homeSceneRoot, myResources.getString("SegregationSimulation"), SIZE);
		mySegregationButton.setOnAction(e -> simButtonClicked(e, segregationFile));

		myHomeScene = new Scene(homeSceneRoot, width, height, background);
		return myHomeScene;
	}
	
	private void simButtonClicked(ActionEvent e, String nextFile) {
		if (e.getSource() == myLifeButton || e.getSource() == myFireButton || e.getSource() == myWatorButton
				|| e.getSource() == mySegregationButton) {
			fileName = nextFile;
			setInfoScene();	
		}
	}
	
	private void setInfoScene() {
		Scene myInfoScene = infoScene(SIZE, SIZE, BACKGROUND);
		myStage.setTitle(myResources.getString("InfoTitle"));
		myStage.show();
	}
	
	private Scene infoScene(int width, int height, Paint background) {
		Pane infoRoot = new Pane();
		Scene myInfoScene = new Scene(infoRoot, SIZE, SIZE, background);
		infoRoot.getChildren().add(setup.readFile(infoRoot,fileName, SIZE, SIZE));
		Button btn_play = setup.buttonPlay(infoRoot, SIZE, SIZE);
		btn_play.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg) {
				askSizeScene();
			}
		});
		Button btn_back = setup.buttonBack(infoRoot, SIZE, SIZE, myResources);
		btn_back.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg) {
				try {
					
					start(myStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		myStage.setScene(myInfoScene);
		return myInfoScene;
	}
	
	private void askSizeScene(){
		Group sizeSceneRoot = new Group();		
		setup.createSizeLabel(sizeSceneRoot, SIZE, SIZE, myResources);
		
		VBox sizes = setup.createSizeBox(sizeSceneRoot, SIZE, SIZE);
		btn_small = setup.createSizeButton(sizeSceneRoot, myResources.getString("SmallGrid"));
		btn_small.setOnAction(f -> SizeClicked(f));
		
		btn_medium = setup.createSizeButton(sizeSceneRoot,myResources.getString("MediumGrid"));
		btn_medium.setOnAction(f -> SizeClicked(f));
		
		btn_large = setup.createSizeButton(sizeSceneRoot,myResources.getString("LargeGrid"));
		btn_large.setOnAction(f -> SizeClicked(f));
		sizes.getChildren().addAll(btn_small, btn_medium, btn_large);

		myScene.setRoot(sizeSceneRoot);
		myStage.setScene(myScene);
		myStage.show();
		myStage.setTitle(myResources.getString("GridSize"));
	}
	
	public void SizeClicked(ActionEvent f){
		if (f.getSource() == btn_small){
			gridSize = 1;	
			
		} else if (f.getSource() == btn_medium){
			gridSize = 2;
			
		} else if (f.getSource() == btn_large){
			gridSize = 3;
			
		}
		createModelAndFrames();
	}
	
	private void createModelAndFrames() {
		root.getChildren().clear();
		BorderPane bp = new BorderPane();
		panel.getChildren().clear();

		if (fileName.equals(lifeFile)) {
			currentModel = new lifeModel(myStage, animation, myResources, SIZE - 50, SIZE - 50, gridSize);
		} else if (fileName.equals(fireFile)) {
			currentModel = new fireModel(myStage, animation, myResources, SIZE - 50, SIZE - 50, gridSize);
		} else if (fileName.equals(watorFile)) {
			currentModel = new watorModel(myStage, animation, myResources, SIZE - 50, SIZE - 50, gridSize);
		} else if (fileName.equals(segregationFile)) {
			currentModel = new segregationModel(myStage, animation, myResources, SIZE - 50, SIZE - 50, gridSize);
		}	

		myScene.setRoot(root);
		myStage.setScene(myScene);
		myStage.setTitle(myResources.getString("SimulationTitle"));
		
		panel.getChildren().addAll(currentModel.createStartSimBtn(), currentModel.createPauseBtn(), currentModel.createStepBtn(), 
				currentModel.createHomeBtn(myHomeScene), currentModel.createResetBtn(), currentModel.createSpeedSlider());
		bp.setTop(panel);
		root.getChildren().add(bp);
		Group rt = currentModel.getRoot();
		bp.setCenter(rt);

	}

	private void setAnimation() {
		KeyFrame frame = new KeyFrame(Duration.millis(1000 / FRAMES_PER_SECOND), e -> step(SECOND_DELAY, myStage));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	private void step(double elapsedTime, Stage stage) {
		if (currentModel != null) {
			currentModel.step();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
} 