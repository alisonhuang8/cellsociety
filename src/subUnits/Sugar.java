package subUnits.sugarScape;

import java.util.Random;

import Unit.Unit;
import javafx.scene.paint.Color;

public class Sugar extends Unit{
	private static final Color[] COLORS = {Color.WHITE, Color.PAPAYAWHIP,
			Color.YELLOW, Color.GREENYELLOW, Color.GOLD};
	private int sugarCount;
	private static final int GROW_RATE = 1;
	private static final int SUGAR_STATE = 13;
	private static final int MAX_SUGAR = 4;
	private static final int[] STARTING_SUGAR = {1, 2, 3, 4};
	private static Random rand = new Random();

	/**
	 * sets the fill and state of the unit also sets the distance walked
	 */
	public Sugar(Unit u, int sugar) {
		super(u);
		sugarCount = sugar;
		setFill(COLORS[sugarCount]);
		state = SUGAR_STATE;
	}
	
	public Sugar(Unit u) {
		this(u, STARTING_SUGAR[rand.nextInt(STARTING_SUGAR.length)]);
	}
	
	public Sugar() {
		this(new Unit());
	}
	
	public int getSugar(){
		return sugarCount;
	}
	
	public void setSugar(int newSugar){
		sugarCount = newSugar;
	}
	
	public void regen(){
		if(sugarCount < MAX_SUGAR){
			sugarCount += GROW_RATE;
			setFill(COLORS[sugarCount]);
		}
	}
}
