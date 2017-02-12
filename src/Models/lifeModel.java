/**
 * Written by Gideon Pfeffer
 * Runs all necessary measurements for the life model
 */
package Models;

import Unit.Unit;
import cellsociety_team06.Grid;
import cellsociety_team06.Model;
import subUnits.Alive;
import subUnits.Blank;

import java.util.Collection;

public class lifeModel extends Model {
	
	/**
	 * makes an instance of the life model
	 */
	public lifeModel(Grid curr, Grid next, Grid init){
		super(curr, next, init);
	}
	
	/**
	 * goes through a tick of the CA, adding life/blank units as necessary
	 * the nextGrid
	 */
	public void updateGrid(){
		for(int i = 0; i < curGrid.rows(); i++){
			for(int j = 0; j < curGrid.cols(); j++){
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
	
	/**
	 * @returns the number of alive units
	 */
	@Override
	public int getUnitA() {
		return (curGrid.getInstances(new Alive()).size());
	}

	/**
	 * @returns the number of blank
	 */
	@Override
	public int getUnitB() {
		return (curGrid.getInstances(new Blank()).size());
	}
	
}