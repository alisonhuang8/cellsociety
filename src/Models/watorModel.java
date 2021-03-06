/**
 * Written by Gideon Pfeffer
 * Runs all necessary measures for the Wator Simulation
 */

package Models;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import Unit.Unit;
import cellsociety_team06.Grid;
import cellsociety_team06.Model;
import subUnits.Blank;
import subUnits.Predator;
import subUnits.Prey;

public class watorModel extends Model {
	private Random rand;
	private List<List<Integer>> takenPrey;
	private List<List<Integer>> takenBlank;
	private Map<Integer[], Integer[]> preyMap;
	private Map<Integer[], Integer[]> blankMap;
	
	private static final int STARTING_ENERGY = 5;
	
	/**
	 * makes an instance of the wator model
	 */
	public watorModel(Grid curr, Grid next, Grid init){
		super(curr, next, init);
		rand = new Random();
		start();
	}
	
	/**
	 * initializes the variables needed for the CA simulation
	 */
	private void start(){
		takenPrey = new ArrayList<>();
		takenBlank = new ArrayList<>();
		preyMap = new HashMap<>();
		blankMap = new HashMap<>();
		resetRoot();
	}
	

	/**
	 * updates the grid one tick
	 */
	public void updateGrid(){
		clearStructures();
		updatePred();
		eatPrey(preyMap);
		movePred(blankMap);
		clearStructures();
		updatePrey();
		movePrey(blankMap);
		resetRoot();
	}
	
	/**
	 * clears the data structures used for the model
	 */
	private void clearStructures(){
		takenPrey.clear();
		takenBlank.clear();
		preyMap.clear();
		blankMap.clear();
	}
	
	/**
	 * figures out the next move for all of the predators
	 * and stores the swaps in a prey and blank map
	 */
	private void updatePred(){
		for(int i = 0; i < curGrid.rows(); i++){
			for(int j = 0; j < curGrid.cols(); j++){
				Integer[] place = new Integer[] {i,j};
				if(!curGrid.getUnit(i, j).isPredator()) continue;
				else{
					List<Integer[]> prey = getNeighbors(i, j, new Prey(), takenPrey);
					List<Integer[]> blank = getNeighbors(i, j, new Blank(), takenBlank);
					if(prey.size() > 0){
						Integer[] preyLoc = prey.get(rand.nextInt(prey.size()));
						takenPrey.add(Arrays.asList(preyLoc));
						preyMap.put(place, preyLoc);
					}
					else if(blank.size() > 0){
						Integer[] blankLoc = blank.get(rand.nextInt(blank.size()));
						takenBlank.add(Arrays.asList(blankLoc));
						blankMap.put(place, blankLoc);
					}
				}
			}
		}
	}
	
	/**
	 * figures out the next move for all of the prey
	 * and stores the swaps in a blank map
	 */
	private void updatePrey(){
		for(int i = 0; i < curGrid.rows(); i++){
			for(int j = 0; j < curGrid.cols(); j++){
				Integer[] place = new Integer[] {i,j};
				if(curGrid.getUnit(i, j).isPrey()){
					List<Integer[]> blank = getNeighbors(i, j, new Blank(), takenBlank);
					if(blank.size() > 0){
						Integer[] blankLoc = blank.get(rand.nextInt(blank.size()));
						takenBlank.add(Arrays.asList(blankLoc));
						blankMap.put(place, blank.get(rand.nextInt(blank.size())));
					}
				}
				else{
					continue;
				}
			}
		}
	}
	
	/**
	 * sends the pred eatPrey for swapping
	 */
	private void eatPrey(Map<Integer[], Integer[]> map){
		for(Integer[] place: map.keySet()){
			movePred(place[0], place[1], map.get(place)[0], map.get(place)[1], true);
		}
	}
	
	/**
	 * sends the pred movePred for swapping
	 */
	private void movePred(Map<Integer[], Integer[]> map){
		for(Integer[] place: map.keySet()){
			movePred(place[0], place[1], map.get(place)[0], map.get(place)[1], false);
		}
	}
	
	/**
	 * @param map takes in the mappings of swaps
	 * sends them to movePrey for swapping
	 */
	private void movePrey(Map<Integer[], Integer[]> map){
		for(Integer[] place: map.keySet()){
			movePrey(place[0], place[1], map.get(place)[0], map.get(place)[1]);
		}
	}
	
	/**
	 * @param a row of unit 1
	 * @param b col of unit 1
	 * @param c row of unit 2
	 * @param d col of unit 2
	 * moves the prey giving birth if applicable
	 */
	private void movePrey(Integer a, Integer b, Integer c, Integer d){
		Prey p = (Prey) curGrid.getUnit(a, b);
		if(curGrid.getUnit(c, d).isPrey()) return;
		if(p.canBirth()){
			curGrid.setUnit(a, b, new Prey(0, curGrid.getUnit(a, b)));
			curGrid.setUnit(c, d, new Prey(0, curGrid.getUnit(c, d)));
		}
		else{
			curGrid.setUnit(a, b, new Blank(curGrid.getUnit(a, b)));
			curGrid.setUnit(c, d, new Prey(p.getWalked() + 1, curGrid.getUnit(c, d)));		}
	}
	
	/**
	 * @param a row of unit 1
	 * @param b col of unit 1
	 * @param c row of unit 2
	 * @param d col of unit 2
	 * moves the predators giving birth
	 * or dying if applicable
	 */
	private void movePred(Integer a, Integer b, Integer c, Integer d, boolean isEating){
		Predator p = (Predator) curGrid.getUnit(a,b);
		if(curGrid.getUnit(c, d).isPredator()) return;
		curGrid.setUnit(a, b, new Blank(curGrid.getUnit(a, b)));
		if(!isEating){
		curGrid.setUnit(c, d, new Predator(p.getEnergy() - 1,
				p.getWalked() + 1, curGrid.getUnit(c, d)));
		}
		else{
			curGrid.setUnit(c, d, new Predator(p.getEnergy() + p.ePerEat() - 1,
					p.getWalked() + 1, curGrid.getUnit(c, d)));
		}
		if(p.canBirth()){
			curGrid.setUnit(a, b, new Predator(STARTING_ENERGY, 0, curGrid.getUnit(a, b)));
			Predator newPred = (Predator) curGrid.getUnit(c, d);
			newPred.resetWalked();
		}
		if(p.removeEnergy()){
			curGrid.setUnit(c, d, new Blank(curGrid.getUnit(c, d)));		}
	}
	
	/**
	 * @return a list of local neighbors
	 * that have the same state as the unit passed in
	 */
	private List<Integer[]> hasNeighbors(int row, int col, Unit u){
		List<Integer[]> list = new ArrayList<>();
		Map<Integer[], Unit> neighbors = curGrid.getNeighbors(row, col);
		for(Integer[] place: neighbors.keySet()){
			if(neighbors.get(place).getState() == u.getState()){
				list.add(place);
			}	
		}
		return list;
	}
	
	/**
	 * @returns the neighbors that haven't already
	 * been mapped to be moved on
	 */
	private List<Integer[]> getNeighbors(int i, int j, Unit u, List<List<Integer>> list){
		List<Integer[]> similar = hasNeighbors(i, j, u);
		List<Integer[]> toReturn = new ArrayList<>();
		for(Integer[] sim:similar){
			if(!list.contains(Arrays.asList(sim))){
				toReturn.add(sim);
			}
		}
		return toReturn;
	}
	
	/**
	 * @returns the number of predator units
	 */
	@Override
	public int getUnitA() {
		return (curGrid.getInstances(new Predator()).size());
	}

	/**
	 * @returns the number of prey units
	 */
	@Override
	public int getUnitB() {
		return (curGrid.getInstances(new Prey()).size());
	}
}

