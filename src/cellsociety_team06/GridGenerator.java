package cellsociety_team06;

import java.util.List;

import cellsociety_team06.Model;
import javafx.scene.Group;
import Unit.Unit;
import XMLReads.Reads;
import subGrids.hexGrid;
import subGrids.squareGrid;
import subGrids.triangularGrid;
import subUnits.Alive;
import subUnits.Blank;
import subUnits.Burning;
import subUnits.Predator;
import subUnits.Prey;
import subUnits.Type1;
import subUnits.Type2;

public class GridGenerator {

	private int simType = 0;
	private int unitShape = 0;
	private int gridSize = 0;
	private List<Integer[]> neighborConfig;
	private int boundaryStyle = 0;
	private int inputStyle = 0;

	private int width = 0;
	private int height = 0;
	private int down = 0;
	private int across = 0;
	private int totalBlank = 0;
	
	private ReadXMLFile files;
	protected Group root;

	private Grid currGrid;
	private Grid nextGrid;
	
	private Reads reads;

	public GridGenerator(int st, int us, int gs, List<Integer[]> nc, int bs, int is, int w, int h){
		simType = st;
		unitShape = us;
		gridSize = gs;
		neighborConfig = nc;
		boundaryStyle = bs;
		inputStyle = is;
		width = w;
		height = h;

		files = new ReadXMLFile();
		triggerEverything();
		
	}
	
	private void triggerEverything(){
		createReads();
		getAcross();
		getDown();
		createEmptyGrids();
		fillCurrGrid();
	}
	
	public Grid returnCurrGrid(){
		return currGrid;
	}
	
	public Grid returnNextGrid(){
		return nextGrid;
	}
	
	private void createReads(){
		if (simType == 1){
			reads = new Reads("Life");
		} else if (simType == 2){
			reads = new Reads("Fire");
		} else if (simType == 3){
			reads = new Reads("Wator");
		} else {
			reads = new Reads("Segregation");
		}
	}

	private void getDown(){

		if (gridSize == 1){
			if (unitShape == 3){
				down = 20;
			} else {
				down = 10;
			}
		} else if (gridSize == 2){
			if (unitShape == 3){
				down = 40;
			} else {
				down = 20;
			}
		} else {
			if (unitShape == 3){
				down = 60;
			} else {
				down = 30;
			}
		}

	}

	private void getAcross(){

		if (gridSize == 1){
			across = 10;
		} else if (gridSize == 2){
			across = 20;
		} else {
			across = 30;
		}

	}

	private void createEmptyGrids(){
		
		if (unitShape == 1){
			currGrid = new squareGrid(down, across, height/down);
			nextGrid = new squareGrid(down, across, height/down);
		} else if (unitShape == 2){
			currGrid = new triangularGrid(down, across, height/down);
			nextGrid = new triangularGrid(down, across, height/down);
		} else {
			currGrid = new hexGrid(down, across, height/down);
			nextGrid = new hexGrid(down, across, height/down);
		}
	}
	
	private void fillCurrGrid(){
		if (simType == 1){
			fillWithLife();
		} else if (simType == 2){
			fillWithFire();
		} else if (simType == 3){
			fillWithWator();
		} else {
			fillWithSeg();
		}
	}
	
	private void fillWithLife(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				if(reads.get(i, j) == '0'){
					currGrid.setUnit(i, j, new Alive(currGrid.getUnit(i, j)));
				}
				else{
					currGrid.setUnit(i, j, new Blank(currGrid.getUnit(i, j)));
				}
			}
		}
	}
	
	private void fillWithFire(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				if(reads.get(i, j) == 'G'){
					currGrid.setUnit(i, j, new Alive(currGrid.getUnit(i, j)));
				}
				if(i == 0 && j == 0){
					currGrid.setUnit(i, j, new Burning(currGrid.getUnit(i, j)));
				}
			}
		}
	}
	
	private void fillWithWator(){
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				if(reads.get(i, j) == 'R'){
					currGrid.setUnit(i, j, new Prey(0, currGrid.getUnit(i, j)));
				}
				else if(reads.get(i, j) == 'Y'){
					currGrid.setUnit(i, j, new Predator(5, 0, currGrid.getUnit(i, j)));
				}
				else{
					currGrid.setUnit(i, j, new Blank(currGrid.getUnit(i, j)));
				}
			}
		}
	}
	
	private void fillWithSeg(){
		totalBlank = 0;
		for(int i = 0; i < down; i++){
			for(int j = 0; j < across; j++){
				if(reads.get(i, j) == '0'){
					currGrid.setUnit(i, j, new Blank(currGrid.getUnit(i, j)));
					totalBlank++;
				}
				else if(reads.get(i, j) == 'A'){
					currGrid.setUnit(i, j, new Type1(currGrid.getUnit(i, j)));
				}
				else{
					currGrid.setUnit(i, j, new Type2(currGrid.getUnit(i, j)));
				}
			}
		}
	}
}
