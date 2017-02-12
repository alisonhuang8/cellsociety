package subUnits.sugarScape;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import Unit.Unit;
import javafx.scene.paint.Color;

public class Agent extends Unit {
	private int sugarLeft, vision, metabolism;
	private final int[] STARTING_SUGAR = {5, 10, 15, 20};
	private final int[] STARTING_VISION = {1, 2, 3, 4, 5, 6};
	private final int[] STARTING_METABOLISM = {1, 2, 3, 4};
	private static final Color COLOR = Color.RED;
	private static final int AGENT_STATE = 12;
	Random rand = new Random();

	/**
	 * sets the fill and state of the unit also sets the distance walked
	 */
	public Agent(Unit u) {
		super(u);
		sugarLeft = STARTING_SUGAR[rand.nextInt(STARTING_SUGAR.length)];
		metabolism = STARTING_METABOLISM[rand.nextInt(STARTING_METABOLISM.length)];
		vision = STARTING_VISION[rand.nextInt(STARTING_VISION.length)];
		setFill(COLOR);
		state = AGENT_STATE;
	}

	public Agent() {
		this(new Unit());
	}
	
	public void metabolize(){
		sugarLeft -= metabolism;
	}
	
	public boolean isDead(){
		return sugarLeft <= 0;
	}
	
	public void pickedUp(int sugarPickedUp){
		sugarLeft += sugarPickedUp;
	}
	
	public int agentVision(){
		return vision;
	}
	
	public List<Integer> getStats(){
		Integer[] stats = {sugarLeft, vision, metabolism};
		return Arrays.asList(stats);
	}
	
	public void setStats(Agent a){
		sugarLeft = a.getStats().get(0);
		vision = a.getStats().get(1);
		metabolism = a.getStats().get(2);
	}
	
}
