package subUnits;

import cellsociety_team06.Unit;
import javafx.scene.paint.Color;

public class Type1 extends Unit{
	private static final Color COLOR = Color.PINK;
	private static final int TYPE1_STATE = 6;
	
	public Type1(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
		setFill(COLOR);
		state = TYPE1_STATE;
	}
	
	public Type1(double x, double y, double width, double height) {
		this((int) x, (int) y, (int) width, (int) height);
	}
}
