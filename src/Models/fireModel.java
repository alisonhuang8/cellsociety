/**
 * Written by Gideon Pfeffer
 * Runs all necessary measurements for the fire model
 */

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
import subGrids.squareGrid;
import subGrids.triangularGrid;
import subUnits.Alive;
import subUnits.Burning;
import subUnits.Burnt;

public class fireModel extends Model {
	
	private int across;
	private int down;
	private Random rand = new Random();
	private double catchChance = 0.7;
	private fireReads reads;
	
	/**
	 * @param s should be factored out by Faith
	 * @param t should be factored out by Faith
	 * @param r should be factored out by Faith
	 * @param width width of the stage
	 * @param height height of the stage
	 * @param size which of the three XML's should be read 
	 */
	public fireModel(int width, int height, int size){
		reads = new fireReads();
		down = reads.height();
		across = reads.width();
		curGrid = new squareGrid(down, across, height/down);
		curGrid.makeTorroidal();
		nextGrid = new squareGrid(down, across, height/down);
		getFireScene();
	}

	/**
	 * Sets up the initial fire scene
	 * should be re-factored into a level generator
	 */
	private void getFireScene(){
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
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
	
	/**
	 * decides which units the fire should spread and spreads it
	 */
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
	
	/**
	 * sets the curGrid to be equal to the NextGrid
	 */
	private void resetCur(){
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
	
	/**
	 * returns the number of direct neighbors that are burning
	 */
	private int getBurningNeighbors(Collection<Unit> neighbors){
		int total = 0;
		for(Unit n:neighbors){
			if(n.isBurning()) total++;
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
		root.getChildren().clear();
		getFireScene();
	}
	
	/**
	 * @returns the number of alive units
	 */
	public int getTreeUnits(){
		return (curGrid.getInstances(new Alive()).size());
	}
	
}