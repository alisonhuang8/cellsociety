/**
 * Written by Gideon Pfeffer
 */
package subUnits;

import Unit.Unit;
import javafx.scene.paint.Color;

public class Type2 extends Unit{
	
	private static final Color COLOR = Color.SANDYBROWN;
	private static final int TYPE2_STATE = 7;
	
	/**
	 * sets the fill and state of the unit
	 * also sets the distance walked
	 */
	public Type2(Unit u){
		super(u);
		setFill(COLOR);
		state = TYPE2_STATE;
	}
	
	public Type2(){
		this(new Unit());
	}
}
