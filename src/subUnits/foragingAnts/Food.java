package subUnits.foragingAnts;

import javafx.scene.paint.Color;

public class Food extends Ground{
	private static final int FOOD_STATE = 1;
	private static final Color FOOD_COLOR = Color.BLUE;
	
	public Food(Ground g) {
		super(g);
		setFill(FOOD_COLOR);
		state = FOOD_STATE;
	}
	
	public Food(){
		this(new Ground());
	}

}
