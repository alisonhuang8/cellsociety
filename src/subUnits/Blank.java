package subUnits;

import cellsociety_team06.Unit;
import javafx.scene.paint.Color;

public class Blank extends Unit{
	private static final Color COLOR = Color.WHITE;
	public Blank(int x, int y, int width, int height) {
		super(x, y, width, height);
		setFill(COLOR);
		state = 2;
	}
	
	public Blank(double x, double y, double width, double height) {
		this((int) x, (int) y, (int) width, (int) height);
	}
}
