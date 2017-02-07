package cellsociety_team06;

import java.util.Random;


import XMLReads.fireReads;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.stage.Stage;
import subUnits.Alive;
import subUnits.Burning;
import subUnits.Burnt;

public class fireModel extends Model {
	
	int across;
	int down;
	private Unit[][] curGrid;
	private Unit[][] nextGrid;
	Random rand = new Random();
	private int width;
	private int height;
	private Group root = new Group();
	private double catchChance = 0.7;
	fireReads reads;
	
	
	public fireModel(Stage s, Timeline t, int width, int height, int size){
		super(s,t);
		reads = new fireReads(size);
		down = reads.height();
		across = reads.width();
		this.width = width;
		this.height = height;
		curGrid = new Unit[down][across];
		nextGrid = new Unit[down][across];
		getFireScene();
	}

	private void getFireScene(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				if(reads.get(i, j) == 'G'){
					curGrid[i][j] = new Alive((width * i)/down, (height * j)/across, width/down, height/across);
				}
				if(i == 0 && j == 0){
					curGrid[i][j] = new Burning((width * i)/down, (height * j)/across, width/down, height/across);
				}
				root.getChildren().add(curGrid[i][j]);
			}
		}
	}
	
	
	
	public void updateGrid(){
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
	
	@Override
	public Group getRoot() {
		return root;
	}

	@Override
	public void setNextScene() {
		updateGrid();
	}

	@Override
	public void reset() {
		
		root.getChildren().clear();
		getFireScene();
	}
	
}