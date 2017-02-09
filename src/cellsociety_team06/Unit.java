package cellsociety_team06;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Unit extends Rectangle{
	protected int state;
	
	public Unit(int x, int y, int width, int height){
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setStroke(Color.BLACK);
	}
	
	public boolean isBurning(){
		return state == 0;
	}
	
	public boolean isBurnt(){
		return state == 1;
	}
	
	public boolean isBlank(){
		return state == 2;
	}
	
	public boolean isPredator(){
		return state == 3;
	}
	
	public boolean isPrey(){
		return state == 4;
	}
	
	public boolean isAlive(){
		return state == 5;
	}
	
	public boolean isType1(){
		return state == 6;
	}
	
	public boolean isType2(){
		return state == 7;
	}
}
