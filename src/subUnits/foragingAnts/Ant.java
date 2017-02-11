package subUnits.foragingAnts;

import Unit.Unit;
import javafx.scene.paint.Color;

public class Ant extends Ground{
	private static final int ANT_STATE = 8;
	private static final Color ANT_COLOR = Color.BROWN;
	private boolean hasFood;
	private int[] orientation = new int[]{0,0};
	
	public Ant(Unit u) {
		super(u);
		setFill(ANT_COLOR);
		state = ANT_STATE;
	}
	
	public Ant(){
		this(new Unit());
	}
	
	public boolean hasFoodItem(){
		return hasFood;
	}
	
	public void gotFoodItem(){
		hasFood = true;
	}
	
	public void setOrientation(int[] o){
		orientation = o;
	}

}
