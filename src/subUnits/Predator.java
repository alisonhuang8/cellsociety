package subUnits;

import cellsociety_team06.Unit;
import javafx.scene.paint.Color;

public class Predator extends Unit{
	private int walkedThresh = 6;
	private int energy;
	private int bonusEnergy = 5;
	private int walked;
	
	private static final Color COLOR = Color.GREEN;
	public Predator(int x, int y, int width, int height, int energy, int walked) {
		super(x, y, width, height);
		this.energy = energy;
		this.walked = walked;
		setFill(COLOR);
	}
	
	public Predator(double x, double y, double width, double height,  int energy, int walked) {
		super(x, y, width, height);
		this.energy = energy;
		this.walked = walked;
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
		return true;
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
		return false;
	}
	@Override
	public boolean isType2() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public int getEnergy(){
		return energy;
	}
	
	public boolean removeEnergy(){
		energy--;
		return energy < 0;
	}
	
	public void energyUp(){
		energy += bonusEnergy;
	}
	
	public int ePerEat(){
		return bonusEnergy;
	}
	
	public int getWalked(){
		return walked;
	}
	
	public boolean canBirth(){
		return walked > walkedThresh;
	}


}
