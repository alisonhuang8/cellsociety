/**
 * Written by Gideon Pfeffer
 * Used to test the different orientations and implementations
 * without having to go through the application
 * USes the fire model
 */
package Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import subUnits.sugarScape.Agent;
import subUnits.sugarScape.Sugar;

public class sugarScape extends Application{
	private Grid curGrid;
	private Random rand = new Random();
	private Group root = new Group();
	private Stage window;
	private Map<Integer[], Integer[]> agentMoves = new HashMap<>();
	private List<List<Integer>> eaten = new ArrayList<>();
	
	
	public static void main(String[] args){
		launch(args);
	}
	
	/**
	 * Sets up the stage
	 * and makes the grids
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.window = primaryStage;
		curGrid = new squareGrid(20, 20, 25);
		curGrid.setNeighbors(new int[] {-1, 1, 0, 0}, new int[]{0, 0, 1, -1});
		root = new Group();
		Scene s = new Scene(root, 500, 500, Color.WHITE);
		s.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		window.setScene(s);
		window.show();
		getStartScene();
	}
	
	/**
	 * sets the starting stage
	 */
	private void getStartScene(){
		for(int i = 0; i < curGrid.rows(); i++){
			for(int j = 0; j < curGrid.cols(); j++){
				if(rand.nextInt(2) == 1){
					curGrid.setUnit(i, j, new Agent(curGrid.getUnit(i, j)));
				}
				else{
					curGrid.setUnit(i, j, new Sugar(curGrid.getUnit(i, j)));
				}
			}
		}
		updateRoot();
	}
	
	private void updateRoot(){
		root.getChildren().clear();
		root.getChildren().addAll(curGrid.getChildren());
	}
	
	
	private void updateGrid(){
		agentMoves.clear();
		eaten.clear();
		updateAgents();
		move();
	}
	
	/**
	 * moves the grid forward a step
	 */
	private void updateAgents(){
		for(int i = 0; i < curGrid.rows(); i++){
			for(int j = 0; j < curGrid.cols(); j++){
				Integer[] place = {i, j};
				if(curGrid.getUnit(i, j).isAgent()){
					Integer[] move = pickRandomSugar(i, j);
					if(move == null) continue;
					eaten.add(Arrays.asList(move));
					agentMoves.put(place, move);
				}
				else{
					Sugar s = (Sugar) curGrid.getUnit(i, j);
					s.regen();
				}
			}
		}
		updateRoot();
	}
	
	private void move(){
		for(Integer[] place: agentMoves.keySet()){
			move(place[0], place[1],
					agentMoves.get(place)[0], agentMoves.get(place)[1]);
		}
	}
	
	private void move(int agRow, int agCol, int sugRow, int sugCol){
		Agent a = (Agent) curGrid.getUnit(agRow, agCol);
		Sugar s = (Sugar) curGrid.getUnit(sugRow, sugCol);
		a.metabolize();
		if(!a.isDead()) {
			a.pickedUp(s.getSugar());
			curGrid.swap(agRow, agCol, sugRow, sugCol);
		}
		curGrid.setUnit(agRow, agCol,
				new Sugar(curGrid.getUnit(agRow, agCol), 0));
		updateRoot();
		
	}
	
	private Integer[] pickRandomSugar(int row, int col){
		Agent a = (Agent) curGrid.getUnit(row, col);
		Map<Integer[], Unit> map = curGrid.getInstances(new Sugar());
		List<Integer[]> list = new ArrayList<>();
		for(Integer[] place : map.keySet()){
			if(isValid(row, col, place[0], place[1], a) &&
					!eaten.contains(Arrays.asList(place))){
				list.add(place);
			}
		}
		if(list.size() == 0) return null;
		return list.get(rand.nextInt(list.size()));
	}
	
	private boolean isValid(int agentRow, int agentCol,
			int sugarRow, int sugarCol, Agent a){
		if(agentRow != sugarRow && agentCol != sugarCol) return false;
		if(Math.abs(agentRow - sugarRow) + Math.abs(agentCol - sugarCol)
			<= a.agentVision()) return true;
		return false;
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