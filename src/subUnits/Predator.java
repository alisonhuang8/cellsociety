/**
 * Written by Gideon Pfeffer
 */
package subUnits;

import Unit.Unit;
import javafx.scene.paint.Color;

public class Predator extends Unit{
	private static final int PREDATOR_STATE = 3;
	private static final int WALKED_THRESH = 6;
	private static final int BONUS_ENERGY = 5;
	private int energy;
	private int walked;
	private static final Color COLOR = Color.GREEN;
	
	/**
	 * sets the fill and state of the unit
	 * also takes in the distance walked and energy
	 */
	public Predator(int energy, int walked, Unit u){
		super(u);
		this.energy = energy;
		this.walked = walked;
		setFill(COLOR);
		state = PREDATOR_STATE;
	}
	
	public Predator(){
		this(0, 0, new Unit());
	}
	/**
	 * returns the current energy of the predator
	 */
	public int getEnergy(){
		return energy;
	}
	
	/**
	 * returns whether or not there is any energy left
	 * if there is, it removes an energy
	 */
	public boolean removeEnergy(){
		energy--;
		return energy < 0;
	}
	
	/**
	 * adds energy when eating a prey
	 */
	public void energyUp(){
		energy += BONUS_ENERGY;
	}
	
	/**
	 * returns the amount of energy per eat
	 */
	public int ePerEat(){
		return BONUS_ENERGY;
	}
	
	/**
	 * returns the total distance walked
	 */
	public int getWalked(){
		return walked;
	}
	
	/**
	 * resets the distance walked to 0
	 */
	public void resetWalked(){
		walked = 0;
	}
	
	/**
	 * if the prey has walked enough
	 * it will return true (ready to give birth)
	 * otherwise, returns false
	 */
	public boolean canBirth(){
		return walked > WALKED_THRESH;
	}
}
