package src.View;
import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCombination.Modifier;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import src.controller.EventListener;
import src.controller.PropertiesReader;
import src.Model.Grid;

import java.util.*;
/**
 * Created by davidyan on 2/1/16.
 * This entire file is part of my code masterpiece.
 * I like how this class is organized in that, everything displayed for the user to see is created within this class
 * The main Display constructor has calls to methods that create one single visual aspect of the screen
 * Example: createButtons method creates all the buttons displayed on the screen, same goes for createSpeedSlider, among others
 * With the code organized in this way, most of the visualization in this program is handled in a single class
 * This code also allows for easy creation of new visual elements
 * Example: if a user would like to create a new button, all they would have to do would be to update the createButtons method
 * Each method is short and very easy to understand, thus allowing for easy debugging
 * With this design, one class handles putting all the visual nodes on the screen
 * 
 */
public class Display {
	private Button myPlay, myPause, myStep;
	private Slider mySlider;
	private GridPane myGrid;
	private Canvas myCanvas;
	private MenuItem newOpen, newExit;
	private File myFile;
	private Stage myStage;
	private BarChart<String,Number> myGraph;
	private PropertiesReader myUseReader;
	private Group myRoot;
	private int screenColumn = 6;
	private int screenRow = 0;

	public Display(Scene scene, Group root, Stage stage, PropertiesReader myReader) {
		myRoot = root;
		myUseReader = myReader;
		makeToolbar(root, stage);
		createGridPane();
		createCanvas();
		createSpeedSlider();
		createButtons();
		//add elements to the grid after they are created
		myGrid.add(myCanvas, screenRow, screenRow, 8, 5);
		myGrid.add(myPlay, screenRow, screenColumn);
		myGrid.add(myPause, ++screenRow, screenColumn);
		myGrid.add(myStep, ++screenRow, screenColumn);
		myGrid.add(mySlider, screenRow+2, screenColumn, 5, 1);
		addToRoot(myGrid);
	}
	
	private void createCanvas(){
		myCanvas = new Canvas(550,550);
		GraphicsContext gc = myCanvas.getGraphicsContext2D();
		gc.setFill(Color.DARKGRAY);
		gc.fillRect(0, 0, 550, 550);
	}
	
	private void createButtons(){
		myPlay = new Button(myUseReader.getString("PlayButton"));
		myPause = new Button(myUseReader.getString("PauseButton"));
		myStep = new Button(myUseReader.getString("StepButton"));
	}
	
	private void addToRoot(Object anItem){
		myRoot.getChildren().add((Node) anItem);
	}
	
	private void createSpeedSlider(){
		mySlider = new Slider(0, 20, 2);
		mySlider.setShowTickMarks(true);
		mySlider.setShowTickLabels(true);
		mySlider.setMajorTickUnit(1);
		mySlider.setBlockIncrement(10.0);
		mySlider.setMinWidth(225);
	}
	
	public void createGridPane(){
		myGrid = new GridPane();
		myGrid.setAlignment(Pos.CENTER);
		myGrid.setHgap(10);
		myGrid.setVgap(10);
		myGrid.setPadding(new Insets(25, 0, 0, -75));
	}

	public void addEventListener(EventListener listener) {
		//add events to handle the button events
		myPlay.setOnAction(event -> listener.playAnimation());
		myPause.setOnAction(event ->listener.pauseAnimation());
		myStep.setOnAction(event ->listener.stepAnimation());
		newExit.setOnAction(event ->listener.onExitClicked());
		mySlider.valueProperty().addListener(event -> {
			listener.onSliderMove((int)mySlider.getValue());
		});
		newOpen.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle(myUseReader.getString("ChooseFile"));
				myFile = fileChooser.showOpenDialog(myStage);
				try {
					listener.onFileSelection(myFile);
				} catch (Exception e){e.printStackTrace();}
			}
		});
	}

	public void makeToolbar(Group root, Stage stage){
		//create the menu displayed on the toolbar
		makeMenu();
		Menu cellMenu = makeMenu();
		Modifier myModifier = KeyCombination.META_DOWN;
		newOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, myModifier));
		newExit.setAccelerator(new KeyCodeCombination(KeyCode.W, myModifier));
		MenuBar menuBar = new MenuBar();
		menuBar.useSystemMenuBarProperty().set(true);
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(menuBar);
		addToRoot(borderPane);
		menuBar.getMenus().add(cellMenu);
	}
	
	private Menu makeMenu(){
		//add the list options that are actually displayed on the toolbar
		Menu cellMenu = new Menu(myUseReader.getString("MakeMenu"));
		newOpen = new MenuItem(myUseReader.getString("MakeOpen"));
		newExit = new MenuItem(myUseReader.getString("MakeExit"));
		cellMenu.getItems().addAll(newOpen, newExit);
		return cellMenu;
	}

	public void makeParamSliders(List<String> listParams, EventListener listener){
		//creates a slider for every parameter in a specific simulation
		//with this, the user can manually change the parameters of a simulation
		int idx = 3;
		for(String aString: listParams){
			Slider aSlider = createSlider(listener, aString);
			myGrid.add(aSlider, 10, idx, 2, 1 );
			idx++;
		}
	}
	
	public Slider createSlider(EventListener e, String aString){
		Slider aSlider = new Slider(0, 1, 0.5);
		aSlider.setId(aString);
		aSlider.setShowTickMarks(true);
		aSlider.setShowTickLabels(true);
		aSlider.setMajorTickUnit(0.1);
		aSlider.setBlockIncrement(0.1);
		aSlider.valueProperty().addListener(event -> {
			e.changeParameter((double)aSlider.getValue());
		});
		return aSlider;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void drawGraph(HashMap<Integer,Integer> myMap) {
		//creates the first instance of the graph by taking in a map that maps cell states to counts of each state
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		myGraph = new BarChart<String,Number>(xAxis,yAxis);
		myGraph.setTitle(myUseReader.getString("Title"));
		xAxis.setLabel(myUseReader.getString("xAxis"));       
		yAxis.setLabel(myUseReader.getString("yAxis"));
		for(Integer anInt: myMap.keySet()){
			XYChart.Series<String, Number> series1 = new XYChart.Series();
			series1.setName("");
			series1.getData().add(new XYChart.Data("", myMap.get(anInt)));
			myGraph.getData().add(series1);
		}
		myGrid.add(myGraph, screenColumn+4, screenRow, screenRow, 1);
	}

	public void updateGraph(HashMap<Integer, Integer> myMap){
		//updates the displayed graph by taking in a map that maps cell states to updated counts of each state
		Integer toUseLength = myMap.keySet().size();
		for(int i=0; i<toUseLength; i++){
			if(!myMap.containsKey(i)){
				myMap.put(i, 0);
			}else{
				myGraph.getData().get(i).getData().get(0).setYValue(myMap.get(i));
			}
		}
	}

	public void removeGraph(){
		myGrid.getChildren().remove(myGraph);
	}

	public void setGraphTitle(String s){
		myGraph.setTitle(s);
	}

	public void draw(Grid grid, HashMap<Integer, Color> statesMap){
		grid.draw(myCanvas, statesMap);
	}

	public File getFile(){
		return myFile;
	}

}
