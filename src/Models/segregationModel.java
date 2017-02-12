package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Stack;

import Unit.Unit;
import XMLReads.segReads;
import cellsociety_team06.Grid;
import cellsociety_team06.Model;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import subGrids.hexGrid;
import subGrids.squareGrid;
import subGrids.triangularGrid;
import subUnits.Blank;
import subUnits.Predator;
import subUnits.Type1;
import subUnits.Type2;

public class segregationModel extends Model {

	private int across;
	private int down;
	private Random rand = new Random();
	private List<Integer[]> available = new ArrayList<>();
	private double satisfactionConstant = 0.7;
	private Stack<Integer> myStack;
	private segReads reads;
	private int size;
	private int totalBlank;
	private int lastSize;
	private Grid initial;
	
	/**
	 * @param s should be factored out by Faith
	 * @param t should be factored out by Faith
	 * @param r should be factored out by Faith
	 * @param width width of the stage
	 * @param height height of the stage
	 * @param size which of the three XML's should be read 
	 */
	
	public segregationModel(Grid curr, Grid next, int unitShape, int height){
		curGrid = curr;
		nextGrid = next;
		down = curGrid.rows();
		across = curGrid.cols();
		
		if (unitShape == 1){
			initial = new squareGrid(curr.rows(), curr.cols(), height/curr.rows());
		} else if (unitShape == 2){
			initial = new triangularGrid(curr.rows(), curr.cols(), height/curr.rows());
		} else {
			initial = new hexGrid(curr.rows(), curr.cols(), height/curr.rows());
		}
		for (int i=0; i<curr.rows(); i++){
			for (int j=0; j<curr.cols(); j++){
				initial.setUnit(i, j, curr.getUnit(i, j));
			}
		}
		
		start();
	}
	
	/**
	 * sets the initializations of all variables necessary for the CA
	 */
	private void start(){
		totalBlank = 0;
		available = new ArrayList<>();
		myStack = new Stack<>();
		myStack.push(across * down);
		resetRoot();
	}

	/**
	 * runs through a tick of the CA
	 * stores the movements of the blank and unhappy tiles in a map
	 */
	@Override
	public void updateGrid(){
		resetAvailable();
		if(!myStack.isEmpty()) lastSize = myStack.pop();
		double av = available.size()/((double) (across * down));
		double visited = 0.0;
		Map<Integer[], Integer[]> map = new HashMap<>();
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				visited++;
				Integer[] place = {i, j};
				if((av * visited < 1) && lastSize > (1.0/7.0) * totalBlank) continue;
				if(curGrid.getUnit(i, j).isType1() || curGrid.getUnit(i, j).isType2()){
					if(!isHappy(i, j) && spaceAvailable()){
						visited = (double) rand.nextInt(2);
						map.put(place, available.remove(rand.nextInt(available.size())));
					}
				}
			}
		}
		myStack.push(map.size());
		swapGrids(map);
		map.clear();
	}
	
	/**
	 * @param map shows the indices that should be swapped
	 * method swaps the two units
	 */
	private void swapGrids(Map<Integer[], Integer[]> map){
		for(Integer[] place: map.keySet()){
			curGrid.swap(place[0], place[1], map.get(place)[0], map.get(place)[1]);
		}
	}
	
	/**
	 * resets available to the locations of the blank units
	 */
	private void resetAvailable(){
		Unit u = new Unit();
		available.clear();
		available.addAll(curGrid.getInstances(new Blank(u)).keySet());	
	}
	
	/**
	 * @return whether or not there are still blanks that can be swapped
	 */
	private boolean spaceAvailable(){
		return !(available.isEmpty());
	}
	
	/**
	 * @param i row of the grid
	 * @param j col of the grid
	 * @return whether the unit is happy with
	 * relation to satisfaction constant
	 */
	private boolean isHappy(int i , int j){
		double total = 0.0;
		int blanks = 0;
		Unit cur = curGrid.getUnit(i, j);
		for(Unit u:curGrid.getNeighbors(i, j).values()){
			if(u.getState() == cur.getState()) total++;
			if(u.isBlank()) blanks++;
		}
		return total/((double)curGrid.getNeighbors(i, j).size() - blanks) > satisfactionConstant;
	}

	/**
	 * ticks the CA once
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
		curGrid = initial;
		resetRoot();
	}

	@Override
	public int getType1Units() {
		return (curGrid.getInstances(new Type1()).size());
	}

	@Override
	public int getType2Units() {
		return (curGrid.getInstances(new Type2()).size());
	}

}
