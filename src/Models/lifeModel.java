package Models;

import java.util.Random;
import java.util.ResourceBundle;

import XMLReads.lifeReads;
import cellsociety_team06.Model;
import cellsociety_team06.Unit;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import subUnits.Alive;
import subUnits.Blank;

import java.util.ArrayList;
import java.util.List;

public class lifeModel extends Model {
	

	private int down;
	private int across;
	private List<List<Unit>> curGrid;
	private List<List<Unit>> nextGrid;
	Random rand = new Random();
	private int width = 500;
	private int height = 500;
	private Group root = new Group();
	lifeReads reads;
	
	public lifeModel(Stage s, Timeline t, ResourceBundle r, int height, int width, int size){
		super(s,t,r);
		this.height = height;
		this.width = width;
		reads = new lifeReads(size);
	}
	
	private void start(){
		down = reads.height();
		across = reads.width();
		curGrid = new ArrayList<>();
		nextGrid = new ArrayList<>();
		getLifeScene();
	}
		
	private void getLifeScene(){
		for(int i = 0; i < down; i++){
			List<Unit> row = new ArrayList<>();
			for(int j = 0; j < across; j++){
				Unit u;
				if(reads.get(i, j) == '0'){
					u = new Alive((width * i)/down, (height * j)/across, width/down, height/across);
				}
				else{
					u = new Blank((width * i)/down, (height * j)/across, width/down, height/across);
				}
				root.getChildren().add(u);
				row.add(u);
			}
			nextGrid.add(row);
			curGrid.add(row);
		}
	}
	
	public void updateGrid(){
		life();
	}

	private void life(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				Unit u = curGrid.get(i).get(j);
				root.getChildren().remove(u);
				int n = getAliveNeighbors(i, j);
				if(u.isAlive()){
					if(n < 2 || n > 3){
						nextGrid.get(i).set(j, new Blank((width * i)/down, (height * j)/across, width/down, height/across));
					}
				}
				else{
					if(n == 3){
						nextGrid.get(i).set(j, new Alive((width * i)/down, (height * j)/across, width/down, height/across));
					}
				}
				root.getChildren().add(nextGrid.get(i).get(j));
			}
		}
		curGrid = new ArrayList<>(nextGrid);
	}
	
	private int getAliveNeighbors(int i, int j){
		int[] move1 = {-1, -1, -1, 0, 0, 1, 1, 1};
		int[] move2 = {1, 0, -1, 1, -1, 1, 0, -1};
		int alive = 0;
		
		for(int x = 0; x < move1.length; x++){
			int newI = i + move1[x];
			int newJ = j + move2[x];
			if(newI >= 0 && newI < down && newJ >= 0 && newJ < across && curGrid.get(newI).get(newJ).isAlive()){
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
		start();
	}
	
	
	
}