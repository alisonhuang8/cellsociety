package subUnits;

import cellsociety_team06.Unit;
import javafx.scene.paint.Color;

public class Burnt extends Unit{
	private static final int BURNT_STATE = 1;
	private static final Color COLOR = Color.DIMGRAY;
	public Burnt(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
		setFill(COLOR);
		state = BURNT_STATE;
	}
	
	public Burnt(double x, double y, double width, double height) {
		this((int) x, (int) y, (int) width, (int) height);
	}
}
