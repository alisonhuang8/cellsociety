package cellsociety_team06;

import java.util.ResourceBundle;
import java.util.Scanner;
import Models.fireModel;
import Models.lifeModel;
import Models.segregationModel;
import Models.watorModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CellSociety extends Application {
	public static final int SIZE = 500;
	public static final Paint BACKGROUND = Color.WHITE;

	private Stage myStage;
	private Timeline animation = new Timeline();
	private Model currentModel;

	private Scene myHomeScene;
	private Scene mySizeScene;
	private Scene myShapeScene;

	private int FRAMES_PER_SECOND = 1;
	private double SECOND_DELAY = 0.75 / FRAMES_PER_SECOND;
	private int gridSize = 0;
	
	private String fileName = "b";
	private String lifeFile = "lifeinfo.txt";
	private String fireFile = "fireinfo.txt";
	private String watorFile = "watorinfo.txt";
	private String segregationFile = "segregationinfo.txt";
	private String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private String shape;
	String[] sizeButtons = {"SmallGrid", "MediumGrid", "LargeGrid"};
	String[][] simulations = {{"GoLSimulation", "lifeinfo.txt"}, {"SpreadingFireSimulation", "fireinfo.txt"},
							  {"PredatorPreySimulation", "watorinfo.txt"},{"SegregationSimulation", "segregationinfo.txt"}};

	private HBox panel = new HBox();
	private Group root = new Group();
	private Scene myScene = new Scene(root, SIZE, SIZE, Color.WHITE);
	private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");

	SceneSetup setup;
	SimulationGUI simSetup;
			
	public void start(Stage s) throws Exception {	
		myStage = s;
		setup = new SceneSetup(SIZE, SIZE, myResources, myStage);
		simSetup = new SimulationGUI(animation);
		mySizeScene = askSizeScene();
		
		myShapeScene = shapeScene(SIZE,SIZE,BACKGROUND);
		myHomeScene = homeScene(SIZE, SIZE, BACKGROUND);
		myStage.setScene(myHomeScene);
		myStage.setTitle(myResources.getString("HomeTitle"));
		myStage.show();
		setAnimation();
	}
	
	private Scene homeScene(int width, int height, Paint background) {
		Pane homeSceneRoot = new FlowPane(Orientation.VERTICAL);
		for (String[] simulation: simulations) {
			Button myButton = setup.createSimButton(homeSceneRoot, myResources.getString(simulation[0]));			
			myButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg) {
					fileName = simulation[1];
					setInfoScene();
				}
			});
		}
		myHomeScene = new Scene(homeSceneRoot, width, height, background);
		return myHomeScene;
	}
		
	private void setInfoScene() {
		Scene myInfoScene = infoScene(SIZE, SIZE, BACKGROUND);
		myStage.setTitle(myResources.getString("InfoTitle"));
		myStage.show();
	}
	
	private Scene infoScene(int width, int height, Paint background) {
		Pane infoRoot = new Pane();
		Scene myInfoScene = new Scene(infoRoot, SIZE, SIZE, background);
		infoRoot.getChildren().add(setup.readFile(infoRoot,fileName));
		setup.buttonPlay(infoRoot, myShapeScene);		
		setup.buttonBack(infoRoot, myHomeScene);
		myStage.setScene(myInfoScene);
		return myInfoScene;
	}
	
	private Scene shapeScene(int width, int height, Paint background) {
		Group shapeRoot = new Group();
		myShapeScene = new Scene(shapeRoot, SIZE, SIZE, BACKGROUND);
		shape = setup.createShapeButtons(shapeRoot, mySizeScene);
		return myShapeScene;
	}
	
	private Scene askSizeScene(){	
		Group sizeSceneRoot = new Group();
		mySizeScene = new Scene(sizeSceneRoot, SIZE, SIZE, BACKGROUND);
		setup.createSizeLabel(sizeSceneRoot, myResources, SIZE, SIZE);
		VBox sizes = setup.createBox(sizeSceneRoot, SIZE, SIZE);		
		for (String button: sizeButtons) {
			Button btn = setup.createSizeButton(sizeSceneRoot, button);
			btn.setOnAction(f -> SizeClicked(f,button));
			sizes.getChildren().add(btn);
		}
		mySizeScene.setRoot(sizeSceneRoot);
		return mySizeScene;
	}
	
	public void SizeClicked(ActionEvent f, String size){
		if (((Button) f.getSource()).getText().equals("SmallGrid")){
			gridSize = 1;	
			
		} else if (((Button) f.getSource()).getText().equals("MediumGrid")){
			gridSize = 2;
			
		} else if (((Button) f.getSource()).getText().equals("LargeGrid")){
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
		
		panel.getChildren().addAll(simSetup.createStartSimBtn(), simSetup.createPauseBtn(), simSetup.createStepBtn(currentModel), 
				simSetup.createHomeBtn(myHomeScene, currentModel, myStage), simSetup.createResetBtn(currentModel), simSetup.createSpeedSlider());
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