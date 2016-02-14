package src.controller.Simulation;
import java.util.ArrayList;
import java.util.Random;

import src.Model.Actor;
import src.Model.Cell;
import src.Model.Grid;

public class WaTorSimulation extends Simulation{
	public String returnTitle(){
		return "WaTor Simulation";
	}
	public class Fish extends Actor{

		public Fish(double x, double y, double actorEnergy, double depletionRate){
			super(x, y, actorEnergy, depletionRate);
		}
	}
	public class Shark extends Actor{

		public Shark(double x, double y, double actorEnergy, double depletionRate) {
			super(x, y, actorEnergy, depletionRate);
		}
	}
	private double chronon;
	private double fishReproductionTime;
	private double sharkReproductionTime;
	private double sharkDepletionRate;
	private double eatFishRegenerationRate;
	private double sharkEnergy;
	private int emptyCell = 0;
	private int fishCell = 1;
	private int sharkCell = 2;

	public void setParameters(ArrayList<Double> params){
		double CHRONON_DEFAULT = 1000/60;
		double FISH_REPRO_DEFAULT = 10*1000/60;
		double SHARK_REPRO_DEFAULT = 10*1000/60;
		double SHARK_DEP_DEFAULT = 0.1;
		double FISH_REG_DEFAULT = 1;
		double SHARK_ENERGY_DEFAULT = 100;
		double DEFAULT_VALUE = -1/999;
		chronon = params.get(0);
		fishReproductionTime = params.get(1);
		sharkReproductionTime = params.get(2);
		sharkDepletionRate = params.get(3);
		eatFishRegenerationRate = params.get(4);
		sharkEnergy = params.get(5);
		if(params.get(0) == DEFAULT_VALUE)
			chronon = CHRONON_DEFAULT;
		if(params.get(1) == DEFAULT_VALUE)
			fishReproductionTime = FISH_REPRO_DEFAULT;
		if(params.get(2) == DEFAULT_VALUE)
			sharkReproductionTime = SHARK_REPRO_DEFAULT;
		if(params.get(3) == DEFAULT_VALUE)
			sharkDepletionRate = SHARK_DEP_DEFAULT;
		if(params.get(4) == DEFAULT_VALUE)
			eatFishRegenerationRate = FISH_REG_DEFAULT;
		if(params.get(5) == DEFAULT_VALUE)
			sharkEnergy = SHARK_ENERGY_DEFAULT;
	}

	public void setChrononParameter(double time){
		chronon = time;
	}
	public void setFishReproductionTimeParameter(double time){
		fishReproductionTime = time;
	}
	public void setSharkReproductionTimeParameter(double time){
		sharkReproductionTime = time;
	}
	public void setSharkDepletionRateParameter(double rate){
		sharkDepletionRate = rate;
	}
	public void setEatFishRegenerationRateParameter(double rate){
		eatFishRegenerationRate = rate;
	}
	public void setSharkEnergyParamter(double energy){
		sharkEnergy = energy;
	}
	public void setEmptyCellParameter(int empty){
		emptyCell = empty;
	}
	public void setFishCellParameter(int fish){
		fishCell = fish;
	}
	public void setSharkCellParameter(int shark){
		sharkCell = shark;
	}

	public WaTorSimulation(Grid grid) {
		super(grid);
	}
	public WaTorSimulation() {
		// TODO Auto-generated constructor stub
	}

	public Grid step(){
		Grid myGrid = getGrid();
		ArrayList<Cell> cellList = getGrid().getCells();
		for(int x=0; x<cellList.size(); x++){
			myGrid.replaceCell(updateCellState(cellList.get(x)));
		}
		return myGrid;
	}

	public void initialize(){
		ArrayList<Cell> cellList = getGrid().getCells();
		for(int x=0; x<cellList.size(); x++){
			int cellState = cellList.get(x).getState();
			Cell nextCell = cellList.get(x);
			nextCell.setState(cellState);
			if(cellState == fishCell){
				nextCell.setActor(new Fish(nextCell.getCenterX(), nextCell.getCenterY(), 0, 0));
			}
			if(cellState == sharkCell){
				nextCell.setActor(new Shark(nextCell.getCenterX(), nextCell.getCenterY(), sharkEnergy, sharkDepletionRate));
			}
		}
	}

	public Cell updateCellState(Cell cell) {
		Actor cellActor = cell.getActor();

		if(cellActor == null){
			return cell;
		}
		if(cellActor instanceof Shark){
			Shark shark = (Shark) cellActor;
			shark.depleteEnergy();
			if(shark.isDead()){
				cell.removeActor();
				cell.setState(emptyCell);
				return cell;
			}
			reproduceIfAllowed(shark, false);
			ArrayList<Cell> emptyCells = new ArrayList<Cell>();
			ArrayList<Fish> neighborFish = new ArrayList<Fish>();

			for(Cell neighborCell : cell.getNonDiagonalNeighbors()){
				Actor neighborActor = neighborCell.getActor();
				if(neighborActor != null){
					if(neighborActor instanceof Fish){
						neighborFish.add((Fish) neighborActor);
					}
				}
				else if(!(neighborActor instanceof Shark)){
					emptyCells.add(neighborCell);
				}
			}
			if(neighborFish.size() == 0 && emptyCells.size() > 0){
				moveAtRandom(shark, emptyCells);
			}
			else if(neighborFish.size() > 0){
				//Eat Fish at random
				Random rand = new Random();
				int randomFishIndex = rand.nextInt(neighborFish.size());
				Fish randomFish = neighborFish.get(randomFishIndex);
				Cell randomFishCell = getGrid().getCell(randomFish.getCenterX(), randomFish.getCenterY());
				randomFishCell.removeActor();
				shark.setEnergy(shark.getEnergy() + eatFishRegenerationRate);
				shark.move(randomFish.getCenterX(), randomFish.getCenterY());
				cell.removeActor();
				cell.setState(emptyCell);
				Cell newSharkCell = getGrid().getCell(shark.getCenterX(), shark.getCenterY());
				newSharkCell.setState(sharkCell);
				newSharkCell.setActor(shark);
			}
		}
		if(cellActor instanceof Fish){
			Fish fish = (Fish) cellActor;
			reproduceIfAllowed(fish, true);
			ArrayList<Cell> emptyCells = new ArrayList<Cell>();
			for(Cell neighborCell : cell.getNonDiagonalNeighbors()){
				Actor neighborActor = neighborCell.getActor();
				if(neighborActor == null){
					emptyCells.add(neighborCell);
				}
			}
			moveAtRandom(fish, emptyCells);
		}

		return cell;
	}
	public void reproduceIfAllowed(Actor actor, boolean useFishReproductionTime){
		Actor newActor;
		double reproductionTime = sharkReproductionTime;
		if(useFishReproductionTime){
			reproductionTime = fishReproductionTime;
		}
		if(actor.getTimeSinceReproduced() >= reproductionTime){
			if(useFishReproductionTime){
				actor.resetTimeSinceReproduced();
				newActor = new Fish(actor.getCenterX(), actor.getCenterY(), actor.getEnergy(), actor.getDepletionRate());
			}
			else{
				actor.resetTimeSinceReproduced();
				newActor = new Shark(actor.getCenterX(), actor.getCenterY(), actor.getEnergy(), actor.getDepletionRate());
			}
			Cell newActorCell = getGrid().getCell(newActor.getCenterX(), newActor.getCenterY());
			newActorCell.setActor(newActor);
		}
		else{
			actor.updateTimeSinceReproduced(chronon);
		}
	}
	public void moveAtRandom(Actor actor, ArrayList<Cell> emptyCells){
		Random rand = new Random();
		if(emptyCells.size() == 0){
			return;
		}
		Cell actorCell = getGrid().getCell(actor.getCenterX(), actor.getCenterY());
		actorCell.setState(emptyCell);
		actorCell.removeActor();

		int randomCell = rand.nextInt(emptyCells.size());
		Cell newLocation = emptyCells.get(randomCell);
		actor.move(newLocation.getCenterX(), newLocation.getCenterY());
		if(actor instanceof Shark){
			newLocation.setState(sharkCell);
		}
		if(actor instanceof Fish){
			newLocation.setState(fishCell);
		}
		newLocation.setActor(actor);
	}

	public ArrayList<String> paramsList(){
		ArrayList<String> toRet = new ArrayList<String>();
		toRet.add("fishReproductionTime");
		return toRet;
	}

	public double getParameter(){
		return fishReproductionTime;
	}
	public void setParameter(double aval){
		fishReproductionTime = aval;
	}
	
	public ArrayList<String> getParameters() {
		ArrayList<String> params = new ArrayList<String>();
		params.add("chronon");
		params.add("fishReproductionTime");
		params.add("sharkReproductionTime");
		params.add("sharkDepletionRate");
		params.add("eatFishRegenerationRate");
		params.add("sharkEnergy");
		return params;
	}
}