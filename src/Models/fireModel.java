package Models;

import java.util.Collection;
import java.util.Random;
import java.util.ResourceBundle;

import Unit.Unit;
import XMLReads.fireReads;
import cellsociety_team06.Model;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.stage.Stage;
import subGrids.hexGrid;
import subUnits.Alive;
import subUnits.Burning;
import subUnits.Burnt;

public class fireModel extends Model {
	
	private int across;
	private int down;
	private Random rand = new Random();
	private double catchChance = 0.7;
	private fireReads reads;
	
	public fireModel(Stage s, Timeline t, ResourceBundle r, int width, int height, int size){
		super(s,t,r);
		reads = new fireReads(size);
		down = reads.height();
		across = reads.width();
		curGrid = new hexGrid(down, across, height/down/2);
		nextGrid = new hexGrid(down, across, height/down/2);
		getFireScene();
	}

	private void getFireScene(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				if(reads.get(i, j) == 'G'){
					curGrid.setUnit(i, j, new Alive(curGrid.getUnit(i, j)));
				}
				if(i == 0 && j == 0){
					curGrid.setUnit(i, j, new Burning(curGrid.getUnit(i, j)));
				}
			}
		}
		resetRoot();
	}
	
	public void updateGrid(){
		for(int i = 0; i < curGrid.rows(); i++){
			for(int j = 0; j < curGrid.cols(); j++){
				int n = getBurningNeighbors(curGrid.getNeighbors(i, j).values());
				Unit u = nextGrid.getUnit(i, j);
				if(curGrid.getUnit(i, j).isAlive()){
					if(((1.0 - (Math.pow(1.0 - catchChance, n))) * 100.0) > rand.nextInt(100)){
						u = new Burning(u);
					}
					else{
						u = new Alive(u);
					}
				}
				else if(curGrid.getUnit(i, j).isBurning() || curGrid.getUnit(i, j).isBurnt()){
					u = new Burnt(u);
				}
				nextGrid.setUnit(i, j, u);
			}
		}
		resetCur();
		resetRoot();
	}
	
	protected void resetCur(){
		for(int i = 0; i < curGrid.rows(); i++){
			for(int j = 0; j < curGrid.cols(); j++){
				if(nextGrid.getUnit(i, j).isAlive()){
					curGrid.setUnit(i, j, new Alive(curGrid.getUnit(i, j)));
				}
				else if(nextGrid.getUnit(i, j).isBurning()){
					curGrid.setUnit(i, j, new Burning(curGrid.getUnit(i, j)));
				}
				else{
					curGrid.setUnit(i, j, new Burnt(curGrid.getUnit(i, j)));
				}
			}
		}
	}
	
	private int getBurningNeighbors(Collection<Unit> neighbors){
		int total = 0;
		for(Unit n:neighbors){
			if(n.isBurning()) total++;
		}
		return total;
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