package src.Model;
public class Actor {
	private double xLocation;
	private double yLocation;
	private double energy;
	private double initialEnergy;
	private double energyDepletionRate;
	private double timeSinceReproduced = 0;
	
	public Actor(double x, double y, double actorEnergy, double depletionRate){
		xLocation = x;
		yLocation = y;
		energy = actorEnergy;
		initialEnergy = actorEnergy;
		energyDepletionRate = depletionRate;
	}
	public double getTimeSinceReproduced(){
		return timeSinceReproduced;
	}
	public void resetTimeSinceReproduced(){
		timeSinceReproduced = 0;
	}
	public double getDepletionRate(){
		return energyDepletionRate;
	}
	public void updateTimeSinceReproduced(double time){
		timeSinceReproduced += time;
	}
	public void move(double x, double y){
		xLocation = x;
		yLocation = y;
	}
	public void setCenterX(double x){
		xLocation = x;
	}
	public void setCenterY(double y){
		yLocation = y;
	}
	public double getCenterY(){
		return yLocation;
	}
	public double getCenterX(){
		return xLocation;
	}
	public void depleteEnergy(){
		energy -= energyDepletionRate;
	}
	public boolean isDead(){
		return energy <= 0;
	}
	public void setEnergy(double newEnergy){
		energy = newEnergy;
	}
	public double getEnergy(){
		return energy;
	}
}
