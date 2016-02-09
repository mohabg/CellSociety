package src.View;
import java.util.HashMap;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCombination.Modifier;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import src.controller.EventListener;

/**
 * Created by davidyan on 2/1/16.
 */
public class Display {
    private Button myPlay, myPause, myStep, myReplay;
    private Slider mySlider;
    private GridPane myGrid;
    private Canvas myCanvas;
    private Grid myGrid2;
    private MenuItem newProgram, newOpen, newExit;
    private LineChart<Number, Number> myGraph;
    private Scene myScene;

    public Display(Scene scene, Group root) {
        myScene = scene;
        makeToolbar(root);

//        Cell base = new Cell(0);
//        myGrid2 = new Grid(base,30,30);

        myGrid = new GridPane();
        myGrid.setAlignment(Pos.CENTER);
        myGrid.setHgap(10);
        myGrid.setVgap(10);
        myGrid.setPadding(new Insets(25, 0, 0, -75));

        myCanvas = new Canvas(550,550);
        GraphicsContext gc = myCanvas.getGraphicsContext2D();

        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0, 0, 550, 550);
//        drawGrid(myCanvas,myGrid2);
        myGrid.add(myCanvas, 0, 0, 8, 5);

        mySlider = new Slider(0, 100, 50);
        mySlider.setShowTickMarks(true);
        mySlider.setShowTickLabels(true);
        mySlider.setMajorTickUnit(10.0);
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
        drawGraph();
        root.getChildren().add(myGrid);
    }

    public void addEventListener(EventListener listener) {
        myPlay.setOnAction(event -> listener.playAnimation());
        myPause.setOnAction(event ->listener.pauseAnimation());
        myStep.setOnAction(event ->listener.stepAnimation());

    }

    public void makeToolbar(Group root){
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

//    public void drawGrid(Canvas toUseCanvas, Grid toUseGrid){
//        double width = toUseCanvas.getWidth();
//        double height = toUseCanvas.getHeight();
//        int gridWidth = toUseGrid.getGridWidth();
//        int gridHeight = toUseGrid.getGridHeight();
//        double myStrokeWidth = 0.05;
//        Color myStrokeColor = Color.DARKGRAY;
//
//
//        double cellWidth = width / (gridWidth + myStrokeWidth * (gridWidth - 1));
//        double cellHeight = height / (gridHeight + myStrokeWidth * (gridHeight - 1));
//
//        double x, y;
//        GraphicsContext gc = toUseCanvas.getGraphicsContext2D();
//        for (Cell cell : toUseGrid.getCellIterator()) {
//            gc.setFill(Color.LIGHTBLUE);
//            x = cellWidth * myStrokeWidth + cellWidth * cell.getX() + (cell.getX() - 1) * cellWidth * myStrokeWidth;
//            y = cellWidth * myStrokeWidth + cellHeight * cell.getY() + (cell.getY() - 1) * cellHeight * myStrokeWidth;
//            gc.fillRect(x, y, cellWidth, cellHeight);
//        }
//
//        gc.setStroke(myStrokeColor);
//        gc.setLineWidth(myStrokeWidth * cellWidth * 2.0);
//        gc.strokeRect(0.0, 0.0, width, height);
//
//    }

    public void drawGraph() {

        NumberAxis xAxis = new NumberAxis(0,100,10);
        NumberAxis yAxis = new NumberAxis(0.0, 1.0, .10);

        myGraph = new LineChart<Number, Number>(xAxis,yAxis);

        myGraph.setTitle("Cell Simulation");
        myGraph.setCreateSymbols(false);
        myGraph.setLegendVisible(false);

        myGrid.add(myGraph, 10, 2, 2, 1);
    }
    public void draw(Grid grid, HashMap<Integer, Color> statesMap) {
        myCanvas.getGraphicsContext2D().setFill(Color.DARKGRAY);
        myCanvas.getGraphicsContext2D().fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
        grid.draw(myCanvas, statesMap);
    }


}
