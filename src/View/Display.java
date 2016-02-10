package src.View;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import src.controller.EventListener;
import src.controller.XMLParser;
import src.Model.Cell;
import java.util.*;
/**
 * Created by davidyan on 2/1/16.
 */
public class Display {
    private Button myPlay, myPause, myStep, myReplay;
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

    
    public Display(Scene scene, Group root, Stage stage) {
        myScene = scene;
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
        
        myPlay = new Button("Play");
        myPause = new Button("Pause");
        myStep = new Button("Step");
        myReplay = new Button("\u21BA");
        
        myGrid.add(myPlay, 0, 6);
        myGrid.add(myPause, 1, 6);
        myGrid.add(myStep, 2, 6);
        myGrid.add(myReplay, 3, 6);
        myGrid.add(mySlider, 4, 6, 5, 1);
        //drawGraph();
        root.getChildren().add(myGrid);
    }
    
    public void addEventListener(EventListener listener) {
        myPlay.setOnAction(event -> listener.playAnimation());
        myPause.setOnAction(event ->listener.pauseAnimation());
        myStep.setOnAction(event ->listener.stepAnimation());
        mySlider.valueProperty().addListener(event -> {
            listener.onSliderMove((int)mySlider.getValue());
        });
        newOpen.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose XML File");
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
        Menu cellMenu = new Menu("CellSociety Menu");
        newProgram = new MenuItem("New");
        newOpen = new MenuItem("Open");
        newExit = new MenuItem("Exit");
        Modifier myModifier;
        myModifier = KeyCombination.META_DOWN;
        newProgram.setAccelerator(new KeyCodeCombination(KeyCode.N, myModifier));
        newOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, myModifier));
        newExit.setAccelerator(new KeyCodeCombination(KeyCode.W, myModifier));
        cellMenu.getItems().addAll(newProgram, newOpen, newExit);
        MenuBar menuBar = new MenuBar();
        menuBar.useSystemMenuBarProperty().set(true);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        root.getChildren().add(borderPane);
        menuBar.getMenus().add(cellMenu);
    }
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void drawGraph() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        myGraph = 
            new BarChart<String,Number>(xAxis,yAxis);
        myGraph.setTitle("Cell Society");
        xAxis.setLabel("Cells");       
        yAxis.setLabel("Amount");
        
		String label;
		Color color;
		XYChart.Series<Number, Number> series;
		
        XYChart.Series<String, Number> series1 = new XYChart.Series();
        series1.setName("Cell Society");       
        series1.getData().add(new XYChart.Data("Cell1", 1));
        series1.getData().add(new XYChart.Data("Cell2", 2));
        myGraph.getData().add(series1);

        myGrid.add(myGraph, 10, 2, 2, 1);
    }
    
    public void updateGraph(){
    	myGraph.getData().get(0).getData().get(0).setYValue(500);
    	myGraph.getData().get(0).getData().get(1).setYValue(200);
    }
    
    public void removeGraph(){
    	myGrid.getChildren().remove(myGraph);
    }
    
    
    public void setGraphTitle(String s){
        myGraph.setTitle(s);
    }
    
    public void draw(Grid grid, HashMap<Integer, Color> statesMap) {
        myCanvas.getGraphicsContext2D().setFill(Color.DARKGRAY);
        myCanvas.getGraphicsContext2D().fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
        grid.draw(myCanvas, statesMap);
    }
    
    public File getFile(){
        return myFile;
    }
    
}