package cellsociety_team06;

import java.util.ArrayList;
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
import subUnits.sugarScape.Agent;
import subUnits.sugarScape.Sugar;

public class GridGenerator {

	private int simType = 0;
	private int unitShape = 0;
	private int gridSize = 0;
	private int neighborConfig;
	private int boundaryStyle = 0;
	private int inputStyle = 0;

	private int width = 0;
	private int height = 0;
	private int down = 0;
	private int across = 0;
	private int totalBlank = 0;

	protected Group root;

	private Grid currGrid;
	private Grid nextGrid;
	private Grid initialGrid;

	private Reads reads;

	public GridGenerator(int st, int us, int gs, int nc, int bs, int is, int w, int h){
		simType = st;
		unitShape = us;
		gridSize = gs;
		neighborConfig = nc;
		boundaryStyle = bs;
		inputStyle = is;
		width = w;
		height = h;

		new ReadXMLFile();
		triggerEverything();

	}

	private void triggerEverything(){
		createReads();
		getAcross();
		getDown();
		createEmptyGrids();
		fillCurrGrid();
		checkToroidal();
		checkPossNeighbors();
	}

	public Grid returnCurrGrid(){
		return currGrid;
	}

	public Grid returnNextGrid(){
		return nextGrid;
	}

	public Grid returnInitialGrid(){
		for (int i=0; i<currGrid.rows(); i++){
			for (int j=0; j<currGrid.cols(); j++){
				initialGrid.setUnit(i, j, currGrid.getUnit(i, j));
			}
		}
		return initialGrid;
	}

	private void createReads(){
		if (simType == 1){
			reads = new Reads("Life", inputStyle, simType);
		} else if (simType == 2){
			reads = new Reads("Fire", inputStyle, simType);
		} else if (simType == 3){
			reads = new Reads("Wator", inputStyle, simType);
		} else if (simType == 4){
			reads = new Reads("Segregation", inputStyle, simType);
		} else {
			reads = new Reads("Sugar", inputStyle, simType);
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
			initialGrid = new squareGrid(down, across, height/down);
		} else if (unitShape == 2){
			currGrid = new triangularGrid(down, across, height/down);
			nextGrid = new triangularGrid(down, across, height/down);
			initialGrid = new triangularGrid(down, across, height/down);
		} else {
			currGrid = new hexGrid(down, across, height/down);
			nextGrid = new hexGrid(down, across, height/down);
			initialGrid = new hexGrid(down, across, height/down);
		}
	}

	private void checkPossNeighbors(){
		if (unitShape == 1){
			int[] rowMove;
			int[] colMove;
			if (neighborConfig == 1){
				rowMove = new int[] {-1, 0, 0, 1, 1, -1, 1, -1};
				colMove = new int[] {0, 1, -1, 0, 1, -1, -1, 1};
				currGrid.setNeighbors(rowMove, colMove);
			} else if (neighborConfig == 2){
				rowMove = new int[] {-1, 0, 0, 1};
				colMove = new int[] {0, 1, -1, 0};
				currGrid.setNeighbors(rowMove, colMove);
			} else if (neighborConfig == 3){
				rowMove = new int[] {1, -1, 1, -1};
				colMove = new int[] {1, -1, -1, 1};
				currGrid.setNeighbors(rowMove, colMove);
			} else if (neighborConfig == 4){
				rowMove = new int[] {0, 0};
				colMove = new int[] {1, -1};
				currGrid.setNeighbors(rowMove, colMove);
			} else {
				rowMove = new int[] {-1, 1};
				colMove = new int[] {0, 0};
				currGrid.setNeighbors(rowMove, colMove);
			}
		}

	}

	private void fillCurrGrid(){
		if (simType == 1){
			fillWithLife();
		} else if (simType == 2){
			fillWithFire();
		} else if (simType == 3){
			fillWithWator();
		} else if (simType == 4) {
			fillWithSeg();
		} else {
			fillWithSugar();
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
				if(i == down/5 && j == across/5){
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

	private void fillWithSugar(){
		for (int i=0; i<down; i++){
			for (int j=0; j<across; j++){
				if(reads.get(i, j) == 'A'){
					currGrid.setUnit(i, j, new Agent(currGrid.getUnit(i, j)));
				} else {
					currGrid.setUnit(i, j, new Sugar(currGrid.getUnit(i, j)));
				}
			}
		}
	}

	private void checkToroidal(){
		if (boundaryStyle == 1){
			currGrid.undoToroidal();
		}
		if (boundaryStyle == 2){
			currGrid.makeToroidal();
		}
	}

}
