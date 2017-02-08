package simUnits;

import java.util.List;

import cellsociety_team06.Unit2;
import javafx.scene.paint.Color;

public class FireUnits extends Unit2{
	private static Color alive;
	private static Color burning;
	private static Color burnt;
	List<Color> colors;
	
	public FireUnits(double x, double y, double width, double height, List<Color> list) {
		super(x, y, width, height);
		this.colors = list;
		alive = list.get(0);
		burning = list.get(1);
		burnt = list.get(2);
		setFill(Color.WHITE);
	}
	
	public void setAlive(){
		setFill(alive);
	}
	
	public void setBurning(){
		setFill(burning);
	}
	
	public void setBurnt(){
		setFill(burnt);
	}
	
	public boolean isBurning(){
		return getFill().equals(burning);
	}
	
	public boolean isAlive(){
		return getFill().equals(alive);
	}
	
	public boolean isBurnt(){
		return getFill().equals(burnt);
	}
	
	public FireUnits copy(){
		return new FireUnits(this.getX(), this.getY(), this.getHeight(), this.getWidth(), colors);
	}
}
