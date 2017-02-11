package cellsociety_team06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import XMLReads.watorReads;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import subUnits.Blank;
import subUnits.Predator;
import subUnits.Prey;

public class watorModel extends Model {
	
	private int across;
	private int down;
	private List<List<Unit>> curGrid;
	private List<List<Unit>> nextGrid;
	Random rand;
	private int width;
	private List<List<Integer>> availablePrey;
	private List<List<Integer>> availableBlank;
	private int startingEnergy = 5;
	int counter = 0;
	watorReads reads;
	private int height;
	private Group root;
	private int size;
	private char predator = 'R';
	private char prey = 'Y';
	
	
	public watorModel(Stage s, Timeline t, ResourceBundle r, int height, int width, int sze){
		super(s,t,r);
		size = sze;   
		this.height = height;
		this.width = width;
		root = new Group();
		start();
	}
	
	private void start(){
		rand = new Random();
		reads = new watorReads(size);
		down = reads.height();
		across = reads.width();
		curGrid = new ArrayList<>();
		nextGrid = new ArrayList<>();
		availablePrey = new ArrayList<>();
		availableBlank = new ArrayList<>();
		counter = 0;
		int startingEnergy = 5;
		getWatorScene();
	}
	
	private void getWatorScene(){
		for(int i = 0; i < down; i++){
			List<Unit> startList = new ArrayList<Unit>();
			List<Unit> blankList = new ArrayList<Unit>();
			curGrid.add(startList);
			nextGrid.add(blankList);
			for(int j = 0; j < across; j++){
				if(reads.get(i, j) == predator){
					startList.add(new Prey((width * i)/down, (height * j)/across, width/down, height/across, 0));
				}
				else if(reads.get(i, j) == prey){
					startList.add(new Predator((width * i)/down, (height * j)/across,
							width/down, height/across, startingEnergy, 0));
				}
				else{
					startList.add(new Blank((width * i)/down, (height * j)/across, width/down, height/across));
				}
				blankList.add(new Blank((width * i)/down, (height * j)/across, width/down, height/across));
				root.getChildren().add(startList.get(j));
			}
		}
	}
	
	private void handleKeyInput(KeyCode code){
		if(code == KeyCode.SPACE){
			updateGrid();
		}
	}

	public void updateGrid(){
		updatePred();
		updatePrey();
	}
	
	private void updatePred(){
		resetAvailablePrey(curGrid);
		resetAvailableBlanks(curGrid);
		Map<List<Integer>, List<Integer>> preyMap = new HashMap<>();
		Map<List<Integer>, List<Integer>> blankMap = new HashMap<>();
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				List<Integer> place = new ArrayList<Integer>(Arrays.asList(i, j));
				Unit curr = curGrid.get(i).get(j);
				root.getChildren().remove(curr);
				nextGrid.get(i).set(j,curGrid.get(i).get(j));
				root.getChildren().add(nextGrid.get(i).get(j));
				if(!curr.isPredator()) continue;
				else{
					if(hasPreyNeighbors(i, j)){
						preyMap.put(place, getPreyNeighbors(i, j));
					}
					else if(hasBlankNeighbors(i, j)){
						blankMap.put(place, getBlankNeighbors(i, j));
					}
				}
			}
		}
		eatPrey(preyMap);
		movePred(blankMap);
		preyMap.clear();
		curGrid = nextGrid;
	}
	
	
	private void updatePrey(){
		resetAvailableBlanks(curGrid);
		Map<List<Integer>, List<Integer>> blankMap = new HashMap<>();
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				List<Integer> place = new ArrayList<Integer>(Arrays.asList(i, j));
				Unit curr = curGrid.get(i).get(j);
				root.getChildren().remove(curr);
				nextGrid.get(i).set(j, curGrid.get(i).get(j));
				root.getChildren().add(nextGrid.get(i).get(j));
				if(curr.isPrey()){
					if(hasBlankNeighbors(i, j)){
						blankMap.put(place, getBlankNeighbors(i, j));
					}
				}
				else{
					continue;
				}
			}
		}
		movePrey(blankMap);
		curGrid = nextGrid;
	}
	
	private void eatPrey(Map<List<Integer>, List<Integer>> map){
		for(List<Integer> place: map.keySet()){
			eatPrey(place.get(0), place.get(1), map.get(place).get(0), map.get(place).get(1));
		}
	}
	
	private void eatPrey(Integer a, Integer b, Integer c, Integer d){
		Predator p = (Predator) curGrid.get(a).get(b);
		root.getChildren().removeAll(nextGrid.get(a).get(b), nextGrid.get(c).get(d));
		nextGrid.get(a).set(b, new Blank((width * a)/down, (height * b)/across, width/down, height/across));
		nextGrid.get(c).set(d, new Predator((width * c)/down, (height * d)/across, width/down, height/across,
				p.getEnergy() + p.ePerEat() - 1, p.getWalked() + 1));
		if(p.canBirth()){
			nextGrid.get(a).set(b, new Predator((width * a)/down, (height * b)/across, width/down,
					height/across, startingEnergy, 0));
			nextGrid.get(c).set(d, new Predator((width * c)/down, (height * d)/across, width/down, height/across,
					p.getEnergy() + p.ePerEat() - 1, 0));
		}
		root.getChildren().addAll(nextGrid.get(a).get(b), nextGrid.get(c).get(d));
	}
	
	private void movePred(Map<List<Integer>, List<Integer>> map){
		for(List<Integer> place: map.keySet()){
			movePred(place.get(0), place.get(1), map.get(place).get(0), map.get(place).get(1));
		}
	}
	
	private void movePrey(Map<List<Integer>, List<Integer>> map){
		for(List<Integer> place: map.keySet()){
			movePrey(place.get(0), place.get(1), map.get(place).get(0), map.get(place).get(1));
		}
	}
	
	private void movePrey(Integer a, Integer b, Integer c, Integer d){
		Prey p = (Prey) curGrid.get(a).get(b);
		root.getChildren().removeAll(nextGrid.get(a).get(b), nextGrid.get(c).get(d));
		if(p.canBirth()){
			nextGrid.get(a).set(b, new Prey((width * a)/down, (height * b)/across, width/down, height/across, 0));
			nextGrid.get(c).set(d, new Prey((width * c)/down, (height * d)/across, width/down, height/across, 0));
		}
		else{
			nextGrid.get(a).set(b, new Blank((width * a)/down, (height * b)/across, width/down, height/across));
			nextGrid.get(c).set(d, new Prey((width * c)/down, (height * d)/across, width/down, height/across, p.getWalked() + 1));
		}
		root.getChildren().addAll(nextGrid.get(a).get(b), nextGrid.get(c).get(d));
	}
	
	private void movePred(Integer a, Integer b, Integer c, Integer d){
		Predator p = (Predator) curGrid.get(a).get(b);
		root.getChildren().removeAll(nextGrid.get(a).get(b), nextGrid.get(c).get(d));
		nextGrid.get(a).set(b, new Blank((width * a)/down, (height * b)/across, width/down, height/across));
		nextGrid.get(c).set(d, new Predator((width * c)/down, (height * d)/across, width/down, height/across,
				p.getEnergy() - 1, p.getWalked() + 1));
		if(p.canBirth()){
			nextGrid.get(a).set(b, new Predator((width * a)/down, (height * b)/across,
					width/down, height/across, startingEnergy, 0));
			nextGrid.get(c).set(d, new Predator((width * c)/down, (height * d)/across, width/down, height/across,
					p.getEnergy() - 1, 0));
		}
		if(p.removeEnergy()){
			nextGrid.get(c).set(d, new Blank((width * c)/down, (height * d)/across, width/down, height/across));
		}
		root.getChildren().addAll(nextGrid.get(a).get(b), nextGrid.get(c).get(d));
	}
	
	private void resetAvailablePrey(List<List<Unit>> grid){
		availablePrey.clear();
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				if(grid.get(i).get(j).isPrey()){
					List<Integer> place = new ArrayList<Integer>(Arrays.asList(i, j));
					availablePrey.add(place);
				}
			}
		}
	}
	
	private void resetAvailableBlanks(List<List<Unit>> grid){
		availableBlank.clear();
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				if(grid.get(i).get(j).isBlank()){
					List<Integer> place = new ArrayList<Integer>(Arrays.asList(i, j));
					availableBlank.add(place);
				}
			}
		}
	}
	
	private boolean hasPreyNeighbors(int i, int j){
		int[] move1 = {-1, -1, -1, 0, 0, 1, 1, 1};
		int[] move2 = {0, 1, -1, 1, -1, 0, 1, -1};		
		
		for(int x = 0; x < move1.length; x++){
			int newI = i + move1[x];
			int newJ = j + move2[x];
			List<Integer> move = new ArrayList<Integer>(Arrays.asList(newI, newJ));
			if (availablePrey.contains(move)) return true;
		}
		return false;
	}
	
	private boolean hasBlankNeighbors(int i, int j){
		int[] move1 = {-1, -1, -1, 0, 0, 1, 1, 1};
		int[] move2 = {0, 1, -1, 1, -1, 0, 1, -1};		
		
		for(int x = 0; x < move1.length; x++){
			int newI = i + move1[x];
			int newJ = j + move2[x];
			List<Integer> move = new ArrayList<Integer>(Arrays.asList(newI, newJ));
			if (availableBlank.contains(move)) return true;
		}
		return false;
	}
	
	private List<Integer> getPreyNeighbors(int i, int j){
		int[] move1 = {-1, -1, -1, 0, 0, 1, 1, 1};
		int[] move2 = {0, 1, -1, 1, -1, 0, 1, -1};		
		List<List<Integer>> list = new ArrayList<>();
		for(int x = 0; x < move1.length; x++){
			int newI = i + move1[x];
			int newJ = j + move2[x];
			List<Integer> move = new ArrayList<Integer>(Arrays.asList(newI, newJ));
			if(newI >= 0 && newI < down && newJ >= 0 && newJ < across){
				if (availablePrey.contains(move)){
					list.add(move);
				}
			}
		}
		List<Integer> picked = list.get(rand.nextInt(list.size()));
		availablePrey.remove(picked);
		return picked;
	}

	private List<Integer> getBlankNeighbors(int i, int j){
		int[] move1 = {-1, -1, -1, 0, 0, 1, 1, 1};
		int[] move2 = {0, 1, -1, 1, -1, 0, 1, -1};		
		List<List<Integer>> list = new ArrayList<>();
		for(int x = 0; x < move1.length; x++){
			int newI = i + move1[x];
			int newJ = j + move2[x];
			List<Integer> move = new ArrayList<Integer>(Arrays.asList(newI, newJ));
			if(newI >= 0 && newI < down && newJ >= 0 && newJ < across){
				if (availableBlank.contains(move)){
					list.add(move);
				}
			}
		}
		List<Integer> picked = list.get(rand.nextInt(list.size()));
		availableBlank.remove(picked);
		return picked;
	}
	

	@Override
	public void setNextScene() {
		updateGrid();
	}

	@Override
	public Group getRoot() {
		return root;
	}

	@Override
	public void reset() {
		start();
	}

}

