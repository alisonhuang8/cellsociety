package subUnits.foragingAnts;

import Unit.Unit;
import javafx.scene.paint.Color;

public class Food extends Ground{
	private static final int FOOD_STATE = 9;
	private static final Color FOOD_COLOR = Color.BLUE;
	
	public Food(Unit u) {
		super(u);
		setFill(FOOD_COLOR);
		state = FOOD_STATE;
	}
	
	public Food(){
		this(new Unit());
	}

}
