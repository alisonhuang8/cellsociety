package cellsociety_team06;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Unit extends Polygon{
	private static final int DEFAULT_STATE = -1;
	protected int state;
	
	public Unit(){
		setFill(Color.WHITE);
		setStroke(Color.BLACK);
		state = DEFAULT_STATE;
	}
	
	public Unit(Unit u){
		this();
		getPoints().addAll(u.getPoints());
		setLayoutX(u.getLayoutX());
		setLayoutY(u.getLayoutY());
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
	
	public int getState(){
		return state;
	}
	
}
