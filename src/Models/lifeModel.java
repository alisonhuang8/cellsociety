/**
 * Written by Gideon Pfeffer
 * Runs all necessary measurements for the life model
 */
package Models;

import java.util.ResourceBundle;

import Unit.Unit;
import XMLReads.lifeReads;
import cellsociety_team06.Model;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.stage.Stage;
import subGrids.hexGrid;
import subGrids.squareGrid;
import subGrids.triangularGrid;
import subUnits.Alive;
import subUnits.Blank;

import java.util.Collection;

public class lifeModel extends Model {
	private int down;
	private int across;
	private lifeReads reads;
	
	/**
	 * @param s should be factored out by Faith
	 * @param t should be factored out by Faith
	 * @param r should be factored out by Faith
	 * @param width width of the stage
	 * @param height height of the stage
	 * @param size which of the three XML's should be read 
	 */
	public lifeModel(int height, int width, int size){
		reads = new lifeReads();
		down = reads.height();
		across = reads.width();
		curGrid = new squareGrid(down, across, height/down);
		nextGrid = new squareGrid(down, across, height/down);
		getLifeScene();
	}
	
	/**
	 * Sets up the initial scene
	 * Should be refactored into a level generator class
	 */
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
	
	/**
	 * goes through a tick of the CA, adding life/blank units as necessary
	 * the nextGrid
	 */
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
	
	/**
	 * @param neighbors collection of units
	 * @return how many of them are alive
	 */
	private int getAliveNeighbors(Collection<Unit> neighbors){
		int total = 0;
		for(Unit n:neighbors){
			if(n.isAlive()) total++;
		}
		return total;
	}

	/**
	 * ticks the CA
	 */
	@Override
	public void setNextScene() {
		updateGrid();
	}


	/**
	 * resets the CA
	 */
	@Override
	public void reset() {
		getLifeScene();
	}


	/**
	 * sets curGrid to be equal to nextGrid
	 */
	private void resetCur(){
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