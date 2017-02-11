package Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import XMLReads.watorReads;
import cellsociety_team06.Model;
import cellsociety_team06.Unit;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.stage.Stage;
import subGrids.hexGrid;
import subGrids.squareGrid;
import subUnits.Blank;
import subUnits.Predator;
import subUnits.Prey;

public class watorModel extends Model {
	
	private int across;
	private int down;
	Random rand;
	private List<List<Integer>> takenPrey;
	private List<List<Integer>> takenBlank;
	Map<Integer[], Integer[]> preyMap;
	Map<Integer[], Integer[]> blankMap;
	
	private int startingEnergy = 5;
	int counter = 0;
	watorReads reads;
	private int size;
	
	
	public watorModel(Stage s, Timeline t, ResourceBundle r, int height, int width, int sze){
		super(s,t,r);
		size = sze;
		root = new Group();
		takenPrey = new ArrayList<>();
		takenBlank = new ArrayList<>();
		rand = new Random();
		reads = new watorReads(size);
		down = reads.height();
		across = reads.width();
		curGrid = new hexGrid(down, across, height/down);
		start();
	}
	
	private void start(){
		preyMap = new HashMap<>();
		blankMap = new HashMap<>();
		counter = 0;
		getWatorScene();
	}
	
	private void getWatorScene(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				if(reads.get(i, j) == 'R'){
					curGrid.setUnit(i, j, new Prey(0, curGrid.getUnit(i, j)));
				}
				else if(reads.get(i, j) == 'Y'){
					curGrid.setUnit(i, j, new Predator(startingEnergy, 0, curGrid.getUnit(i, j)));
				}
				else{
					curGrid.setUnit(i, j, new Blank(curGrid.getUnit(i, j)));
				}
			}
		}
		resetRoot();
	}

	public void updateGrid(){
		counter++;
		takenPrey.clear();
		takenBlank.clear();
		preyMap.clear();
		blankMap.clear();
		updatePred();
		eatPrey(preyMap);
		movePred(blankMap);
		preyMap.clear();
		blankMap.clear();
		takenBlank.clear();
		updatePrey();
		movePrey(blankMap);
		resetRoot();
	}
	
	private void updatePred(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				Integer[] place = new Integer[] {i,j};
				if(!curGrid.getUnit(i, j).isPredator()) continue;
				else{
					List<Integer[]> prey = getPreyNeighbors(i, j);
					List<Integer[]> blank = getBlankNeighbors(i, j);
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
	
	
	private void updatePrey(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				Integer[] place = new Integer[] {i,j};
				if(curGrid.getUnit(i, j).isPrey()){
					List<Integer[]> blank = getBlankNeighbors(i, j);
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
	
	private void eatPrey(Map<Integer[], Integer[]> map){
		for(Integer[] place: map.keySet()){
			eatPrey(place[0], place[1], map.get(place)[0], map.get(place)[1]);
		}
	}
	
	private void eatPrey(Integer a, Integer b, Integer c, Integer d){
		Predator p = (Predator) curGrid.getUnit(a, b);
		if(curGrid.getUnit(c, d).isPredator()) return;
		curGrid.setUnit(a, b, new Blank(curGrid.getUnit(a, b)));
		curGrid.setUnit(c, d, new Predator(p.getEnergy() + p.ePerEat() - 1,
				p.getWalked() + 1, curGrid.getUnit(c, d)));
		if(p.canBirth()){
			curGrid.setUnit(a, b, new Predator(startingEnergy, 0, curGrid.getUnit(a, b)));
			Predator newPred = (Predator) curGrid.getUnit(c, d);
			newPred.resetWalked();
		}
	}
	
	private void movePred(Map<Integer[], Integer[]> map){
		for(Integer[] place: map.keySet()){
			movePred(place[0], place[1], map.get(place)[0], map.get(place)[1]);
		}
	}
	
	private void movePrey(Map<Integer[], Integer[]> map){
		for(Integer[] place: map.keySet()){
			movePrey(place[0], place[1], map.get(place)[0], map.get(place)[1]);
		}
	}
	
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
	
	private void movePred(Integer a, Integer b, Integer c, Integer d){
		Predator p = (Predator) curGrid.getUnit(a,b);
		if(curGrid.getUnit(c, d).isPredator()) return;
		curGrid.setUnit(a, b, new Blank(curGrid.getUnit(a, b)));
		curGrid.setUnit(c, d, new Predator(p.getEnergy() - 1,
				p.getWalked() + 1, curGrid.getUnit(c, d)));
		if(p.canBirth()){
			curGrid.setUnit(a, b, new Predator(startingEnergy, 0, curGrid.getUnit(a, b)));
			Predator newPred = (Predator) curGrid.getUnit(c, d);
			newPred.resetWalked();
		}
		if(p.removeEnergy()){
			curGrid.setUnit(c, d, new Blank(curGrid.getUnit(c, d)));		}
	}
	
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
	
	private List<Integer[]> getBlankNeighbors(int i, int j){
		List<Integer[]> blanks = hasNeighbors(i, j, new Blank());
		List<Integer[]> toReturn = new ArrayList<>();
		for(Integer[] blank:blanks){
			if(!takenBlank.contains(Arrays.asList(blank))){
				toReturn.add(blank);
			}
		}
		return toReturn;
	}
	
	private List<Integer[]> getPreyNeighbors(int i, int j){
		List<Integer[]> preys = hasNeighbors(i, j, new Prey());
		List<Integer[]> toReturn = new ArrayList<>();
		for(Integer[] prey:preys){
			if(!takenPrey.contains(Arrays.asList(prey))){
				toReturn.add(prey);
			}
		}
		return toReturn;
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
	protected void resetCur() {
		//Not necessary for this algorithm
	}
}

