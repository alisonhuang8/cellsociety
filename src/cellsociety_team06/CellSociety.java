/**
 * Written by Faith Rodriguez
 * Creates scenes and controls timeline; runs the simulations
 */

package cellsociety_team06;

import java.util.Arrays;
import java.util.List;

import java.util.ResourceBundle;
import Models.fireModel;
import Models.lifeModel;
import Models.segregationModel;
import Models.sugarModel;
import Models.watorModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
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
	private int SIZE = 500;
	private int graphHeight = 100;
	private Paint BACKGROUND = Color.WHITE;
	private int height = SIZE + graphHeight;
	private int width = SIZE + graphHeight*2;

	private Stage myStage;
	private Timeline animation = new Timeline();
	private Model currentModel;

	private Scene myHomeScene;
	private Scene mySizeScene;
	private Scene myShapeScene;
	private Scene myBoundaryScene;
	private Scene myInputScene;
	private Scene myNeighborScene;

	private int FRAMES_PER_SECOND = 1;
	private double SECOND_DELAY = 0.75 / FRAMES_PER_SECOND;
	
	//parameters needed by the grid generator
	private int simType = 1;
	private int unitShape = 1;
	private int gridSize = 1;
	private int neighborConfig;
	private int boundaryStyle = 1;
	private int inputStyle = 1;

	private String fileName = "";
	private String lifeFile = "lifeinfo.txt";
	private String fireFile = "fireinfo.txt";
	private String watorFile = "watorinfo.txt";
	private String segregationFile = "segregationinfo.txt";
	private String sugarFile = "sugarinfo.txt";
	private String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private String[] sizeButtons = {"SmallGrid", "MediumGrid", "LargeGrid"};
	private String[] shapes = {"SquareShape", "TriangleShape", "HexagonShape"};
	private String[] neighbors = {"All", "Sides", "Corners", "Horizontal","Verticle"};
	private String[] boundaryTypes = {"NormalBoundary","ToroidalBoundary"};
	private String[] input = {"Read", "Random"};
	private String[][] simulations = {{"GoLSimulation", "lifeinfo.txt"}, {"SpreadingFireSimulation", "fireinfo.txt"},
							  {"PredatorPreySimulation", "watorinfo.txt"},{"SegregationSimulation", "segregationinfo.txt"},
							  {"SugarSimulation","sugarinfo.txt"}};

	private HBox panel = new HBox();
	private Group root = new Group();
	private Scene myScene = new Scene(root, width, height, Color.WHITE);
	private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");
	private LineChart<Number, Number> popGraph;
	
	SceneSetup setup;
	SimulationGUI simSetup;
			
	public void start(Stage s) throws Exception {	
		myStage = s;
		setup = new SceneSetup(width, height, myResources, myStage);
		initializeScenes();
		myStage.setScene(myHomeScene);
		myStage.setTitle(myResources.getString("HomeTitle"));
		myStage.show();
		setAnimation();
		simSetup = new SimulationGUI(animation, myResources);
	}
	private void initializeScenes() {
		mySizeScene = askSizeScene();
		myInputScene = createScene(width,height,BACKGROUND, mySizeScene, "InputChoice", input);
		myBoundaryScene = createScene(width,height,BACKGROUND, myInputScene, "BoundaryChoice", boundaryTypes);
		myNeighborScene = createScene(width,height,BACKGROUND, myBoundaryScene, "ChooseNeighbors", neighbors);
		myShapeScene = createScene(width,height,BACKGROUND, myNeighborScene, "ChooseCellShape", shapes);
		myHomeScene = homeScene(width, height, BACKGROUND);
	}
	private Scene homeScene(int width, int height, Paint background) {
		Pane homeSceneRoot = new FlowPane(Orientation.VERTICAL);
		for (String[] simulation: simulations) {
			Button myButton = setup.createSimButton(homeSceneRoot, myResources.getString(simulation[0]));			
			myButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg) {
					fileName = simulation[1];
					simType = Arrays.asList(simulations).indexOf(simulation) + 1;
					setInfoScene();
				}
			});
		}
		myHomeScene = new Scene(homeSceneRoot, width, height, background);
		return myHomeScene;
	}
		
	private void setInfoScene() {
		Scene myInfoScene = infoScene(width, height, BACKGROUND);
		myStage.setTitle(myResources.getString("InfoTitle"));
		myStage.show();
	}
	
	private Scene infoScene(int width, int height, Paint background) {
		Pane infoRoot = new Pane();
		Scene myInfoScene = new Scene(infoRoot, width, height, background);
		infoRoot.getChildren().add(setup.readFile(infoRoot,fileName));
		setup.buttonPlay(infoRoot, myShapeScene, simType);		
		setup.buttonBack(infoRoot, myHomeScene);
		myStage.setScene(myInfoScene);
		return myInfoScene;
	}
	
	private Scene createScene(int width, int height, Paint background, Scene nextScene, String message, String[] choice) {
		Group root = new Group();
		myBoundaryScene = new Scene(root, width, height, BACKGROUND);
		setup.createLabel(root,  width, height, message);
		setup.createButtons(root, nextScene, choice); 
		return myBoundaryScene;
	}
	
	private Scene askSizeScene(){
		Group sizeSceneRoot = new Group();
		mySizeScene = new Scene(sizeSceneRoot, width, height, BACKGROUND);
		setup.createLabel(sizeSceneRoot,  width, height, "ChooseGridSize");
		VBox sizes = setup.createBox(sizeSceneRoot, width, height);		
		for (String button: sizeButtons) {
			Button btn = setup.createSizeButton(sizeSceneRoot, button);
			btn.setOnAction(f -> SizeClicked(f,button));
			sizes.getChildren().add(btn);
		}
		mySizeScene.setRoot(sizeSceneRoot);
		return mySizeScene;
	}
	
	public void SizeClicked(ActionEvent f, String size){
		if (((Button) f.getSource()).getText().equals(myResources.getString("SmallGrid"))){
			gridSize = 1;	
			
		} else if (((Button) f.getSource()).getText().equals(myResources.getString("MediumGrid"))){
			gridSize = 2;
			System.out.println(gridSize);
			
		} else if (((Button) f.getSource()).getText().equals(myResources.getString("LargeGrid"))){
			gridSize = 3;
			
		}
		createModelAndFrames();
	}
	
	private void createModelAndFrames() {
		getUserChoices();
		root.getChildren().clear();
		BorderPane bp = new BorderPane();
		panel.getChildren().clear();

		GridGenerator gg = new GridGenerator(simType, unitShape, gridSize, 3, boundaryStyle, inputStyle, SIZE-50, SIZE-50);
		Grid currGrid = gg.returnCurrGrid();
		Grid nextGrid = gg.returnNextGrid();
		Grid initialGrid = gg.returnInitialGrid();
		if (fileName.equals(lifeFile)) {
			currentModel = new lifeModel(currGrid, nextGrid, initialGrid);
		} else if (fileName.equals(fireFile)) {
			currentModel = new fireModel(currGrid, nextGrid, initialGrid);
		} else if (fileName.equals(watorFile)) {
			currentModel = new watorModel(currGrid, nextGrid, initialGrid);
		} else if (fileName.equals(segregationFile)) {
			currentModel = new segregationModel(currGrid, nextGrid, initialGrid);
		}
		else if (fileName.equals(sugarFile)) {
			currentModel = new sugarModel(currGrid, nextGrid, initialGrid);
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
		if (!(currentModel instanceof segregationModel)) {
			popGraph = simSetup.createGraph(currentModel);
			popGraph.setMaxHeight(100);
			bp.setBottom(popGraph);
		}
	}

	private void getUserChoices() {
		Integer[] setupChoices = setup.getChoices();
		simType = setupChoices[0];
		unitShape = setupChoices[1];
		neighborConfig = setupChoices[2];
		boundaryStyle = setupChoices[3];
		inputStyle = setupChoices[4];
		
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
			if(popGraph != null) simSetup.addData(currentModel);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
} 