package subUnits.foragingAnts;

import Unit.Unit;
import javafx.scene.paint.Color;

public class Ground extends Unit {
	private static final int GROUND_STATE = 0;
	private static final Color GROUND_COLOR = Color.SEASHELL;
	

	public Ground() {
		setFill(GROUND_COLOR);
		setStroke(Color.BLACK);
		state = GROUND_STATE;
	}

	public Ground(Ground g) {
		this();
		getPoints().addAll(g.getPoints());
		setLayoutX(g.getLayoutX());
		setLayoutY(g.getLayoutY());
	}
}
