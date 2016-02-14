package src.View;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCombination.Modifier;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import src.controller.EventListener;
import src.controller.PropertiesReader;
import src.controller.XMLParser;
import src.Model.Cell;
import src.Model.Grid;

import java.util.*;
/**
 * Created by davidyan on 2/1/16.
 */
public class Display {
	private Button myPlay, myPause, myStep;
	private Slider mySlider;
	private GridPane myGrid;
	private Canvas myCanvas;
	private Grid grid;
	private MenuItem newProgram, newOpen, newExit;
	private Scene myScene;
	private File myFile;
	private Stage myStage;
	private HashMap<Series<Number, Number>, Integer> myGraphSeries;
	private BarChart<String,Number> myGraph;
	private PropertiesReader myUseReader;

	public Display(Scene scene, Group root, Stage stage, PropertiesReader myReader) {
		myScene = scene;
		myUseReader = myReader;
		makeToolbar(root, stage);
		myGrid = new GridPane();
		myGrid.setAlignment(Pos.CENTER);
		myGrid.setHgap(10);
		myGrid.setVgap(10);
		myGrid.setPadding(new Insets(25, 0, 0, -75));

		myCanvas = new Canvas(550,550);
		GraphicsContext gc = myCanvas.getGraphicsContext2D();

		gc.setFill(Color.DARKGRAY);
		gc.fillRect(0, 0, 550, 550);
		myGrid.add(myCanvas, 0, 0, 8, 5);

		mySlider = new Slider(0, 20, 2);
		mySlider.setShowTickMarks(true);
		mySlider.setShowTickLabels(true);
		mySlider.setMajorTickUnit(1);
		mySlider.setBlockIncrement(10.0);

		mySlider.setMinWidth(225);

		myPlay = new Button(myUseReader.getString("PlayButton"));
		myPause = new Button(myUseReader.getString("PauseButton"));
		myStep = new Button(myUseReader.getString("StepButton"));

		myGrid.add(myPlay, 0, 6);
		myGrid.add(myPause, 1, 6);
		myGrid.add(myStep, 2, 6);
		myGrid.add(mySlider, 4, 6, 5, 1);
		//drawGraph();
		root.getChildren().add(myGrid);
	}

	public void addEventListener(EventListener listener) {
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
				} catch (Exception e){

				}
			}
		});
	}

	public void makeToolbar(Group root, Stage stage){
		Stage myStage = stage;
		Menu cellMenu = new Menu(myUseReader.getString("MakeMenu"));
		newOpen = new MenuItem(myUseReader.getString("MakeOpen"));
		newExit = new MenuItem(myUseReader.getString("MakeExit"));
		Modifier myModifier;
		myModifier = KeyCombination.META_DOWN;
		newOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, myModifier));
		newExit.setAccelerator(new KeyCodeCombination(KeyCode.W, myModifier));
		cellMenu.getItems().addAll(newOpen, newExit);
		MenuBar menuBar = new MenuBar();
		menuBar.useSystemMenuBarProperty().set(true);
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(menuBar);
		root.getChildren().add(borderPane);
		menuBar.getMenus().add(cellMenu);
	}

	public void makeParamSliders(List<String> listParams, EventListener listener){
		int idx = 3;
		for(String aString: listParams){
			Slider aSlider = new Slider(0, 1, 0.5);
			aSlider.setShowTickMarks(true);
			aSlider.setShowTickLabels(true);
			aSlider.setMajorTickUnit(0.1);
			aSlider.setBlockIncrement(0.1);

			aSlider.valueProperty().addListener(event -> {
				listener.changeParameter((double)aSlider.getValue());
			});

			myGrid.add(aSlider, 10, idx, 2, 1 );
			idx++;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void drawGraph(HashMap<Integer,Integer> myMap) {
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

		myGrid.add(myGraph, 10, 2, 2, 1);
	}

	public void updateGraph(HashMap<Integer, Integer> myMap){
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
