package subUnits.foragingAnts;

import Unit.Unit;
import javafx.scene.paint.Color;

public class Ground extends Unit {
	private static final int GROUND_STATE = 10;
	private static final Color GROUND_COLOR = Color.SEASHELL;
	

	public Ground() {
		setFill(GROUND_COLOR);
		setStroke(Color.BLACK);
		state = GROUND_STATE;
	}

	public Ground(Unit u) {
		super(u);
	}
}
