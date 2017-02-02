package cellsociety_team06;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Unit extends Rectangle{
	
	public Unit(double x, double y, double width, double height){
		this((int) x, (int) y, (int) width, (int) height);
	}
	
	public Unit(int x, int y, int width, int height){
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setStroke(Color.BLACK);
	}
	
	public abstract boolean isBurning();
	
	public abstract boolean isBurnt();
	
	public abstract boolean isBlank();
	
	public abstract boolean isPredator();
	
	public abstract boolean isPrey();
	
	public abstract boolean isAlive();
	
	public abstract boolean isType1();
	
	public abstract boolean isType2();
}
