package cellsociety_team06;

import java.util.Random;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import subUnits.Alive;
import subUnits.Blank;
import subUnits.Burning;
import subUnits.Burnt;

public class Tester extends Application{

	private Stage window;
	private Unit[][] curGrid = new Unit[10][10];
	private Unit[][] nextGrid = new Unit[10][10];
	Random rand = new Random();
	private int width = 500;
	private int height = 500;
	private Group root = new Group();
	private double catchChance = 0.4;
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setResizable(false);
		window.setScene(reset());
		window.show();
	}
	
	private Scene reset(){
		root.getChildren().removeAll();
		nextGrid = new Unit[10][10];
		return getLifeScene();
//		return getFireScene();
	}
	
	private Scene getLifeScene(){
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				int r = rand.nextInt(4);
				if(r < 2){
					curGrid[i][j] = new Blank((width * i)/10, (height * j)/10, width/10, height/10);
				}
				else{
					curGrid[i][j] = new Alive((width * i)/10, (height * j)/10, width/10, height/10);
				}
				root.getChildren().add(curGrid[i][j]);
			}
		}
		Scene scene = new Scene(root, width, height);
		scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		return scene;
	}
	
	private Scene getFireScene(){
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				if(i == 0 && j == 0){
					curGrid[i][j] = new Burning((width * i)/10, (height * j)/10, width/10, height/10);
				}
				else{
					curGrid[i][j] = new Alive((width * i)/10, (height * j)/10, width/10, height/10);
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
		if(code == KeyCode.R){
			window.setScene(reset());
		}
		
	}
	
	private void updateGrid(){
//		reverse();
		life();
//		fire();
	}
	
	private void reverse(){
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				root.getChildren().remove(curGrid[i][j]);
				if(curGrid[i][j].isBlank()){
					nextGrid[i][j] = new Burning((width * i)/10, (height * j)/10, width/10, height/10);
				}
				else{
					nextGrid[i][j] = new Blank((width * i)/10, (height * j)/10, width/10, height/10);
				}
				root.getChildren().add(nextGrid[i][j]);
			}
		}
		curGrid = nextGrid;
		nextGrid = new Unit[10][10];
	}

	private void life(){
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				root.getChildren().remove(curGrid[i][j]);
				int n = getAliveNeighbors(i, j);
				if(curGrid[i][j].isAlive()){
					if(n < 2 || n > 3){
						nextGrid[i][j] = new Blank((width * i)/10, (height * j)/10, width/10, height/10);
					}
					else{
						nextGrid[i][j] = curGrid[i][j];
					}
				}
				else{
					if(n == 3){
						nextGrid[i][j] = new Alive((width * i)/10, (height * j)/10, width/10, height/10);
					}
					else{
						nextGrid[i][j] = new Blank((width * i)/10, (height * j)/10, width/10, height/10);
					}
				}
				root.getChildren().add(nextGrid[i][j]);
			}
		}
		curGrid = nextGrid;
		nextGrid = new Unit[10][10];
	}
	
	private int getAliveNeighbors(int i, int j){
		int[] move1 = {-1, -1, -1, 0, 0, 1, 1, 1};
		int[] move2 = {1, 0, -1, 1, -1, 1, 0, -1};
		int alive = 0;
		
		for(int x = 0; x < move1.length; x++){
			int newI = i + move1[x];
			int newJ = j + move2[x];
			if(newI >= 0 && newI < 10 && newJ >= 0 && newJ < 10 && curGrid[newI][newJ].isAlive()){
				alive++;
			}
		}
		return alive;
	}
	
	private void fire(){
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				root.getChildren().remove(curGrid[i][j]);
				int n = getBurningNeighbors(i, j);
				if(curGrid[i][j].isAlive()){
					if(((1.0 - (Math.pow(1.0 - catchChance, n))) * 100.0) > rand.nextInt(100)){
						nextGrid[i][j] = new Burning((width * i)/10, (height * j)/10, width/10, height/10);
					}
					else{
						nextGrid[i][j] = new Alive((width * i)/10, (height * j)/10, width/10, height/10);
					}
				}
				if(curGrid[i][j].isBurning() || curGrid[i][j].isBurnt()){
					nextGrid[i][j] = new Burnt((width * i)/10, (height * j)/10, width/10, height/10);
				}
				root.getChildren().add(nextGrid[i][j]);
			}
		}
		curGrid = nextGrid;
		nextGrid = new Unit[10][10];
	}
	
	private int getBurningNeighbors(int i, int j){
		int[] move1 = {-1, -1, -1, 0, 0, 1, 1, 1};
		int[] move2 = {1, 0, -1, 1, -1, 1, 0, -1};
		int burning = 0;
		
		for(int x = 0; x < move1.length; x++){
			int newI = i + move1[x];
			int newJ = j + move2[x];
			if(newI >= 0 && newI < 10 && newJ >= 0 && newJ < 10 && curGrid[newI][newJ].isBurning()){
				burning++;
			}
		}
		return burning;
	}
	
}
