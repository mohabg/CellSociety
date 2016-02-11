dwy3, zap3 Code Review

#Duplicated Code Section
####My suggested changes for Zdravko:
> - I see a lot of repeated code in SegregationSimulation.java, GameOfLifeSimulation.java, and SquareGrid.java. Many of these errors come as the result of writing the same exact code repeatedly with the same exact function. What I would suggest would be creating those lines of code in the abstract classes like Grid.java or Simulation.java. Thus, when each of those specific simulation classes are extended, you can avoid have to use duplicated method code. This would be a good way to write only half the code you currently have with regards to those classes.

####Zdravko's suggested changes for me:
> - Within Simulation.java and WaTorSimulation.java. Both of these classes have similar methods, even though WaTor extends Simulation. Thus, I should just remove duplicated methods in WaTor and just use a reference to the method defined in the abstract class.

#Long Methods Section
