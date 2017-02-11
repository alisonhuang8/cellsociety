package subUnits.foragingAnts;

import javafx.scene.paint.Color;

public class Ant extends Ground{
	private static final int ANT_STATE = 3;
	private static final Color ANT_COLOR = Color.BROWN;
	
	public Ant(Ground g) {
		super(g);
		setFill(ANT_COLOR);
		state = ANT_STATE;
	}
	
	public Ant(){
		this(new Ground());
	}

}
