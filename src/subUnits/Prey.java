/**
 * Written by Gideon Pfeffer
 */
package subUnits;

import Unit.Unit;
import javafx.scene.paint.Color;

public class Prey extends Unit{
	private static final int PREDATOR_STATE = 4;
	private static final int WALKED_THRESH = 5;
	private static final Color COLOR = Color.CYAN;
	private int walked;
	
	/**
	 * sets the fill and state of the unit
	 * also sets the distance walked
	 */
	public Prey(int walked, Unit u) {
		super(u);
		this.walked = walked;
		setFill(COLOR);
		state = PREDATOR_STATE;
	}
	
	public Prey(){
		this(0, new Unit());
	}
	
	/**
	 * returns the total distance walked
	 */
	public int getWalked(){
		return walked;
	}
	
	/**
	 * returns whether or not the prey is ready to give birth
	 */
	public boolean canBirth(){
		return (walked > WALKED_THRESH);
	}
}
