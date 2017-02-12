package Models;

/**
 * Written by Gideon Pfeffer
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import Unit.Unit;
import cellsociety_team06.Grid;
import cellsociety_team06.Model;
import subUnits.sugarScape.Agent;
import subUnits.sugarScape.Sugar;

public class sugarModel extends Model {
	private Random rand = new Random();
	private Map<Integer[], Integer[]> agentMoves = new HashMap<>();
	private List<List<Integer>> eaten = new ArrayList<>();

	/**
	 * makes a new instance of the sugar CA
	 */
	public sugarModel(Grid curr, Grid next, Grid init) {
		super(curr, next, init);
	}

	/**
	 * performs all operations for the simulation
	 */
	public void updateGrid(){
		agentMoves.clear();
		eaten.clear();
		updateAgents();
		move();
	}

	/**
	 * moves the grid forward a step
	 */
	private void updateAgents() {
		for (int i = 0; i < curGrid.rows(); i++) {
			for (int j = 0; j < curGrid.cols(); j++) {
				Integer[] place = { i, j };
				if (curGrid.getUnit(i, j).isAgent()) {
					Integer[] move = pickSugar(i, j);
					if (move == null){
						continue;
					}
					eaten.add(Arrays.asList(move));
					agentMoves.put(place, move);
				} else {
					Sugar s = (Sugar) curGrid.getUnit(i, j);
					s.regen();
				}
			}
		}
		resetRoot();
	}

	/**
	 * moves the sugar agents
	 */
	private void move() {
		for (Integer[] place : agentMoves.keySet()){
			move(place[0], place[1], agentMoves.get(place)[0], agentMoves.get(place)[1]);
		}
	}

	/**
	 * checks if the agent should die
	 * if not, moves
	 */
	private void move(int agRow, int agCol, int sugRow, int sugCol) {
		Agent a = (Agent) curGrid.getUnit(agRow, agCol);
		Sugar s = (Sugar) curGrid.getUnit(sugRow, sugCol);
		a.metabolize();
		if (!a.isDead()){
			a.pickedUp(s.getSugar());
			curGrid.swap(agRow, agCol, sugRow, sugCol);
		}
		curGrid.setUnit(agRow, agCol, new Sugar(curGrid.getUnit(agRow, agCol), 0));
		resetRoot();

	}

	/**
	 * selects the sugar for the agent
	 */
	private Integer[] pickSugar(int row, int col) {
		Integer[] best = {-1, -1};
		Agent a = (Agent) curGrid.getUnit(row, col);
		int bestSugar = 0;
		int minDistance = a.agentVision();
		Map<Integer[], Unit> map = curGrid.getInstances(new Sugar());
		for (Integer[] place : map.keySet()) {
			int curSugar = getSugarValue(place[0], place[1]);
			int curDistance = getDistance(row, col, place[0], place[1]);
			if (isValid(row, col, place[0], place[1], a) && !eaten.contains(Arrays.asList(place))
					&& curSugar >= bestSugar && curDistance <= minDistance) {
				best = place;
				bestSugar= curSugar;
				minDistance = curDistance;
			}
		}
		if(best[0] == -1 && best[1] == -1) return null;
		return best;
	}
	
	/**
	 * returns the sugar value
	 */
	private int getSugarValue(int row, int col){
		Sugar s = (Sugar) curGrid.getUnit(row, col);
		return s.getSugar();
	}
	
	/**
	 * @return the distance between an agent and sugar
	 */
	private int getDistance(int agRow, int agCol, int sugRow, int sugCol){
		return Math.abs(agRow - sugRow) + Math.abs(agCol - sugCol);
	}

	/**
	 * checks if a sugar is a valid move
	 * for the agent
	 */
	private boolean isValid(int agentRow, int agentCol, int sugarRow, int sugarCol, Agent a) {
		if (agentRow != sugarRow && agentCol != sugarCol)
			return false;
		return (Math.abs(agentRow - sugarRow)
				+ Math.abs(agentCol - sugarCol) <= a.agentVision());
	}

	/**
	 * moves the CA forward a tick
	 */
	@Override
	public void setNextScene() {
		updateGrid();
	}

	/**
	 * returns the number of sugar
	 */
	@Override
	public int getType1Units() {
		return curGrid.getInstances(new Sugar()).size();
	}

	/**
	 * returns the number of agents
	 */
	@Override
	public int getType2Units() {
		return curGrid.getInstances(new Agent()).size();
	}
}