# Cell Society Design - Team 10
#Introduction
> - By Mohab Gabal, Adam Tache, David Yan

The goal of Cell Society is to design and implement a program that will allow the user to run and visualize various types of cell automata simulations. For each separate cell automata simulation, the user should be able to select between different options and decide what typ of simulation he/she would like to see. There should also be extra implementation including a graph that will be displayed as the simulation is running, and this graph will show the population sizes of different cells as the simulation runs. In total, the most important consideration, in our view, is designing the program in such a way that it will be flexible with regards to adding new rules with simulations or overriding existing simulations.
#Overview
#User Interface
![This is our design](http://www.davidwyan.com/resources/proj1design.png)
We wanted to design a UI that is as simple and straightforward for the user as possible. Some basic functionality that we have thought of include an option for the user to first load any specific simulation they want. To this end, a simple drop down menu called "upload" or "choose" will allow the user to load a specific simulation file, preferably a separate xml file, that will instantiate a new simulation. We also want the user to play, pause, and replay the simulation at any time they choose so we will also incorporate separate buttons that will handle those cases. There will also be an interactive component with the grid in which the user can click on a cell to change a single cell's state manually, thus allowing for different observances of a particular simulation. Other than that,we will do our best to make the grid responsive and functional to the size of the window it is being run on, and position the wall, graph, and buttons in such a way that makes it very intuitive and simple for the user.

#Design Details
#Design Considerations
#Team Responsibilities

We decided to divide the project into three main roles. David will work primarily on the front-end development of this project, which includes designing the visual layouts of the various screens and the look of the grids and corresponding graphs. Mohab will work primarily on the back-end development, which includes designing the code that actually runs and creates the simulations. Adam will work on merging the gap between the front-end and back-end development, which includes the creation of the XML files for loading into the simulations and populating the cell grids.
