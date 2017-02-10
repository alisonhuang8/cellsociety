package cellsociety_team06;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Unit extends Polygon{
	protected int state;
	
	public Unit(){
		setFill(Color.WHITE);
		setStroke(Color.BLACK);
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

}
