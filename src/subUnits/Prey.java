package subUnits;

import cellsociety_team06.Unit;
import javafx.scene.paint.Color;

public class Prey extends Unit{
	private static final int PREDATOR_STATE = 4;
	private static final int WALKED_THRESH = 5;
	private static final Color COLOR = Color.CYAN;
	private int walked;
	
	public Prey(int x, int y, int width, int height, int walked) {
		super(x, y, width, height);
		this.walked = walked;
		setFill(COLOR);
		state = PREDATOR_STATE;
	}
	
	public Prey(double x, double y, double width, double height, int walked) {
		this((int) x, (int) y, (int) width, (int) height, walked);
	}

	
	public int getWalked(){
		return walked;
	}
	
	public boolean canBirth(){
		return (walked > WALKED_THRESH);
	}

}
