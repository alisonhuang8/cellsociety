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
	private int down = 10;
	private int across = 10;
	private Unit[][] curGrid = new Unit[down][across];
	private Unit[][] nextGrid = new Unit[down][across];
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
		window.setScene(getLifeScene());
		window.show();
	}
	
	
	private Scene getLifeScene(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				int r = rand.nextInt(4);
				if(r < 2){
					curGrid[i][j] = new Blank((width * i)/down, (height * j)/across, width/down, height/across);
				}
				else{
					curGrid[i][j] = new Alive((width * i)/down, (height * j)/across, width/down, height/across);
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
				if(curGrid[i][j].isAlive()){
					if(n < 2 || n > 3){
						nextGrid[i][j] = new Blank((width * i)/down, (height * j)/across, width/down, height/across);
					}
					else{
						nextGrid[i][j] = curGrid[i][j];
					}
				}
				else{
					if(n == 3){
						nextGrid[i][j] = new Alive((width * i)/down, (height * j)/across, width/down, height/across);
					}
					else{
						nextGrid[i][j] = new Blank((width * i)/down, (height * j)/across, width/down, height/across);
					}
				}
				root.getChildren().add(nextGrid[i][j]);
			}
		}
		curGrid = nextGrid;
		nextGrid = new Unit[down][across];
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
