package cellsociety_team06;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import XMLReads.segReads;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import subUnits.Blank;
import subUnits.Type1;
import subUnits.Type2;

public class segregationModel extends Model {

	private int across;
	private int down;
	private List<List<Unit>> curGrid = new ArrayList<>();
	private List<List<Unit>> nextGrid =  new ArrayList<>();
	Random rand = new Random();
	private int width = 500;
	private List<Integer[]> available = new ArrayList<>();
	
	private int height = 500;
	private Group root = new Group();
	private double satisfactionConstant = 0.7;
	Stack<Integer> myStack = new Stack();
	private double totalBlank = 0.0;
	segReads reads;
	
	public segregationModel(Stage s, Timeline t, int height, int width){
		super(s,t);
		this.height = height;
		this.width = width;
		start();
	}
	
	private void start(){
		curGrid = new ArrayList<>();
		nextGrid =  new ArrayList<>();
		available = new ArrayList<>();
		myStack = new Stack();
		reads = new segReads();
		down = reads.height();
		across = reads.width();
		totalBlank = 0.0;
		myStack.push(across * down);
		getSegScene();
	}
	
	private void getSegScene(){
		for(int i = 0; i < down; i++){
			List<Unit> start = new ArrayList<>();
			List<Unit> blank = new ArrayList<>();
			curGrid.add(start);
			nextGrid.add(blank);
			for(int j = 0; j < across; j++){
				
				if(reads.get(i, j) == '0'){
					start.add(new Blank((width * i)/down, (height * j)/across, width/down, height/across));
					totalBlank++;
				}
				else if(reads.get(i, j) == 'A'){
					start.add(new Type1((width * i)/down, (height * j)/across, width/down, height/across));
				}
				else{
					start.add(new Type2((width * i)/down, (height * j)/across, width/down, height/across));
				}
				blank.add(new Blank((width * i)/down, (height * j)/across, width/down, height/across));
				root.getChildren().add(start.get(j));
			}
		}
	}
	
	private void handleKeyInput(KeyCode code){
		if(code == KeyCode.SPACE){
			updateGrid();
		}
	}

	private void updateGrid(){
		double visited = 0;
		int last = myStack.pop();
		resetAvailable(curGrid);
		double av = available.size()/((double) (across * down));
		Map<Integer[], Integer[]> map = new HashMap<>();
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				visited++;
				Integer[] place = {i, j};
				Unit curr = curGrid.get(i).get(j);
				root.getChildren().remove(curr);
				nextGrid.get(i).set(j, curGrid.get(i).get(j));
				root.getChildren().add(nextGrid.get(i).get(j));
				if((av * visited < 1) && last > (1.0/7.0) * totalBlank){
					continue;
				}
				visited = (double) rand.nextInt(2);
				if(curr.isType1()){
					if(!isHappy1(i, j) && spaceAvailable()){
						map.put(place, available.remove(rand.nextInt(available.size())));
					}
				}
				else if(curr.isType2()){
					if(!isHappy2(i, j) && spaceAvailable()){
						map.put(place, available.remove(rand.nextInt(available.size())));
					}
				}
			}
		}
		myStack.push(map.size());
		swapGrids(map);
		map.clear();
	}
	
	private void swapGrids(Map<Integer[], Integer[]> map){
		for(Integer[] place: map.keySet()){
			swap(place[0], place[1], map.get(place)[0], map.get(place)[1]);
		}
		curGrid = nextGrid;
	}
	
	private void swap(Integer a, Integer b, Integer c, Integer d){
		if(nextGrid.get(a).get(b).isType1()) swap1(a, b, c, d);
		else swap2(a, b, c, d);
	}
	
	private void swap1(Integer a, Integer b, Integer c, Integer d){
		root.getChildren().removeAll(nextGrid.get(a).get(b), nextGrid.get(c).get(d));
		nextGrid.get(a).set(b, new Blank((width * a)/down, (height * b)/across, width/down, height/across));
		nextGrid.get(c).set(d, new Type1((width * c)/down, (height * d)/across, width/down, height/across));
		root.getChildren().addAll(nextGrid.get(a).get(b), nextGrid.get(c).get(d));
	}
	
	private void swap2(Integer a, Integer b, Integer c, Integer d){
		root.getChildren().removeAll(nextGrid.get(a).get(b), nextGrid.get(c).get(d));
		nextGrid.get(a).set(b, new Blank((width * a)/down, (height * b)/across, width/down, height/across));
		nextGrid.get(c).set(d, new Type2((width * c)/down, (height * d)/across, width/down, height/across));
		root.getChildren().addAll(nextGrid.get(a).get(b), nextGrid.get(c).get(d));
	}
	
	private void resetAvailable(List<List<Unit>> grid){
		available.clear();
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				if(curGrid.get(i).get(j).isBlank()){
					Integer[] place = {i, j};
					available.add(place);
				}
			}
		}
	}
	
	private boolean spaceAvailable(){
		return !(available.isEmpty());
	}
	
	private boolean isHappy1(int i, int j){
		int[] move1 = {-1, -1, -1, 0, 0, 1, 1, 1};
		int[] move2 = {0, 1, -1, 1, -1, 0, 1, -1};
		double same = 0;
		double diff = 0;
		
		for(int x = 0; x < move1.length; x++){
			int newI = i + move1[x];
			int newJ = j + move2[x];
			if(newI >= 0 && newI < down && newJ >= 0 && newJ < across){
				if (curGrid.get(newI).get(newJ).isType1()) same++;
				else if(curGrid.get(newI).get(newJ).isType2()) diff++;
			}
		}
		if(same + diff < 1) return true; 
		return (same / (same + diff)) >= satisfactionConstant;
	}
	
	private boolean isHappy2(int i, int j){
		int[] move1 = {-1, -1, -1, 0, 0, 1, 1, 1};
		int[] move2 = {0, 1, -1, 1, -1, 0, 1, -1};
		double same = 0;
		double diff = 0;
		
		for(int x = 0; x < move1.length; x++){
			int newI = i + move1[x];
			int newJ = j + move2[x];
			if(newI >= 0 && newI < down && newJ >= 0 && newJ < across){
				if (curGrid.get(newI).get(newJ).isType2()) same++;
				else if(curGrid.get(newI).get(newJ).isType1()) diff++;
			}
		}
		if(same + diff < 1) return true; 
		return (same / (same + diff)) >= satisfactionConstant;
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
