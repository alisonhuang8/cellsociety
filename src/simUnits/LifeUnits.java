package simUnits;

import java.util.Arrays;
import java.util.List;

import cellsociety_team06.Unit2;
import javafx.scene.paint.Color;

public class LifeUnits extends Unit2{
	private static Color alive;
	private static Color dead;
	List<Color> colors;
	private static Color[] defaultColors = {Color.GREENYELLOW, Color.WHITE};
	
	public LifeUnits(double x, double y, double width, double height, List<Color> list) {
		super(x, y, width, height);
		this.colors = list;
		alive = list.get(0);
		dead = list.get(1);
		setFill(Color.WHITE);
	}
	
	public LifeUnits(double x, double y, double width, double height) {
		this(x, y, width, height, Arrays.asList(defaultColors));
	}
	
	public void setAlive(){
		setFill(alive);
	}
	
	public void setDead(){
		setFill(dead);
	}
	
	public boolean isAlive(){
		return getFill().equals(alive);
	}
	
	public boolean isDead(){
		return getFill().equals(dead);
	}
	
	public LifeUnits copy(){
		LifeUnits replica = makeUnit(this);
		replica.setFill(this.getFill());
		return replica;
	}
	
	private LifeUnits makeUnit(LifeUnits unit){
		return new LifeUnits(unit.getX(), unit.getY(), unit.getHeight(), unit.getWidth(), colors);

	}
}
