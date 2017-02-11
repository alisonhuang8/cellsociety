package Models;

import java.util.ResourceBundle;

import Unit.Unit;
import XMLReads.lifeReads;
import cellsociety_team06.Model;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.stage.Stage;
import subGrids.squareGrid;
import subUnits.Alive;
import subUnits.Blank;

import java.util.Collection;

public class lifeModel extends Model {
	private int down;
	private int across;
	private lifeReads reads;
	
	public lifeModel(Stage s, Timeline t, ResourceBundle r, int height, int width, int size){
		super(s,t,r);
		reads = new lifeReads(size);
		down = reads.height();
		across = reads.width();
		curGrid = new squareGrid(down, across, height/down, width/across);
		nextGrid = new squareGrid(down, across, height/down, width/across);
		getLifeScene();
	}
	
	private void start(){
		getLifeScene();
	}
		
	private void getLifeScene(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				if(reads.get(i, j) == '0'){
					curGrid.setUnit(i, j, new Alive(curGrid.getUnit(i, j)));
				}
				else{
					curGrid.setUnit(i, j, new Blank(curGrid.getUnit(i, j)));
				}
			}
		}
		resetRoot();
	}
	
	public void updateGrid(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				int n = getAliveNeighbors(curGrid.getNeighbors(i, j).values());
				nextGrid.setUnit(i, j, curGrid.getUnit(i, j));
				if(curGrid.getUnit(i, j).isAlive() && (n < 2 || n > 3)){
					nextGrid.setUnit(i, j, new Blank(nextGrid.getUnit(i, j)));
				}
				else if(curGrid.getUnit(i, j).isBlank() && n == 3){
					nextGrid.setUnit(i, j, new Alive(nextGrid.getUnit(i, j)));
				}
			}
		}
		resetCur();
		resetRoot();
	}
	
	private int getAliveNeighbors(Collection<Unit> neighbors){
		int total = 0;
		for(Unit n:neighbors){
			if(n.isAlive()) total++;
		}
		return total;
	}

	@Override
	public void setNextScene() {
		updateGrid();
	}

	@Override
	public void reset() {
		start();
	}

	@Override
	protected void resetCur(){
		for(int i = 0; i < curGrid.rows(); i++){
			for(int j = 0; j < curGrid.cols(); j++){
				if(nextGrid.getUnit(i, j).isAlive()){
					curGrid.setUnit(i, j, new Alive(curGrid.getUnit(i, j)));
				}
				else{
					curGrid.setUnit(i, j, new Blank(curGrid.getUnit(i, j)));
				}
			}
		}
	}
}