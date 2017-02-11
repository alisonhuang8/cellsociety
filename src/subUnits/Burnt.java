/**
 * Written by Gideon Pfeffer
 */
package subUnits;

import Unit.Unit;
import javafx.scene.paint.Color;

public class Burnt extends Unit{
	private static final int BURNT_STATE = 1;
	private static final Color COLOR = Color.DIMGRAY;
	
	/**
	 * sets the fill and state of the unit
	 */
	public Burnt(Unit u) {
		super(u);
		setFill(COLOR);
		state = BURNT_STATE;
	}
	
	public Burnt(){
		this(new Unit());
	}
}
