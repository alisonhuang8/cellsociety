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
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import simUnits.FireUnits;
import subGrids.squareGrid;
import subUnits.Alive;
import subUnits.Burning;
import subUnits.Burnt;

public class fireModel extends Model {
	
	private int across;
	private int down;
	private Grid curGrid, nextGrid;
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
		curGrid = new squareGrid(down, across, height/down, width/across);
		nextGrid = new squareGrid(down, across, height/down, width/across);
		getFireScene();
		
	}

	private void getFireScene(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				Shape u = curGrid.getShape(i, j);
				if(reads.get(i, j) == 'G'){
					u = new Alive();
				}
				if(i == 0 && j == 0){
					u = new Burning();
				}
				root.getChildren().add(u);
			}

		}
	}
	
	public void updateGrid(){
		fire();
	}

	
	private void fire(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				Shape cur = curGrid.getShape(i, j);
				Shape next = nextGrid.getShape(i, j);
				root.getChildren().remove(cur);
				int n = getBurningNeighbors(i, j);
				if(((Unit) cur).isAlive()){
					if(((1.0 - (Math.pow(1.0 - catchChance, n))) * 100.0) > rand.nextInt(100)){
						next = new Burning();
					}
					else{
						next = new Alive();
					}
				}
				else if(((Unit) cur).isBurning() || ((Unit) cur).isBurnt()){
					next = new Burnt();
				}
				root.getChildren().add(next);
			}
		}
	}
	
	private int getBurningNeighbors(int i, int j){
		int[] move1 = {-1, 0, 0, 1};
		int[] move2 = {0, 1, -1, 0};
		int burning = 0;
		
		for(int x = 0; x < move1.length; x++){
			int newI = i + move1[x];
			int newJ = j + move2[x];
			Shape s = curGrid.getShape(newI, newJ);
			if(newI >= 0 && newI < down && newJ >= 0 && newJ < across && ((Unit) s).isBurning()){
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