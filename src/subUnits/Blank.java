package subUnits;

import Unit.Unit;
import javafx.scene.paint.Color;

public class Blank extends Unit{
	private static final int BLANK_STATE = 2;
	private static final Color COLOR = Color.WHITE;
	
	public Blank(Unit u){
		super(u);
		setFill(COLOR);
		state = BLANK_STATE;
	}
	
	public Blank(){
		this(new Unit());
	}
}
