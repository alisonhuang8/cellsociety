/**
 * Written by Gideon Pfeffer
 */
package subUnits;

import Unit.Unit;
import javafx.scene.paint.Color;

public class Type1 extends Unit{
	private static final Color COLOR = Color.PINK;
	private static final int TYPE1_STATE = 6;
	
	/**
	 * sets the fill and state of the unit
	 * also sets the distance walked
	 */
	public Type1(Unit u){
		super(u);
		setFill(COLOR);
		state = TYPE1_STATE;
	}
	
	public Type1(){
		this(new Unit());
	}
}
