package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Stack;
import XMLReads.segReads;
import cellsociety_team06.Model;
import cellsociety_team06.Unit;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import subGrids.hexGrid;
import subGrids.squareGrid;
import subUnits.Blank;
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
	
	public segregationModel(Stage s, Timeline t, ResourceBundle r, int height, int width, int sze){
		super(s,t, r);
		size = sze;
		reads = new segReads(size);
		down = reads.height();
		across = reads.width();
		curGrid = new hexGrid(down, across, height/(down * 3));
		nextGrid =  new hexGrid(down, across, height/(down * 3));
		start();
	}
	
	private void start(){
		totalBlank = 0;
		available = new ArrayList<>();
		myStack = new Stack<>();
		myStack.push(across * down);
		getSegScene();
	}
	
	private void getSegScene(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				if(reads.get(i, j) == '0'){
					curGrid.setUnit(i, j, new Blank(curGrid.getUnit(i, j)));
					totalBlank++;
				}
				else if(reads.get(i, j) == 'A'){
					curGrid.setUnit(i, j, new Type1(curGrid.getUnit(i, j)));
				}
				else{
					curGrid.setUnit(i, j, new Type2(curGrid.getUnit(i, j)));
				}
			}
		}
		resetRoot();
	}

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
	
	private void swapGrids(Map<Integer[], Integer[]> map){
		for(Integer[] place: map.keySet()){
			curGrid.swap(place[0], place[1], map.get(place)[0], map.get(place)[1]);
		}
	}
	
	private void resetAvailable(){
		Unit u = new Unit();
		available.clear();
		available.addAll(curGrid.getInstances(new Blank(u)).keySet());	
	}
	
	private boolean spaceAvailable(){
		return !(available.isEmpty());
	}
	
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
		//Not needed for this algorithm.
	}

}
