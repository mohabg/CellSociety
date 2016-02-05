
public class Actor {
	private int xLocation;
	private int yLocation;
	private double energy;
	private double energyDepletionRate;
	
	public Actor(int x, int y, double actorEnergy, double depletionRate){
		xLocation = x;
		yLocation = y;
		energy = actorEnergy;
		energyDepletionRate = depletionRate;
	}
	public Actor reproduce(){
		return new Actor(xLocation, yLocation, energy, energyDepletionRate);
	}
	public void move(int x, int y){
		xLocation = x;
		yLocation = y;
	}
	public int getX(){
		return xLocation;
	}
	public void setX(int x){
		xLocation = x;
	}
	public int getY(){
		return yLocation;
	}
	public void setY(int y){
		yLocation = y;
	}
	public void depleteEnergy(){
		energy -= energyDepletionRate;
	}
	public Boolean isDead(){
		return energy <= 0;
	}
	public void setEnergy(double newEnergy){
		energy = newEnergy;
	}
	public double getEnergy(){
		return energy;
	}
}
