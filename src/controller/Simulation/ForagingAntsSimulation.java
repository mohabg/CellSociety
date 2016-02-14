package src.controller.Simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import src.Model.*;
import src.Model.Grid;

public class ForagingAntsSimulation extends Simulation{
	
	public class Ant extends Actor{
		private Boolean hasFoodItem;
		
		public Ant(double x, double y, double actorEnergy, double depletionRate) {
			super(x, y, actorEnergy, depletionRate);
			hasFoodItem = false;
		}
		public void pickUpFoodItem(){
			hasFoodItem = true;
		}
		public void dropFoodItem(){
			hasFoodItem = false;
		}
		public Boolean hasFoodItem(){
			return hasFoodItem;
		}
		public Boolean isAtLocation(double xLocation, double yLocation){
			return getX() == xLocation && getY() == yLocation;
		}
	}
	private int emptyCell = 0;
	private int antCell = 1;
	private int obstacleCell = 2;
	private int homePheromoneIndex = 0;
	private int foodPheromoneIndex = 1;
	private int numberOfPheromones = 2;
	private int maximumAntsPerLocation = 10;
	private int maximumAntsInSimulation = 1000;
	private int antsBornePerTimeStep = 2;
	private double antLifeTime = 50;
	private double minNumberOfPheromones = 0.0;
	private double maxNumberOfPheromones = 100.0;
	private double gradientConstant = 2;
	private double evaporationRatio = 0.3;
	private double diffusionRatio = 0.4;
	private double kProbability = 0.01;
	private double nProbability = 10.0;
	private int nestX = 70;
	private int nestY = 70;
	private double numberOfFoodSources = 2;
	private List<Cell> foodSources;
	private List<Actor> ants;
	private Grid myGrid;
	
	
	public void setFoodSourceCells(List<Double> xLocations, List<Double> yLocations){
		for(int i = 0; i < numberOfFoodSources; i++){
			foodSources.add(myGrid.getCell(xLocations.get(i), yLocations.get(i)));
		}
	}
	public ForagingAntsSimulation(Grid grid) {
		super(grid);
		myGrid = grid;
		ants = new ArrayList<Actor>();
		foodSources = new ArrayList<Cell>();
		for(Cell cell: grid.getCells()){
			for(int i = 0; i < numberOfPheromones; i++){
			cell.getGround().addPheromone(minNumberOfPheromones);
			}
			if(cell.isState(antCell)){
				Ant ant = new Ant(cell.getCenterX(), cell.getCenterY(), antLifeTime, 1);
				ants.add(ant);
				cell.setActor(ant);
			}
		}
	}
	@Override 
	public void createOrRemovePerStep(){
		for(int i = 0; i < antsBornePerTimeStep; i++){
			if(ants.size() >= maximumAntsInSimulation){
				break;
			}
			Ant ant = new Ant(nestX, nestY, antLifeTime, 1);
			ants.add(ant);
			Cell antCell = myGrid.getCell(nestX, nestY);
			antCell.setActor(ant);
			ant.setCell(antCell);
		}
	}
	@Override
	public Cell updateCellState(Cell cell) {
		evaporateAndDiffusePheromones(cell);
		
		for(Actor actor : cell.getActors()){
			if(actor instanceof Ant){
				Ant ant = (Ant) actor;
				ant.depleteEnergy();
				if(ant.isDead()){
					ants.remove(ant);
					ant.getCell().setState(emptyCell);
					ant.getCell().removeActor(ant);
				}
				if(ant.hasFoodItem){
					returnToNest(ant);
				}
				else{
					findFoodSource(ant);
				}
			}
		}
		return null;
	}
	public void evaporateAndDiffusePheromones(Cell cell) {
		List<Double> pheromones = cell.getGround().getPheromones();
		for(double pheromone : pheromones){
			List<Cell> neighbors = cell.getAllNeighbors();
			cell.getGround().diffusePheromones(neighbors, diffusionRatio);
			pheromone = pheromone * evaporationRatio;
		}
	}
	public List<Cell> findForwardLocations(Ant ant){
		List<Cell> forwardLocations = new ArrayList<Cell>();
		double angle = -45;
		boolean isMovingUp = (ant.getNextCellY() - ant.getY() > 0);
		for(int i = 0; i < forwardLocations.size(); i++){
			forwardLocations.add(i, ant.findCellGivenAngle(angle, isMovingUp, myGrid));
			angle += 45;
		}
		return forwardLocations;
	}
	
	public List<Cell> findBackwardLocations(List<Cell> forwardLocations, Ant ant){
		List<Cell> allLocations = ant.getCell().getAllNeighbors();
		for(Cell forwardCell : forwardLocations){
			Iterator<Cell> iterator = allLocations.iterator();
			while(iterator.hasNext()){
				if(iterator.next().equals(forwardCell)){
					iterator.remove();
				}
			}
		}
		return allLocations;
	}
	
	public Cell selectLocation(List<Cell> locations){
		Iterator<Cell> iterator = locations.iterator();
		while(iterator.hasNext()){
			Cell possibleLocation = iterator.next();
			if(possibleLocation.isState(obstacleCell)){
				iterator.remove();
			}
			else if(possibleLocation.getActors().size() >= maximumAntsPerLocation){
				iterator.remove();
			}
		}
		if(locations.size() == 0){
			return null;
		}
		double[] locationProbabilities = new double[locations.size()];
		double locationProbabilitySum = 0;
		locationProbabilitySum = findProbabilitiesSum(locations, locationProbabilities, locationProbabilitySum);
		
		Random rand = new Random();
		int randomProb = rand.nextInt((int) locationProbabilitySum);
		double sum = 0;
		int index = 0;
		while(sum < randomProb){
			sum += locationProbabilities[index++];
		}
		return locations.get(index - 1);
	}
	
	public double findProbabilitiesSum(List<Cell> locations, double[] locationProbabilities,
			double locationProbabilitySum) {
		
		for(int i = 0; i < locations.size(); i++){
			double numberOfFoodPheromones = locations.get(i).getGround().getPheromones().get(foodPheromoneIndex);
			double locationProbability = Math.pow((kProbability + numberOfFoodPheromones), nProbability);
			locationProbabilities[i] = locationProbability;
			locationProbabilitySum += locationProbability;
		}
		return locationProbabilitySum;
	}
	
	public void returnToNest(Ant ant){
		List<Cell> forwardLocations = findForwardLocations(ant);
		for(Cell foodSource : foodSources){
			double foodSourceX = foodSource.getCenterX();
			double foodSourceY = foodSource.getCenterY();
			
			if(ant.isAtLocation(foodSourceX, foodSourceY)){
				List<Cell> allNeighbors = ant.getCell().getAllNeighbors();
				Cell maxHomePheromoneCell = findCellWithMaxPheromones(allNeighbors, homePheromoneIndex);
				ant.setOrientation(maxHomePheromoneCell);
		}
		}
		Cell maxPheromoneCell = findCellWithMaxPheromones(forwardLocations, homePheromoneIndex);
		if(maxPheromoneCell == null){
			List<Cell> backwardLocations = findBackwardLocations(forwardLocations, ant);
			maxPheromoneCell = findCellWithMaxPheromones(backwardLocations, homePheromoneIndex);
		}
		if(maxPheromoneCell != null){
			boolean isAtSource = isAtSource(ant, nestX, nestY, homePheromoneIndex);
			if(!isAtSource){
			dropPheromone(ant, homePheromoneIndex);
			}
			ant.getCell().setState(emptyCell);
			ant.move(maxPheromoneCell);
			ant.getCell().setState(antCell);
			pickUpOrDropFood(ant);
		}
	}
	
	
	public void findFoodSource(Ant ant){
		List<Cell> forwardLocations = findForwardLocations(ant);
		if(ant.isAtLocation(nestX, nestY)){
			List<Cell> allNeighbors = ant.getCell().getAllNeighbors();
			Cell maxFoodPheromoneCell = findCellWithMaxPheromones(allNeighbors, foodPheromoneIndex);
			ant.setOrientation(maxFoodPheromoneCell);
		}
		
		Cell cellToMoveTo = selectLocation(forwardLocations);
		if(cellToMoveTo == null){
			List<Cell> backwardLocations = findBackwardLocations(forwardLocations, ant);
			cellToMoveTo = selectLocation(backwardLocations);
		}
		
		if(cellToMoveTo != null){
			boolean isAtSource = false;
			for(Cell foodSource : foodSources){
				isAtSource = isAtSource(ant, foodSource.getCenterX(), foodSource.getCenterY(), foodPheromoneIndex);
			}
			if(!isAtSource){
			dropPheromone(ant, foodPheromoneIndex);
			}
			ant.getCell().setState(emptyCell);
			ant.move(cellToMoveTo);
			ant.getCell().setState(antCell);
			pickUpOrDropFood(ant);
		}
	}
	public void pickUpOrDropFood(Ant ant){
		Cell antCell = ant.getCell();
		for(Cell foodSource : foodSources){
			double foodSourceX = foodSource.getCenterX();
			double foodSourceY = foodSource.getCenterY();
		if(ant.isAtLocation(foodSourceX, foodSourceY)){
			ant.pickUpFoodItem();
			return;
		}
		}
		if(antCell.getCenterX() == nestX && antCell.getCenterY() == nestY){
			ant.dropFoodItem();
		}
	}
	public Boolean isAtSource(Ant ant, double sourceX, double sourceY, int pheromoneIndex){
		PatchOfGround currentGround = ant.getCell().getGround();
		if(ant.isAtLocation(sourceX, sourceY)){
			currentGround.setPheromone(maxNumberOfPheromones, pheromoneIndex);
			return true;
		}
		return false;
	}
	public void dropPheromone(Ant ant, int pheromoneIndex){
			PatchOfGround currentGround = ant.getCell().getGround();
			double pheromonesToDrop = calculatePheromonesToDrop(ant, pheromoneIndex, currentGround);
			if(pheromonesToDrop > 0){
				currentGround.increasePheromone(pheromonesToDrop, pheromoneIndex);
			}
	}
	public double calculatePheromonesToDrop(Ant ant, int pheromoneIndex, PatchOfGround currentGround) {
		List<Cell> neighbors = ant.getCell().getAllNeighbors();
		Cell maxPheromoneCell = findCellWithMaxPheromones(neighbors, pheromoneIndex);
		double maxPheromones = maxPheromoneCell.getGround().getPheromones().get(pheromoneIndex);
		double decrementedPheromones = maxPheromones - gradientConstant;
		double currentPheromones = currentGround.getPheromones().get(pheromoneIndex);
		double pheromonesToDrop = decrementedPheromones - currentPheromones;
		return pheromonesToDrop;
	}
	public Cell findCellWithMaxPheromones(List<Cell> neighbors, int pheromoneIndex){
		Cell maxPheromoneCell = null;
		double maxPheromone = -1;
		for(Cell neighborCell : neighbors){
			PatchOfGround ground = neighborCell.getGround();
			double pheromone = ground.getPheromones().get(pheromoneIndex);
			if(pheromone > maxPheromone){
				maxPheromone = pheromone;
				maxPheromoneCell = neighborCell;
			}
		}
		return maxPheromoneCell;
	}
}
