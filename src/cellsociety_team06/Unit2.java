package cellsociety_team06;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Unit2 extends Rectangle{
	
	public Unit2(double x, double y, double width, double height){
		this((int) x, (int) y, (int) width, (int) height);
	}
	
	public Unit2(int x, int y, int width, int height){
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setStroke(Color.BLACK);
	}

}
