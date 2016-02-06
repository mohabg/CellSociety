package src.controller;
import src.Model.Cell;
import src.View.Display;
import src.View.Grid;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
<<<<<<< HEAD

import java.util.ArrayList;
import java.util.Random;
import src.controller.Simulation.*;
public class MainDriver implements EventListener {

    private Display myDisplay;
    private Group myRoot;
    private boolean isRunning = false;
    private int myFPS = 2;
    Timeline myAnimation = new Timeline();
    private Stage myStage;
    private Scene myScene;
    private Cell baseCell = new Cell(0);
    private Grid myGrid = new Grid(baseCell,25,25);
    Duration length;
    private WaTorSimulation mySim;

    public MainDriver(Stage stage) {
    	
    	Random r = new Random();
    	ArrayList<Integer> cellStates = new ArrayList<Integer>();
        for(Cell cell: myGrid.getCellIterator()){
            int randy = r.nextInt(3);
            cellStates.add(randy);
        }
        mySim = new WaTorSimulation(myGrid);
        mySim.initialize(cellStates);
        myStage = stage;
        setSimulationFPS(myFPS);
        myRoot = new Group();
        myScene = new Scene(myRoot, 1100, 700);
        myDisplay = new Display(myScene, myRoot);
        myDisplay.addEventListener(this);
        myDisplay.draw(myGrid);
        myStage.setScene(myScene);
        myStage.show();
    }
=======
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

public class MainDriver implements EventListener {

	private Display myDisplay;
	private Group myRoot;
	private boolean isRunning = false;
	private int myFPS = 2;
	Timeline myAnimation = new Timeline();
	private Stage myStage;
	private Scene myScene;
	private Grid myGrid;
	Duration length;
	HashMap<Integer, Color> statesMap;

	public MainDriver(Stage stage) throws ParserConfigurationException, SAXException, IOException, NoSuchFieldException, SecurityException, ClassNotFoundException, DOMException, IllegalArgumentException, IllegalAccessException {
		myStage = stage;
		setSimulationFPS(myFPS);
		myRoot = new Group();
		myScene = new Scene(myRoot, 1100, 700);
		myDisplay = new Display(myScene, myRoot);
		myDisplay.addEventListener(this);

		File file = new File("src/XML_Files/Life.xml");
		XMLParser parser = new XMLParser(file);
		HashMap<Integer[], Integer> cellsMap = parser.getCellsMap();
		int xLen = parser.getXLen();
		int yLen = parser.getYLen();
		myGrid = parser.makeCellsGrid(cellsMap, xLen, yLen);
		statesMap = parser.getStatesMap();
		HashMap<String, Double> paramsMap = parser.getParamsMap();
		
		myDisplay.draw(myGrid, statesMap);
		myStage.setScene(myScene);
		myStage.show();
	}
>>>>>>> e4c51f7c606f8089b33588393b4d999fa7793239

	private void setSimulationFPS(double FPS) {
		myAnimation.stop();
		myAnimation = new Timeline();
		myAnimation.setCycleCount(Timeline.INDEFINITE);
		if (FPS != 0) {
			length = Duration.millis(500);
			KeyFrame frame = new KeyFrame(length, e -> step());
			myAnimation.getKeyFrames().add(frame);
		} //else length should be indefinite, add more to this as I get slider working

	}

	public void playAnimation() {
		myAnimation.play();
		isRunning = true;
	}

	public void pauseAnimation() {
		myAnimation.pause();
		isRunning = false;
	}

<<<<<<< HEAD
    public void stepAnimation() {
        myAnimation.pause();
        isRunning = false;
        step();
       // mySim.step();
    }

    private void step() {
        myDisplay.draw(mySim.step());
    }
=======
	public void stepAnimation() {
		myAnimation.pause();
		isRunning = false;
		step();
	}

	private void step() {
		Random r = new Random();
		Grid newGrid = myGrid;
		for(Cell cell: newGrid.getCellIterator()){
			int randy = r.nextInt(3);
			cell.setState(randy);
		}
		myDisplay.draw(newGrid, statesMap);
	}
>>>>>>> e4c51f7c606f8089b33588393b4d999fa7793239

}