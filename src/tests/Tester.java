package tests;

import java.util.Random;

import XMLReads.lifeReads;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import simUnits.LifeUnits;
import subUnits.Alive;
import subUnits.Blank;


public class Tester extends Application{

	private Stage window;
	private int down;
	private int across;
	private LifeUnits[][] curGrid;
	private LifeUnits[][] nextGrid;
	Random rand = new Random();
	private int width = 500;
	private int height = 500;
	private Group root = new Group();
	lifeReads reads;
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		reads = new lifeReads(2);
		down = reads.height();
		across = reads.width();
		curGrid = new LifeUnits[down][across];
		nextGrid = new LifeUnits[down][across];
		window = primaryStage;
		window.setResizable(false);
		window.setScene(getLifeScene());
		window.show();
	}
	
	
	private Scene getLifeScene(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				curGrid[i][j] = new LifeUnits((width * i)/down, (height * j)/across, width/down, height/across);
				if(reads.get(i, j) == '0'){
					curGrid[i][j].setDead();
				}
				else{
					curGrid[i][j].setAlive();
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
		life();
	}

	private void life(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				root.getChildren().remove(curGrid[i][j]);
				int n = getAliveNeighbors(i, j);
				nextGrid[i][j] = curGrid[i][j].copy();
				if(curGrid[i][j].isAlive()){
					if(n < 2 || n > 3){
						nextGrid[i][j].setDead();
					}
				}
				else{
					if(n == 3){
						nextGrid[i][j].setAlive();
					}
				}
				root.getChildren().add(nextGrid[i][j]);
			}
		}
		curGrid = nextGrid;
		nextGrid = new LifeUnits[down][across];
	}
	
	private int getAliveNeighbors(int i, int j){
		int[] move1 = {-1, -1, -1, 0, 0, 1, 1, 1};
		int[] move2 = {1, 0, -1, 1, -1, 1, 0, -1};
		int alive = 0;
		
		for(int x = 0; x < move1.length; x++){
			int newI = i + move1[x];
			int newJ = j + move2[x];
			if(newI >= 0 && newI < down && newJ >= 0 && newJ < across && curGrid[newI][newJ].isAlive()){
				alive++;
			}
		}
		return alive;
	}
}
