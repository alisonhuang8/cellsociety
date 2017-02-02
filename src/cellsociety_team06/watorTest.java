package cellsociety_team06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import subUnits.Blank;
import subUnits.Predator;
import subUnits.Prey;
import subUnits.Type1;



public class watorTest extends Application{

	private Stage window;
	private int across = 25;
	private int down = 25;
	private Unit[][] curGrid = new Unit[down][across];
	private Unit[][] nextGrid = new Unit[down][across];
	Random rand = new Random();
	private double blankpct = 0.1;
	private double predpct = 0.02;
	private double preypct = 0.1;
	private int width = 500;
	private ArrayList<ArrayList<Integer>> availablePrey = new ArrayList<>();
	private ArrayList<ArrayList<Integer>> availableBlank = new ArrayList<>();
	private int startingEnergy = 5;
	private int counter = 0;
	
	private int height = 500;
	private Group root = new Group();
	private double satisfactionConstant = 0.6;
	Stack<Integer> myStack = new Stack();
	
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setResizable(false);
		window.setScene(getWatorScene());
		window.show();
		myStack.push(across * down);
	}
	
	
	private Scene getWatorScene(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				int r = rand.nextInt(100);
				if(r < (preypct * 100)){
					curGrid[i][j] = new Prey((width * i)/down, (height * j)/across, width/down, height/across, 0);
				}
				else if(r < (preypct + predpct) * 100){
					curGrid[i][j] = new Predator((width * i)/down, (height * j)/across,
							width/down, height/across, startingEnergy, 0);
				}
				else{
					curGrid[i][j] = new Blank((width * i)/down, (height * j)/across, width/down, height/across);
				}
				root.getChildren().add(curGrid[i][j]);
			}
		}
		Scene scene = new Scene(root, width, height);
		scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		return scene;
	}
	
	private void handleKeyInput(KeyCode code){
		if(code == KeyCode.SPACE){
			updateGrid();
		}
	}

	private void updateGrid(){
//		counter++;
//		if(counter % 2 ==0) updatePred();
//		else updatePrey();
		updatePred();
		updatePrey();
	}
	
	private void updatePred(){
		resetAvailablePrey(curGrid);
		resetAvailableBlanks(curGrid);
		HashMap<ArrayList<Integer>, ArrayList<Integer>> preyMap = new HashMap<>();
		HashMap<ArrayList<Integer>, ArrayList<Integer>> blankMap = new HashMap<>();
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				ArrayList<Integer> place = new ArrayList<Integer>(Arrays.asList(i, j));
				Unit curr = curGrid[i][j];
				root.getChildren().remove(curr);
				nextGrid[i][j] = curGrid[i][j];
				root.getChildren().add(nextGrid[i][j]);
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
		nextGrid = new Unit[down][across];
	}
	
	
	private void updatePrey(){
		resetAvailableBlanks(curGrid);
		HashMap<ArrayList<Integer>, ArrayList<Integer>> blankMap = new HashMap<>();
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				ArrayList<Integer> place = new ArrayList<Integer>(Arrays.asList(i, j));
				Unit curr = curGrid[i][j];
				root.getChildren().remove(curr);
				nextGrid[i][j] = curGrid[i][j];
				root.getChildren().add(nextGrid[i][j]);
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
		nextGrid = new Unit[down][across];
	}
	
	private void eatPrey(HashMap<ArrayList<Integer>, ArrayList<Integer>> map){
		for(ArrayList<Integer> place: map.keySet()){
			eatPrey(place.get(0), place.get(1), map.get(place).get(0), map.get(place).get(1));
		}
	}
	
	private void eatPrey(Integer a, Integer b, Integer c, Integer d){
		Predator p = (Predator) curGrid[a][b];
		root.getChildren().removeAll(nextGrid[a][b], nextGrid[c][d]);
		nextGrid[a][b] = new Blank((width * a)/down, (height * b)/across, width/down, height/across);
		nextGrid[c][d] = new Predator((width * c)/down, (height * d)/across, width/down, height/across,
				p.getEnergy() + p.ePerEat() - 1, p.getWalked() + 1);
		if(p.canBirth()){
			nextGrid[a][b] = new Predator((width * a)/down, (height * b)/across, width/down,
					height/across, startingEnergy, 0);
			nextGrid[c][d] = new Predator((width * c)/down, (height * d)/across, width/down, height/across,
					p.getEnergy() + p.ePerEat() - 1, 0);
		}
		root.getChildren().addAll(nextGrid[a][b], nextGrid[c][d]);
	}
	
	private void movePred(HashMap<ArrayList<Integer>, ArrayList<Integer>> map){
		for(ArrayList<Integer> place: map.keySet()){
			movePred(place.get(0), place.get(1), map.get(place).get(0), map.get(place).get(1));
		}
	}
	
	private void movePrey(HashMap<ArrayList<Integer>, ArrayList<Integer>> map){
		for(ArrayList<Integer> place: map.keySet()){
			movePrey(place.get(0), place.get(1), map.get(place).get(0), map.get(place).get(1));
		}
	}
	
	private void movePrey(Integer a, Integer b, Integer c, Integer d){
		Prey p = (Prey) curGrid[a][b];
		root.getChildren().removeAll(nextGrid[a][b], nextGrid[c][d]);
		if(p.canBirth()){
			nextGrid[a][b] = new Prey((width * a)/down, (height * b)/across, width/down, height/across, 0);
			nextGrid[c][d] = new Prey((width * c)/down, (height * d)/across, width/down, height/across, 0);
		}
		else{
			nextGrid[a][b] = new Blank((width * a)/down, (height * b)/across, width/down, height/across);
			nextGrid[c][d] = new Prey((width * c)/down, (height * d)/across, width/down, height/across, p.getWalked() + 1);
		}
		root.getChildren().addAll(nextGrid[a][b], nextGrid[c][d]);
	}
	
	private void movePred(Integer a, Integer b, Integer c, Integer d){
		Predator p = (Predator) curGrid[a][b];
		root.getChildren().removeAll(nextGrid[a][b], nextGrid[c][d]);
		nextGrid[a][b] = new Blank((width * a)/down, (height * b)/across, width/down, height/across);
		nextGrid[c][d] = new Predator((width * c)/down, (height * d)/across, width/down, height/across,
				p.getEnergy() - 1, p.getWalked() + 1);
		if(p.canBirth()){
			nextGrid[a][b] = new Predator((width * a)/down, (height * b)/across,
					width/down, height/across, startingEnergy, 0);
			nextGrid[c][d] = new Predator((width * c)/down, (height * d)/across, width/down, height/across,
					p.getEnergy() - 1, 0);
		}
		if(p.removeEnergy()){
			nextGrid[c][d] = new Blank((width * c)/down, (height * d)/across, width/down, height/across);
		}
		root.getChildren().addAll(nextGrid[a][b], nextGrid[c][d]);
	}
	
	private void resetAvailablePrey(Unit[][] grid){
		availablePrey.clear();
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				if(grid[i][j].isPrey()){
					ArrayList<Integer> place = new ArrayList<Integer>(Arrays.asList(i, j));
					availablePrey.add(place);
				}
			}
		}
	}
	
	private void resetAvailableBlanks(Unit[][] grid){
		availableBlank.clear();
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				if(grid[i][j].isBlank()){
					ArrayList<Integer> place = new ArrayList<Integer>(Arrays.asList(i, j));
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
			ArrayList<Integer> move = new ArrayList<Integer>(Arrays.asList(newI, newJ));
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
			ArrayList<Integer> move = new ArrayList<Integer>(Arrays.asList(newI, newJ));
			if (availableBlank.contains(move)) return true;
		}
		return false;
	}
	
	private ArrayList<Integer> getPreyNeighbors(int i, int j){
		int[] move1 = {-1, -1, -1, 0, 0, 1, 1, 1};
		int[] move2 = {0, 1, -1, 1, -1, 0, 1, -1};		
		ArrayList<ArrayList<Integer>> list = new ArrayList<>();
		for(int x = 0; x < move1.length; x++){
			int newI = i + move1[x];
			int newJ = j + move2[x];
			ArrayList<Integer> move = new ArrayList<Integer>(Arrays.asList(newI, newJ));
			if(newI >= 0 && newI < down && newJ >= 0 && newJ < across){
				if (availablePrey.contains(move)){
					list.add(move);
				}
			}
		}
		ArrayList<Integer> picked = list.get(rand.nextInt(list.size()));
		availablePrey.remove(picked);
		return picked;
	}

	private ArrayList<Integer> getBlankNeighbors(int i, int j){
		int[] move1 = {-1, -1, -1, 0, 0, 1, 1, 1};
		int[] move2 = {0, 1, -1, 1, -1, 0, 1, -1};		
		ArrayList<ArrayList<Integer>> list = new ArrayList<>();
		for(int x = 0; x < move1.length; x++){
			int newI = i + move1[x];
			int newJ = j + move2[x];
			ArrayList<Integer> move = new ArrayList<Integer>(Arrays.asList(newI, newJ));
			if(newI >= 0 && newI < down && newJ >= 0 && newJ < across){
				if (availableBlank.contains(move)){
					list.add(move);
				}
			}
		}
		ArrayList<Integer> picked = list.get(rand.nextInt(list.size()));
		availableBlank.remove(picked);
		return picked;
	}
	
	
}
