/**
 * Written by Gideon Pfeffer
 * Used to test the different orientations and implementations
 * without having to go through the application
 * USes the fire model
 */
package Test;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import Unit.Unit;
import XMLReads.antsReads;
import cellsociety_team06.Grid;
import cellsociety_team06.ReadXMLFile;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import subGrids.squareGrid;
import subGrids.triangularGrid;
import subUnits.Alive;
import subUnits.Burning;
import subUnits.Burnt;
import subUnits.foragingAnts.Ant;
import subUnits.foragingAnts.Food;
import subUnits.foragingAnts.Ground;
import subUnits.foragingAnts.Nest;


public class foragingAnts extends Application{
	private Grid curGrid, nextGrid;
	private Random rand = new Random();
	private Group root = new Group();
	private double catchChance = 0.4;
	private Stage window;
	private antsReads files;
	
	public static void main(String[] args){
		launch(args);
	}
	
	/**
	 * Sets up the stage
	 * and makes the grids
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		files = new antsReads();
		this.window = primaryStage;
		curGrid = new squareGrid(60, 60, 10);
		curGrid.makeTorroidal();
		nextGrid = new squareGrid(60, 60, 10);
		root = new Group();
		Scene s = new Scene(root, 600, 600, Color.WHITE);
		s.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		window.setScene(s);
		window.show();
		getFirstScene();
	}
	
	/**
	 * sets the starting stage
	 */
	private void getFirstScene(){
		for(int i = 0; i < curGrid.rows(); i++){
			for(int j = 0; j < curGrid.cols(); j++){
				if(files.get(i, j) == 'A'){
					curGrid.setUnit(i, j, new Ant(curGrid.getUnit(i, j)));
				}
				else if(files.get(i, j) == 'N'){
					curGrid.setUnit(i, j, new Nest(curGrid.getUnit(i, j)));
				}
				else if(files.get(i, j) == 'F'){
					curGrid.setUnit(i, j, new Food(curGrid.getUnit(i, j)));
				}
				else{
					curGrid.setUnit(i, j, new Ground(curGrid.getUnit(i, j)));
				}
			}
		}
		updateRoot();
	}
	
	private void updateRoot(){
		root.getChildren().clear();
		root.getChildren().addAll(curGrid.getChildren());
	}
	
	/**
	 * moves the grid forward a step
	 */
	private void updateGrid(){
		for(Unit instance : curGrid.getInstances(new Ant()).values()){
			Ant a = (Ant) instance;
			if(a.hasFoodItem()) returnToNest(a);
			else findFoodSource(a);
		}
		updateRoot();
	}
	
	private void returnToNest(Ant a){
		
	}
	
	private void findFoodSource(Ant a){

	}

	/**
	 * moves the simulation forward one tick
	 */
	private void handleKeyInput(KeyCode code){
		if(code == KeyCode.SPACE){
			updateGrid();
		}
	}
}