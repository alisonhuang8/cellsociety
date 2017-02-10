package subUnits;

import cellsociety_team06.Unit;
import javafx.scene.paint.Color;

public class Type2 extends Unit{
	
	private static final Color COLOR = Color.SANDYBROWN;
	private static final int TYPE2_STATE = 7;
	
	public Type2(Unit u){
		super(u);
		setFill(COLOR);
		state = TYPE2_STATE;
	}
}
