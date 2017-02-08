package tests;

import java.util.Random;

import XMLReads.fireReads;
import XMLReads.segReads;

import java.util.List;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import simUnits.FireUnits;
import subUnits.Alive;
import subUnits.Burning;
import subUnits.Burnt;

public class FireTest extends Application{

	private Stage window;
	int across;
	int down;
	private FireUnits[][] curGrid;
	private FireUnits[][] nextGrid;
	Random rand = new Random();
	private int width = 500;
	private int height = 500;
	private Group root = new Group();
	private double catchChance = 0.5;
	fireReads reads;
	List<Color> colors = new ArrayList<>();
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		colors.add(Color.GREEN);
		colors.add(Color.RED);
		colors.add(Color.DARKGRAY);
		reads = new fireReads(2);
		down = reads.height();
		across = reads.width();
		curGrid = new FireUnits[down][across];
		nextGrid = new FireUnits[down][across];
		window = primaryStage;
		window.setResizable(false);
		window.setScene(getFireScene(colors));
		window.show();
	}
	
	
	private Scene getFireScene(List<Color> list){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				curGrid[i][j] = new FireUnits((width * i)/down, (height * j)/across, width/down, height/across, list);
				curGrid[i][j].setAlive();
				if(i == 0 && j == 0){
					curGrid[i][j].setBurning();
				}
				root.getChildren().add(curGrid[i][j]);
			}
		}
		Scene scene = new Scene(root, width, height);
		scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		return scene;
	}
	
	private void handleKeyInput(KeyCode code){
		if(code == KeyCode.SPACE){
			updateGrid();
		}
	}
	
	private void updateGrid(){
		fire();
	}

	
	private void fire(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				root.getChildren().remove(curGrid[i][j]);
				nextGrid[i][j] = curGrid[i][j].copy();
				int n = getBurningNeighbors(i, j);
				if(curGrid[i][j].isAlive()){
					if(((1.0 - (Math.pow(1.0 - catchChance, n))) * 100.0) > rand.nextInt(100)){
						nextGrid[i][j].setBurning();
					}
					else{
						nextGrid[i][j].setAlive();
					}
				}
				else if(curGrid[i][j].isBurning() || curGrid[i][j].isBurnt()){
					nextGrid[i][j].setBurnt();
				}
				root.getChildren().add(nextGrid[i][j]);
			}
		}
		curGrid = nextGrid;
		nextGrid = new FireUnits[down][across];
	}
	
	private int getBurningNeighbors(int i, int j){
		int[] move1 = {-1, 0, 0, 1};
		int[] move2 = {0, 1, -1, 0};
		int burning = 0;
		
		for(int x = 0; x < move1.length; x++){
			int newI = i + move1[x];
			int newJ = j + move2[x];
			if(newI >= 0 && newI < down && newJ >= 0 && newJ < across && curGrid[newI][newJ].isBurning()){
				burning++;
			}
		}
		return burning;
	}
}
