/**
 * Written by Gideon Pfeffer
 * the super class for all units
 */
package Unit;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Unit extends Polygon{
	private static final int DEFAULT_STATE = -1;
	protected int state;
	
	/**
	 * sets the color to black to indicate no sub unit
	 * has been made
	 */
	public Unit(){
		setFill(Color.WHITE);
		setStroke(Color.BLACK);
		state = DEFAULT_STATE;
	}
	
	/**
	 * sets the color to black to indicate no sub unit
	 */
	public Unit(Unit u){
		this();
		getPoints().addAll(u.getPoints());
		setLayoutX(u.getLayoutX());
		setLayoutY(u.getLayoutY());
	}
	
	/**
	 * returns if the unit is a burning unit
	 */
	public boolean isBurning(){
		return state == 0;
	}
	
	/**
	 * returns if the unit is a burnt unit
	 */
	public boolean isBurnt(){
		return state == 1;
	}
	
	/**
	 * returns if the unit is a blank unit
	 */
	public boolean isBlank(){
		return state == 2;
	}
	
	/**
	 * returns if the unit is a predator unit
	 */
	public boolean isPredator(){
		return state == 3;
	}
	
	/**
	 * returns if the unit is a prey unit
	 */
	public boolean isPrey(){
		return state == 4;
	}
	
	/**
	 * returns if the unit is an alive unit
	 */
	public boolean isAlive(){
		return state == 5;
	}
	
	/**
	 * returns if the unit is a type1 unit
	 */
	public boolean isType1(){
		return state == 6;
	}
	
	/**
	 * returns if the unit is a type2 unit
	 */
	public boolean isType2(){
		return state == 7;
	}
	
	/**
	 * returns if the unit is a ant unit
	 */
	public boolean isAnt(){
		return state == 8;
	}
	
	/**
	 * returns if the unit is a food unit
	 */
	public boolean isFood(){
		return state == 9;
	}
	
	/**
	 * returns if the unit is a ground unit
	 */
	public boolean isGround(){
		return state == 10;
	}
	
	/**
	 * returns if the unit is a nest unit
	 */
	public boolean isNest(){
		return state == 11;
	}
	
	/**
	 * returns if the state of the unit
	 */
	public int getState(){
		return state;
	}
	
}
