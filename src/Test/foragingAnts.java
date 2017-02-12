/**
 * Written by Gideon Pfeffer
 * Used to test the different orientations and implementations
 * without having to go through the application
 * USes the fire model
 */
package Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;
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
		for(int i = 0; i < curGrid.rows(); i++){
			for(int j = 0; j < curGrid.cols(); j++){
				if(curGrid.getUnit(i, j).isAnt()){
					Ant a = (Ant) curGrid.getUnit(i, j);
					if(a.hasFoodItem()) returnToNest(i, j, a);
					else findFoodSource(i, j, a);
				}
				
			}
		}
		updateRoot();
	}
	
	private void returnToNest(int row, int col, Ant a){
		if(locatedAtFoodSource(row, col)){
			a.setOrientation(neighborWithMaxHomePher(row, col));
		}
	}
	
	/**
	 * @returns the neighbor with the most home pharamones
	 */
	private int[] neighborWithMaxHomePher(int row, int col){
		double maxPher = 0;
		int[] dir = new int[]{0,0};
		Map<Integer[], Unit> map = curGrid.getNeighbors(row, col);
		for(Integer[] place: map.keySet()){
			Ground g = (Ground) curGrid.getUnit(place[0], place[1]);
			if(g.getHomePheromone() > maxPher){
				dir = new int[]{row + place[0], col + place[1]};
				maxPher = g.getHomePheromone();
			}
		}
		return dir;
	}
	
	private boolean locatedAtFoodSource(int row, int col){
		for(Unit u : curGrid.getNeighbors(row, col).values()){
			if(u.isFood()) return true;
		}
		return false;
	}
	
	private void findFoodSource(int row, int col, Ant a){

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