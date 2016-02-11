dwy3, zap3 Code Review

####My suggested changes for Zdravko:
> - I see a lot of repeated code in SegregationSimulation.java, GameOfLifeSimulation.java, and SquareGrid.java. Many of these errors come as the result of writing the same exact code repeatedly with the same exact function. What I would suggest would be creating those lines of code in the abstract classes like Grid.java or Simulation.java. Thus, when each of those specific simulation classes are extended, you can avoid have to use duplicated method code.
