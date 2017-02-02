package cellsociety_team06;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import subUnits.Blank;
import subUnits.Type1;
import subUnits.Type2;


public class SegregationTest extends Application{

	private Stage window;
	private int across = 30;
	private int down = 30;
	private Unit[][] curGrid = new Unit[down][across];
	private Unit[][] nextGrid = new Unit[down][across];
	Random rand = new Random();
	private double blankpct = 0.2;
	private double t1pct = 0.4            ;
	private double t2pct = 0.4;
	private int width = 500;
	private ArrayList<Integer[]> available = new ArrayList<Integer[]>();
	
	private int height = 500;
	private Group root = new Group();
	private double satisfactionConstant = 0.5;
	Stack<Integer> myStack = new Stack();
	
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setResizable(false);
		window.setScene(getSegScene());
		window.show();
		myStack.push(across * down);
	}
	
	
	private Scene getSegScene(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				int r = rand.nextInt(100);
				if(r < blankpct * 100){
					curGrid[i][j] = new Blank((width * i)/down, (height * j)/across, width/down, height/across);
				}
				else if(r < (t1pct + blankpct) * 100){
					curGrid[i][j] = new Type1((width * i)/down, (height * j)/across, width/down, height/across);
				}
				else{
					curGrid[i][j] = new Type2((width * i)/down, (height * j)/across, width/down, height/across);
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
		double visited = 0;
		int last = myStack.pop();
		resetAvailable(curGrid);
		double av = available.size()/((double) (across * down));
		System.out.print(av+ " ");
		HashMap<Integer[], Integer[]> map = new HashMap<Integer[], Integer[]>();
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				visited++;
				Integer[] place = {i, j};
				Unit curr = curGrid[i][j];
				root.getChildren().remove(curr);
				nextGrid[i][j] = curGrid[i][j];
				root.getChildren().add(nextGrid[i][j]);
				if((av * visited < 1) && last > 20){
					continue;
				}
				visited = (double) rand.nextInt(2);
				if(curr.isType1()){
					if(!isHappy1(i, j) && spaceAvailable()){
						System.out.println(rand.nextInt(available.size()));
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
		System.out.println("Map Size: " + map.size() + " Available Size: " + available.size());
		swapGrids(map);
		map.clear();
	}
	
	private void swapGrids(HashMap<Integer[], Integer[]> map){
		for(Integer[] place: map.keySet()){
			swap(place[0], place[1], map.get(place)[0], map.get(place)[1]);
		}
		curGrid = nextGrid;
		nextGrid = new Unit[down][across];
	}
	
	private void swap(Integer a, Integer b, Integer c, Integer d){
		if(nextGrid[a][b].isType1()) swap1(a, b, c, d);
		else swap2(a, b, c, d);
	}
	
	private void swap1(Integer a, Integer b, Integer c, Integer d){
		root.getChildren().removeAll(nextGrid[a][b], nextGrid[c][d]);
		nextGrid[a][b] = new Blank((width * a)/down, (height * b)/across, width/down, height/across);
		nextGrid[c][d] = new Type1((width * c)/down, (height * d)/across, width/down, height/across);
		root.getChildren().addAll(nextGrid[a][b], nextGrid[c][d]);
	}
	
	private void swap2(Integer a, Integer b, Integer c, Integer d){
		root.getChildren().removeAll(nextGrid[a][b], nextGrid[c][d]);
		nextGrid[a][b] = new Blank((width * a)/down, (height * b)/across, width/down, height/across);
		nextGrid[c][d] = new Type2((width * c)/down, (height * d)/across, width/down, height/across);
		root.getChildren().addAll(nextGrid[a][b], nextGrid[c][d]);
	}
	
	private void resetAvailable(Unit[][] grid){
		available.clear();
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				if(curGrid[i][j].isBlank()){
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
				if (curGrid[newI][newJ].isType1()) same++;
				else if(curGrid[newI][newJ].isType2()) diff++;
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
				if (curGrid[newI][newJ].isType2()) same++;
				else if(curGrid[newI][newJ].isType1()) diff++;
			}
		}
		if(same + diff < 1) return true; 
		return (same / (same + diff)) >= satisfactionConstant;
	}
}
