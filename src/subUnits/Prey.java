package subUnits;

import cellsociety_team06.Unit;
import javafx.scene.paint.Color;

public class Prey extends Unit{
	private int walkedThresh = 5;
	private int walked;
	private static final Color COLOR = Color.CYAN;
	public Prey(int x, int y, int width, int height, int walked) {
		super(x, y, width, height);
		this.walked = walked;
		setFill(COLOR);
		state = 4;
	}
	
	public Prey(double x, double y, double width, double height, int walked) {
		this((int) x, (int) y, (int) width, (int) height, walked);
	}

	
	public int getWalked(){
		return walked;
	}
	
	public boolean canBirth(){
		return (walked > walkedThresh);
	}

}
