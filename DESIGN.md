# Cell Society Design - Team 10
#Introduction
> - By Mohab Gabal, Adam Tache, David Yan

The goal of Cell Society is to design and implement a program that will allow the user to run and visualize various types of cell automata simulations. For each separate cell automata simulation, the user should be able to select between different options and decide what typ of simulation he/she would like to see. There should also be extra implementation including a graph that will be displayed as the simulation is running, and this graph will show the population sizes of different cells as the simulation runs. In total, the most important consideration, in our view, is designing the program in such a way that it will be flexible with regards to adding new rules with simulations or overriding existing simulations.
#Overview
![This is our design](http://www.davidwyan.com/resources/overview1.png)
The first class that is loaded is called `MainHandler`. This class is responsible for creating and managing all the objects that handle the functionality of the project. When loading a file, an instance of `XMLReader` is created which reads the game parameters from that file, and calls a method which returns a new `Grid` instance which is a 2D array of 'Cell' objects. 
The cell objects hold variables/method for its position in the grid, its current state, all available states, and all of its neighbors. The game parameters are also used to create a concrete subclass of the `Simulation` class which stores two instances of the grid, game rules, and steps through the simulation. One instance of the grid is saved, and used in order to update the other grid, which will be used in the next frame. 

In order to display the Simulation, an instance of `UIManager` is also created in MainHandler. This class is responsible for taking the events that occur in the back end, and rendering them in the front end. In the display it shows the user, there are options for playing, stopping, resetting the simulation. In addition, there is a step button which lets the user step through the simulation frame by frame. 
#User Interface
![This is our design idea](http://www.davidwyan.com/resources/proj1design.png)
We wanted to design a UI that is as simple and straightforward for the user as possible. Some basic functionality that we have thought of include an option for the user to first load any specific simulation they want. To this end, a simple drop down menu called "upload" or "choose" will allow the user to load a specific simulation file, preferably a separate xml file, that will instantiate a new simulation. We also want the user to play, pause, and replay the simulation at any time they choose so we will also incorporate separate buttons that will handle those cases. There will also be an interactive component with the grid in which the user can click on a cell to change a single cell's state manually, thus allowing for different observances of a particular simulation. Other than that,we will do our best to make the grid responsive and functional to the size of the window it is being run on, and position the wall, graph, and buttons in such a way that makes it very intuitive and simple for the user. Ideally, with regards to error prevention, the program should throw exception errors if the user does not choose a file for simulation. Other errors may come up as we begin coding the game, during which we will try to just throw exception errors for the user.

#Design Details

Classes:

- Cell: class holds individual cells and information corresponding to each cell, including the position of the cell in the grid, its neighbors, and methods for manipulating state properties.
- Grid: class is centered around a 2D array of Cell objects and primarily serves as a container for the cells. There will be methods for accessing and manipulating data within the Grid.
- Simulation: abstract class that stands as the supper class for various types of simulations and is used to update the current state of the simulation in the back-end. The Simulation subclasses will contain specific information relevant to the specific simulations they represent.
- MainHandler: class holds the main for the project and runs step-by-step what actions are required to create and run each simulation. This includes working with the XMLReader and UIManager and combining separate functionality into a working project.
- XMLReader: class is passed the uploaded XML file and parses the game rules and returns them back to the MainHandler for instantiation.
-UIManager: handles front-end input events and graphically renders the simulation. 

#Design Considerations

One of the main considerations we made for this project was the tradeoff between subclassing cells vs. subclassing simulations. We decided against the former because we felt there wasn't enough of a difference between different cells that would require handling these differences in subclasses and wanted the Cell class as more of an overall structural object. On the other hand, we felt that different simulations had unique features that required subclasses for them. Another consideration was how to contain the actual Grid, and we considered various options including a GridPane and a TilePane. We decided to use a 2D array right now because we feel it's the most reliable option that we can ensure will work.


#Team Responsibilities

We decided to divide the project into three main roles. David will work primarily on the front-end development of this project, which includes designing the visual layouts of the various screens and the look of the grids and corresponding graphs. Mohab will work primarily on the back-end development, which includes designing the code that actually runs and creates the simulations. Adam will work on merging the gap between the front-end and back-end development, which includes the creation of the XML files for loading into the simulations and populating the cell grids.
