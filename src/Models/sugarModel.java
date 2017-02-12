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

	public sugarModel(Grid curr, Grid next, Grid init) {
		curGrid = curr;
		nextGrid = next;
		initialGrid = init;
	}

	private void updateRoot() {
		root.getChildren().clear();
		root.getChildren().addAll(curGrid.getChildren());
	}

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
					Integer[] move = pickRandomSugar(i, j);
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
		updateRoot();
	}

	private void move() {
		for (Integer[] place : agentMoves.keySet()){
			move(place[0], place[1], agentMoves.get(place)[0], agentMoves.get(place)[1]);
		}
	}

	private void move(int agRow, int agCol, int sugRow, int sugCol) {
		Agent a = (Agent) curGrid.getUnit(agRow, agCol);
		Sugar s = (Sugar) curGrid.getUnit(sugRow, sugCol);
		a.metabolize();
		if (!a.isDead()){
			a.pickedUp(s.getSugar());
			curGrid.swap(agRow, agCol, sugRow, sugCol);
		}
		curGrid.setUnit(agRow, agCol, new Sugar(curGrid.getUnit(agRow, agCol), 0));
		updateRoot();

	}

	private Integer[] pickRandomSugar(int row, int col) {
		Agent a = (Agent) curGrid.getUnit(row, col);
		Map<Integer[], Unit> map = curGrid.getInstances(new Sugar());
		List<Integer[]> list = new ArrayList<>();
		for (Integer[] place : map.keySet()) {
			if (isValid(row, col, place[0], place[1], a) && !eaten.contains(Arrays.asList(place))) {
				list.add(place);
			}
		}
		if (list.size() == 0)
			return null;
		return list.get(rand.nextInt(list.size()));
	}

	private boolean isValid(int agentRow, int agentCol, int sugarRow, int sugarCol, Agent a) {
		if (agentRow != sugarRow && agentCol != sugarCol)
			return false;
		return (Math.abs(agentRow - sugarRow)
				+ Math.abs(agentCol - sugarCol) <= a.agentVision());
	}

	@Override
	public void setNextScene() {
		updateGrid();
	}

	@Override
	public int getType1Units() {
		return curGrid.getInstances(new Sugar()).size();
	}

	@Override
	public int getType2Units() {
		return curGrid.getInstances(new Agent()).size();
	}
}