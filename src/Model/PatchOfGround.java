package src.Model;
import java.util.ArrayList;
import java.util.List;
/**This entire class is part of my masterpiece.
 * Mohab Gabal
 *
 *This class is one of two classes that is made to make the life of the person coding a new Simulation easier. Its purpose is
 *kind of similar to that of Actor, but at the same time it is completely different. Actor was made to mimic something moving
 *across the grid, but this class is made to mimic something that is stationary on a single cell. The two new simulations I 
 *coded for the second sprint use this class heavily. It keeps a list of different pheromones, or chemical signals, that can
 *be either updated or diffused to its neighbors. Keeping it in a list makes it possible to support any number of pheromones
 *on a single patch of ground. The only thing that needs to be tracked of is the index and the specific pheromone it corresponds
 *to, which I did in the Foragin Ants Simuation. This class has no dependencies, which makes sense since it was created with
 *the intention of attaching it to a cell. 
 * 
 */
public class PatchOfGround{
	private List<Double> pheromones;
	
	public PatchOfGround(){
		pheromones = new ArrayList<Double>();
	}
	public int numberOfPheromones(){
		int numberOfPheromones = 0;
		for(Double pheromone : pheromones){
			if(pheromone > 0){
				numberOfPheromones++;
			}
		}
		return numberOfPheromones;
	}
	public void addPheromone(double pheromone){
		pheromones.add(pheromone);
	}
	public void increasePheromone(double pheromone, int index){
		pheromones.set(index, pheromone);
	}
	public void setPheromone(double pheromone, int index){
		pheromones.set(index, pheromone);
	}
	public List<Double> getPheromones(){
		return pheromones;
	}
	public void diffusePheromones(List<Cell> neighbors, double diffusionRatio){
		for(Cell cell : neighbors){
			List<Double> neighborPheromones = cell.getGround().getPheromones();
			for(int i = 0; i < pheromones.size(); i++){
				double pheromone = diffusionRatio * pheromones.get(i);
				if(i < neighborPheromones.size()){
					double pheromoneToAdd = neighborPheromones.get(i);
					pheromone += pheromoneToAdd;
				}
				neighborPheromones.add(i, pheromone);
			}
		}
	}
}
