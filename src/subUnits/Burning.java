/**
 * Written by Gideon Pfeffer
 */
package subUnits;

import Unit.Unit;
import javafx.scene.paint.Color;

public class Burning extends Unit{
	private static final int BURNING_STATE = 0;
	private static final Color COLOR = Color.RED;
	
	/**
	 * sets the fill and state of the unit
	 */
	public Burning(Unit u){
		super(u);
		setFill(COLOR);
		state = BURNING_STATE;
	}
	
	public Burning(){
		this(new Unit());
	}
}
