package cellSociety;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCombination.Modifier;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.LineChart;
import cell.Cell;

/**
 * Created by davidyan on 2/1/16.
 */
public class Display {
    private Scene myScene;
    private Group myRoot;
    private Button myPlay, myPause, myStep, myReplay;
    private Slider mySlider;
    private GridPane myGrid;
    private Canvas myCanvas;
    private Grid myGrid2;
    private MenuItem menuNew, menuOpen, menuExit;
    private LineChart<Number, Number> myGraph;

    public void createScene(Stage stage) {
        myRoot = new Group();
        myScene = new Scene(myRoot, 1200, 700);
        makeToolbar();
        checkEventsMenu(stage);

        Cell base = new Cell(0);
        myGrid2 = new Grid(base,30,30);

        myGrid = new GridPane();
        myGrid.setAlignment(Pos.CENTER);
        myGrid.setHgap(10);
        myGrid.setVgap(10);
        myGrid.setPadding(new Insets(25, 0, 0, -75));

        myCanvas = new Canvas(550,550);
        GraphicsContext gc = myCanvas.getGraphicsContext2D();

        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0, 0, 550, 550);
        drawGrid(myCanvas,myGrid2);
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
        myRoot.getChildren().add(myGrid);
        stage.setScene(myScene);
        stage.show();
    }

    public void makeToolbar(){
        Menu fileMenu = new Menu("CellSociety Menu");
        menuNew = new MenuItem("New");
        menuOpen = new MenuItem("Open");
        menuExit = new MenuItem("Exit");

        Modifier modifier;

        modifier = KeyCombination.META_DOWN;
        menuOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, modifier));
        menuNew.setAccelerator(new KeyCodeCombination(KeyCode.N, modifier));
        menuExit.setAccelerator(new KeyCodeCombination(KeyCode.W, modifier));

        fileMenu.getItems().addAll(menuNew, menuOpen, menuExit);

        MenuBar menuBar = new MenuBar();

        menuBar.useSystemMenuBarProperty().set(true);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        myRoot.getChildren().add(borderPane);
        menuBar.getMenus().add(fileMenu);
    }
    public void checkEventsMenu(Stage s){
        menuExit.setOnAction(new EventHandler<ActionEvent>() {
             public void handle(ActionEvent e) {
                //System.out.println("time to close");
                s.close();
            }
        });
    }

    public void drawGrid(Canvas toUseCanvas, Grid toUseGrid){
        double width = toUseCanvas.getWidth();
        double height = toUseCanvas.getHeight();
        int gridWidth = toUseGrid.getGridWidth();
        int gridHeight = toUseGrid.getGridHeight();
        double myStrokeWidth = 0.05;
        Color myStrokeColor = Color.DARKGRAY;


        double cellWidth = width / (gridWidth + myStrokeWidth * (gridWidth - 1));
        double cellHeight = height / (gridHeight + myStrokeWidth * (gridHeight - 1));

        double x, y;
        GraphicsContext gc = toUseCanvas.getGraphicsContext2D();
        for (Cell cell : toUseGrid.getCellIterator()) {
            gc.setFill(Color.LIGHTBLUE);
            x = cellWidth * myStrokeWidth + cellWidth * cell.getX() + (cell.getX() - 1) * cellWidth * myStrokeWidth;
            y = cellWidth * myStrokeWidth + cellHeight * cell.getY() + (cell.getY() - 1) * cellHeight * myStrokeWidth;
            gc.fillRect(x, y, cellWidth, cellHeight);
        }

        gc.setStroke(myStrokeColor);
        gc.setLineWidth(myStrokeWidth * cellWidth * 2.0);
        gc.strokeRect(0.0, 0.0, width, height);

    }

    public void drawGraph() {

        NumberAxis xAxis = new NumberAxis(0,100,10);
        NumberAxis yAxis = new NumberAxis(0.0, 1.0, .10);

        myGraph = new LineChart<Number,Number>(xAxis,yAxis);

        myGraph.setTitle("Cell Simulation");
        myGraph.setCreateSymbols(false);
        myGraph.setLegendVisible(false);

        myGrid.add(myGraph, 10, 2, 2, 1);

    }


}
