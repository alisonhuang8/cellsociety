package cellsociety_team06;

import java.util.Random;
import java.util.ResourceBundle;

import XMLReads.lifeReads;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import simUnits.LifeUnits;
import subUnits.Alive;
import subUnits.Blank;

public class lifeModel extends Model {
	

	private int down;
	private int across;
	private LifeUnits[][] curGrid;
	private LifeUnits[][] nextGrid;
	Random rand = new Random();
	private int width = 500;
	private int height = 500;
	private Group root = new Group();
	lifeReads reads;
	
	public lifeModel(Stage s, Timeline t, ResourceBundle r, int height, int width, int size){
		super(s,t,r);
		reads = new lifeReads(size);
		down = reads.height();
		across = reads.width();
		this.height = height;
		this. width = width;
		curGrid = new LifeUnits[down][across];
		nextGrid = new LifeUnits[down][across];
		getLifeScene();
	}
		
	private void getLifeScene(){
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
	}
	
	public void updateGrid(){
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