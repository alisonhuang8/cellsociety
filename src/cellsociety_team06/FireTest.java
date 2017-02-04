package cellsociety_team06;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import subUnits.Alive;
import subUnits.Burning;
import subUnits.Burnt;

public class FireTest extends Application{

	private Stage window;
	int across = 20;
	int down = 20;
	private Unit[][] curGrid = new Unit[down][across];
	private Unit[][] nextGrid = new Unit[down][across];
	Random rand = new Random();
	private int width = 500;
	private int height = 500;
	private Group root = new Group();
	private double catchChance = 0.7;
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setResizable(false);
		window.setScene(getFireScene());
		window.show();
	}
	
	
	private Scene getFireScene(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				if(i == down/2 && j == across/2){
					curGrid[i][j] = new Burning((width * i)/down, (height * j)/across, width/down, height/across);
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
		fire();
	}

	
	private void fire(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				root.getChildren().remove(curGrid[i][j]);
				int n = getBurningNeighbors(i, j);
				if(curGrid[i][j].isAlive()){
					if(((1.0 - (Math.pow(1.0 - catchChance, n))) * 100.0) > rand.nextInt(100)){
						nextGrid[i][j] = new Burning((width * i)/down, (height * j)/across, width/down, height/across);
					}
					else{
						nextGrid[i][j] = new Alive((width * i)/down, (height * j)/across, width/down, height/across);
					}
				}
				if(curGrid[i][j].isBurning() || curGrid[i][j].isBurnt()){
					nextGrid[i][j] = new Burnt((width * i)/down, (height * j)/across, width/down, height/across);
				}
				root.getChildren().add(nextGrid[i][j]);
			}
		}
		curGrid = nextGrid;
		nextGrid = new Unit[down][across];
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
