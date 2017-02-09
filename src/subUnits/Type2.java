package subUnits;

import cellsociety_team06.Unit;
import javafx.scene.paint.Color;

public class Type2 extends Unit{
	private static final Color COLOR = Color.SANDYBROWN;
	public Type2(int x, int y, int width, int height) {
		super(x, y, width, height);
		setFill(COLOR);
		state = 7;
	}
	
	public Type2(double x, double y, double width, double height) {
		this((int) x, (int) y, (int) width, (int) height);
	}
}
