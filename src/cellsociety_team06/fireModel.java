package cellsociety_team06;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import XMLReads.fireReads;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import simUnits.FireUnits;
import subUnits.Alive;
import subUnits.Burning;
import subUnits.Burnt;

public class fireModel extends Model {
	
	
	private int across;
	private int down;
	private List<List<Unit>> curGrid;
	private List<List<Unit>> nextGrid;
	Random rand = new Random();
	private int width;
	private int height;
	private Group root = new Group();
	private double catchChance = 0.7;
	private fireReads reads;
	private ResourceBundle myResources;
	List<Color> colors;
	
	public fireModel(Stage s, Timeline t, ResourceBundle r, int width, int height, int size){
		super(s,t,r);
		colors = new ArrayList<>();
		colors.add(Color.GREEN);
		colors.add(Color.RED);
		colors.add(Color.GRAY);
		reads = new fireReads(size);
		down = reads.height();
		across = reads.width();
		this.width = width;
		this.height = height;
		curGrid = new ArrayList<>();
		nextGrid = new ArrayList<>();
		getFireScene(colors);
	}

	private void getFireScene(List<Color> list){
		for(int i = 0; i < down; i++){
			List<Unit> cols = new ArrayList<>();
			for(int j = 0; j < across; j++){
				if(reads.get(i, j) == 'G'){
					cols.add(new Alive((width * i)/down, (height * j)/across, width/down, height/across));
				}
				if(i == 0 && j == 0){
					cols.add(new Burning((width * i)/down, (height * j)/across, width/down, height/across));
				}
				root.getChildren().add(cols.get(j));
			}
			curGrid.add(cols);
			nextGrid.add(cols);
		}
	}
	
	public void updateGrid(){
		fire();
	}

	
	private void fire(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				Unit u = curGrid.get(i).get(j);
				root.getChildren().remove(u);
				int n = getBurningNeighbors(i, j);
				if(u.isAlive()){
					if(((1.0 - (Math.pow(1.0 - catchChance, n))) * 100.0) > rand.nextInt(100)){
						nextGrid.get(i).set(j, new Burning((width * i)/down, (height * j)/across, width/down, height/across));
					}
					else{
						nextGrid.get(i).set(j, new Alive((width * i)/down, (height * j)/across, width/down, height/across));
					}
				}
				else if(u.isBurning() || u.isBurnt()){
					nextGrid.get(i).set(j, new Burnt((width * i)/down, (height * j)/across, width/down, height/across));
				}
				root.getChildren().add(nextGrid.get(i).get(j));
			}
		}
		curGrid = new ArrayList<>(nextGrid);
	}
	
	private int getBurningNeighbors(int i, int j){
		int[] move1 = {-1, 0, 0, 1};
		int[] move2 = {0, 1, -1, 0};
		int burning = 0;
		
		for(int x = 0; x < move1.length; x++){
			int newI = i + move1[x];
			int newJ = j + move2[x];
			if(newI >= 0 && newI < down && newJ >= 0 && newJ < across && curGrid.get(newI).get(newJ).isBurning()){
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
		getFireScene(colors);
	}
	
}