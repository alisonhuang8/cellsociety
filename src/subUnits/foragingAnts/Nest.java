package subUnits.foragingAnts;

import javafx.scene.paint.Color;

public class Nest extends Ground{
	private static final int NEST_STATE = 2;
	private static final Color NEST_COLOR = Color.RED;
	
	public Nest(Ground g) {
		super(g);
		setFill(NEST_COLOR);
		state = NEST_STATE;
	}
	
	public Nest(){
		this(new Ground());
	}

}
