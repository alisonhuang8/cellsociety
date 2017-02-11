/**
 * Written by Gideon Pfeffer
 */
package subUnits;

import Unit.Unit;
import javafx.scene.paint.Color;

public class Alive extends Unit{
	private static final int ALIVE_STATE = 5;
	private static final Color COLOR = Color.CHARTREUSE;

	/**
	 * sets the fill and state of the unit
	 */
	public Alive(Unit u){
		super(u);
		setFill(COLOR);
		state = ALIVE_STATE;
	}
	public Alive(){
		this(new Unit());
	}
}
