package cellsociety_team06;

import java.util.Random;

import XMLReads.lifeReads;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import subUnits.Alive;
import subUnits.Blank;

public class lifeModel extends Model {
	
	private Stage window;
	private int down;
	private int across;
	private Unit[][] curGrid;
	private Unit[][] nextGrid;
	Random rand = new Random();
	private int width = 500;
	private int height = 500;
	private Group root = new Group();
	lifeReads reads;
	
	public lifeModel(Stage s, Timeline t, int height, int width, int size){
		super(s,t);
		reads = new lifeReads(size);
		down = reads.height();
		across = reads.width();
		this.height = height;
		this. width = width;
		curGrid = new Unit[down][across];
		nextGrid = new Unit[down][across];
		getLifeScene();
	}

	private void getLifeScene(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				if(reads.get(i, j) == '0'){
					curGrid[i][j] = new Blank((width * i)/down, (height * j)/across, width/down, height/across);
				}
				else{
					curGrid[i][j] = new Alive((width * i)/down, (height * j)/across, width/down, height/across);
				}
				root.getChildren().add(curGrid[i][j]);
			}
		}
	}
	
	public void updateGrid(){
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


	@Override
	public void setNextScene() {
		updateGrid();
	}

	@Override
	public Group getRoot() {
		return root;
	}

	@Override
	public void reset() {
		root.getChildren().clear();
		getLifeScene();
	}
	
	
	
}
