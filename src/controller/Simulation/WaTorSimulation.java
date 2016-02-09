package src.controller.Simulation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import src.Model.Actor;
import src.Model.Cell;
import src.View.Grid;
import src.controller.CellIterator;

public class WaTorSimulation extends Simulation{
    public String returnTitle(){
        return "WaTor Simulation";
    }
    public class Fish extends Actor{
        
        public Fish(int x, int y, double actorEnergy, double depletionRate){
            super(x, y, actorEnergy, depletionRate);
        }
    }
    public class Shark extends Actor{
        
        public Shark(int x, int y, double actorEnergy, double depletionRate) {
            super(x, y, actorEnergy, depletionRate);
        }
    }
    //Maps X Location -> Y Location -> Actor
    //private HashMap<Integer, HashMap<Integer, Actor>> cellToActorMap;
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
        chronon = params.get(0);
        fishReproductionTime = params.get(1);
        sharkReproductionTime = params.get(2);
        sharkDepletionRate = params.get(3);
        eatFishRegenerationRate = params.get(4);
        sharkEnergy = params.get(5);
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
    /*public void populateCellToActorMap() {
     cellToActorMap = new HashMap<Integer, HashMap<Integer, Actor>>();
     Grid myGrid = getGrid();
     for(int i = 0; i < myGrid.getGridWidth(); i++){
     for(int j = 0; j < myGrid.getGridHeight(); j++){
     if(cellToActorMap.containsKey(i)){
					cellToActorMap.get(i).put(j, null);
     }
     else{
					HashMap<Integer, Actor> tempMap = new HashMap<Integer, Actor>();
					tempMap.put(j, null);
					cellToActorMap.put(i, tempMap);
     }
     }
     }
     }
     */
    public Grid step(){
        Grid myGrid = getGrid();
        CellIterator cellIt = myGrid.getCellIterator();
        for(Cell cell: cellIt){
            myGrid.setCell(updateCellState(cell));
        }
        return myGrid;
    }
    
    public void initialize(ArrayList<Integer> cellStates){
        int statesListIndex = 0;
        CellIterator cellIt = getGrid().getCellIterator();
        for(Cell nextCell: cellIt){
            int cellState = cellStates.get(statesListIndex++);
            nextCell.setState(cellState);
            
            if(cellState == fishCell){
                nextCell.setActor(new Fish(nextCell.getX(), nextCell.getY(), 0, 0));
            }
            if(cellState == sharkCell){
                nextCell.setActor(new Shark(nextCell.getX(), nextCell.getY(), sharkEnergy, sharkDepletionRate));
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
            
            for(Cell neighborCell : getGrid().getNonDiagonalNeighbors(cell)){
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
                Cell randomFishCell = getGrid().getCell(randomFish.getX(), randomFish.getY());
                randomFishCell.removeActor();
                shark.setEnergy(shark.getEnergy() + eatFishRegenerationRate);
                shark.move(randomFish.getX(), randomFish.getY());
                cell.removeActor();
                cell.setState(emptyCell);
                Cell newSharkCell = getGrid().getCell(shark.getX(), shark.getY());
                newSharkCell.setState(sharkCell);
                newSharkCell.setActor(shark);
            }
        }
        if(cellActor instanceof Fish){
            Fish fish = (Fish) cellActor;
            reproduceIfAllowed(fish, true);
            ArrayList<Cell> emptyCells = new ArrayList<Cell>();
            for(Cell neighborCell : getGrid().getNonDiagonalNeighbors(cell)){
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
                newActor = new Fish(actor.getX(), actor.getY(), actor.getEnergy(), actor.getDepletionRate());
            }
            else{
                actor.resetTimeSinceReproduced();
                newActor = new Shark(actor.getX(), actor.getY(), actor.getEnergy(), actor.getDepletionRate());
            }
            Cell newActorCell = getGrid().getCell(newActor.getX(), newActor.getY());
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
        Cell actorCell = getGrid().getCell(actor.getX(), actor.getY());
        actorCell.setState(emptyCell);
        actorCell.removeActor();
        
        int randomCell = rand.nextInt(emptyCells.size());
        Cell newLocation = emptyCells.get(randomCell);
        actor.move(newLocation.getX(), newLocation.getY());
        if(actor instanceof Shark){
            newLocation.setState(sharkCell);
        }
        if(actor instanceof Fish){
            newLocation.setState(fishCell);
        }
        newLocation.setActor(actor);
    }
}