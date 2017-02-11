/**
 * Written by Gideon Pfeffer
 * Used to test the different orientations and implementations
 * without having to go through the application
 * USes the fire model
 */
package Test;

import java.util.Collection;
import java.util.Random;

import Unit.Unit;
import cellsociety_team06.Grid;
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


public class foragingAnts extends Application{
	private Grid curGrid, nextGrid;
	Random rand = new Random();
	private Group root = new Group();
	private double catchChance = 0.4;
	private Stage window;
	
	public static void main(String[] args){
		launch(args);
	}
	
	/**
	 * sets the starting stage
	 */
	private void getFireScene(){
		for(int i = 0; i < curGrid.rows(); i++){
			for(int j = 0; j < curGrid.cols(); j++){
				curGrid.setUnit(i, j, new Alive(curGrid.getUnit(i, j)));
				if(i == 5 && j == 2){
					curGrid.setUnit(i, j, new Burning(curGrid.getUnit(i, j)));
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
				int n = getBurningNeighbors(curGrid.getNeighbors(i, j).values());
				Unit u = nextGrid.getUnit(i, j);
				if(curGrid.getUnit(i, j).isAlive()){
					if(((1.0 - (Math.pow(1.0 - catchChance, n))) * 100.0) > rand.nextInt(100)){
						u = new Burning(u);
					}
					else{
						u = new Alive(u);
					}
				}
				else if(curGrid.getUnit(i, j).isBurning() || curGrid.getUnit(i, j).isBurnt()){
					u = new Burnt(u);
				}
				nextGrid.setUnit(i, j, u);
			}
		}
		resetCur();
		updateRoot();
	}
	
	/**
	 * returns the number of burning neighbors
	 */
	private int getBurningNeighbors(Collection<Unit> neighbors){
		int total = 0;
		for(Unit n:neighbors){
			if(n.isBurning()) total++;
		}
		return total;
	}

	/**
	 * moves the simulation forward one tick
	 */
	private void handleKeyInput(KeyCode code){
		if(code == KeyCode.SPACE){
			updateGrid();
		}
	}
	
	/**
	 * Sets up the stage
	 * and makes the grids
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.window = primaryStage;
		curGrid = new squareGrid(50, 50, 10);
		curGrid.makeTorroidal();
		nextGrid = new squareGrid(50, 50, 10);
		root = new Group();
		root.getChildren().addAll(curGrid.getChildren());
		Scene s = new Scene(root, 500, 500, Color.WHITE);
		s.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		window.setScene(s);
		window.show();
		getFireScene();
	}
	
	/**
	 * resets current to be equal to next
	 */
	private void resetCur(){
		for(int i = 0; i < curGrid.rows(); i++){
			for(int j = 0; j < curGrid.cols(); j++){
				if(nextGrid.getUnit(i, j).isAlive()){
					curGrid.setUnit(i, j, new Alive(curGrid.getUnit(i, j)));
				}
				else if(nextGrid.getUnit(i, j).isBurning()){
					curGrid.setUnit(i, j, new Burning(curGrid.getUnit(i, j)));
				}
				else{
					curGrid.setUnit(i, j, new Burnt(curGrid.getUnit(i, j)));
				}
			}
		}
	}
}