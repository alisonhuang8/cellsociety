package subUnits;

import cellsociety_team06.Unit;
import javafx.scene.paint.Color;

public class Predator extends Unit{
	private static final int PREDATOR_STATE = 3;
	private static final int WALKED_THRESH = 6;
	private static final int BONUS_ENERGY = 5;
	private int energy;
	private int walked;
	
	private static final Color COLOR = Color.GREEN;
	public Predator(int x, int y, int width, int height, int energy, int walked) {
		super(x, y, width, height);
		this.energy = energy;
		this.walked = walked;
		setFill(COLOR);
		state = PREDATOR_STATE;
	}
	
	public Predator(double x, double y, double width, double height,  int energy, int walked) {
		this((int) x, (int) y, (int) width, (int) height, energy, walked);
	}

	
	public int getEnergy(){
		return energy;
	}
	
	public boolean removeEnergy(){
		energy--;
		return energy < 0;
	}
	
	public void energyUp(){
		energy += BONUS_ENERGY;
	}
	
	public int ePerEat(){
		return BONUS_ENERGY;
	}
	
	public int getWalked(){
		return walked;
	}
	
	public boolean canBirth(){
		return walked > WALKED_THRESH;
	}


}
