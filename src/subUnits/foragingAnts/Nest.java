package subUnits.foragingAnts;

import Unit.Unit;
import javafx.scene.paint.Color;

public class Nest extends Ground{
	private static final int NEST_STATE = 11;
	private static final Color NEST_COLOR = Color.RED;
	
	public Nest(Unit u) {
		super(u);
		setFill(NEST_COLOR);
		state = NEST_STATE;
	}
	
	public Nest(){
		this(new Unit());
	}

}
