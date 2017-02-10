package Test;

import java.util.Collection;
import java.util.Random;
import cellsociety_team06.Grid;
import cellsociety_team06.Unit;
import cellsociety_team06.hexGrid;
import cellsociety_team06.squareGrid;
import cellsociety_team06.triangularGrid;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import subUnits.Alive;
import subUnits.Burning;
import subUnits.Burnt;


public class Test extends Application{
	private Grid curGrid, nextGrid;
	Random rand = new Random();
	private Group root = new Group();
	private double catchChance = 0.8;
	private Stage window;
	
	public static void main(String[] args){
		launch(args);
	}
	
	private void getFireScene(){
		for(int i = 0; i < curGrid.cols(); i++){
			for(int j = 0; j < curGrid.rows(); j++){
				curGrid.setUnit(i, j, new Alive(curGrid.getUnit(i, j)));
				if(i == 0 && j == 0){
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
	
	private void updateGrid(){
		for(int i = 0; i < curGrid.cols(); i++){
			for(int j = 0; j < curGrid.rows(); j++){
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
	
	private int getBurningNeighbors(Collection<Unit> neighbors){
		int total = 0;
		for(Unit n:neighbors){
			if(n.isBurning()) total++;
		}
		return total;
	}

	private void handleKeyInput(KeyCode code){
		if(code == KeyCode.SPACE){
			updateGrid();
		}
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.window = primaryStage;
		curGrid = new hexGrid(5, 5, 25);
		nextGrid = new hexGrid(5, 5, 25);
		root = new Group();
		root.getChildren().addAll(curGrid.getChildren());
		Scene s = new Scene(root, 500, 500, Color.WHITE);
		s.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		window.setScene(s);
		window.show();
		getFireScene();
	}
	
	private void resetCur(){
		for(int i = 0; i < curGrid.cols(); i++){
			for(int j = 0; j < curGrid.rows(); j++){
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