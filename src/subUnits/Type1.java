package subUnits;

import cellsociety_team06.Unit;
import javafx.scene.paint.Color;

public class Type1 extends Unit{
	private static final Color COLOR = Color.PINK;
	public Type1(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
		setFill(COLOR);
	}
	
	public Type1(double x, double y, double width, double height) {
		super(x, y, width, height);
		setFill(COLOR);
	}
	@Override
	public boolean isBurning() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isBurnt() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isBlank() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isPredator() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isPrey() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isType1() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isType2() {
		// TODO Auto-generated method stub
		return false;
	}

}
