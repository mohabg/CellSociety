
public class Actor {
	private int xLocation;
	private int yLocation;
	private double energy;
	private double initialEnergy;
	private double energyDepletionRate;
	private double timeSinceReproduced = 0;
	
	public Actor(int x, int y, double actorEnergy, double depletionRate){
		xLocation = x;
		yLocation = y;
		energy = actorEnergy;
		initialEnergy = actorEnergy;
		energyDepletionRate = depletionRate;
	}
	public Actor reproduce(){
		timeSinceReproduced = 0;
		return new Actor(xLocation, yLocation, initialEnergy, energyDepletionRate);
	}
	public double getTimeSinceReproduced(){
		return timeSinceReproduced;
	}
	public void updateTimeSinceReproduced(double time){
		timeSinceReproduced += time;
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
