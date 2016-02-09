package src.controller;
import src.Model.Cell;
import src.View.Display;
import src.View.Grid;
import src.controller.Simulation.FireSimulation;
import src.controller.Simulation.GameOfLifeSimulation;
import src.controller.Simulation.SegregationSimulation;
import src.controller.Simulation.Simulation;
import src.controller.Simulation.WaTorSimulation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
	HashMap<Integer, Color> statesMap = new HashMap<Integer, Color>();
	Simulation mySim;
	HashMap<String, Simulation> defaultSimTypes = new HashMap<String, Simulation>();

	public MainDriver(Stage stage) throws ParserConfigurationException, SAXException, IOException, NoSuchFieldException, SecurityException, ClassNotFoundException, DOMException, IllegalArgumentException, IllegalAccessException {
		myStage = stage;
		setSimulationFPS(myFPS);
		myRoot = new Group();
		myScene = new Scene(myRoot, 1100, 700);
		myDisplay = new Display(myScene, myRoot, stage);
		myDisplay.addEventListener(this);
		myStage.setScene(myScene);
		setupSims();
		myStage.show();
	}

	public Simulation setSim(String simType, ArrayList<Double> paramsList, ArrayList<Integer> statesList){
		HashMap<String, Simulation> simTypes = new HashMap<String, Simulation>();
		simTypes.put("Fire", new FireSimulation(myGrid));
		simTypes.put("Life", new GameOfLifeSimulation(myGrid));
		simTypes.put("Segregation", new SegregationSimulation(myGrid));
		simTypes.put("Wator", new WaTorSimulation(myGrid));
		mySim = simTypes.get(simType);
		mySim.setParameters(paramsList);
		if(simType.equals("Wator"))
			mySim.initialize(statesList);
		return mySim;
	}
	
	public void setupSims(){
		defaultSimTypes.put("Fire", new FireSimulation());
		defaultSimTypes.put("Life", new GameOfLifeSimulation());
		defaultSimTypes.put("Segregation", new SegregationSimulation());
		defaultSimTypes.put("Wator", new WaTorSimulation());
	}

	private void setSimulationFPS(double FPS) {
		myAnimation.stop();
		myAnimation = new Timeline();
		myAnimation.setCycleCount(Timeline.INDEFINITE);
		if (FPS != 0) {
			length = Duration.millis(1000/FPS);
		}else if (FPS==0){
			length = Duration.INDEFINITE;	
		}
		if(FPS!=0){
			KeyFrame frame = new KeyFrame(length, e -> step());
			myAnimation.getKeyFrames().add(frame);
		}
	}

	public void playAnimation() {
		myAnimation.play();
		isRunning = true;
	}

	public void pauseAnimation() {
		myAnimation.pause();
		isRunning = false;
	}

	public void stepAnimation() {
		myAnimation.pause();
		isRunning = false;
		step();
	}

	public void onSliderMove(int newValue) {
		boolean resume = isRunning;
		setSimulationFPS(newValue);
		if (resume) {
			myAnimation.play();
		}
	}
	
	public void onFileSelection(File myFile) throws NoSuchFieldException, SecurityException, ClassNotFoundException, DOMException, IllegalArgumentException, IllegalAccessException, ParserConfigurationException, SAXException, IOException{
		File file = myDisplay.getFile();
		XMLParser parser = new XMLParser(myFile, defaultSimTypes);
		HashMap<Integer[], Integer> cellsMap = parser.getCellsMap();
		int xLen = parser.getXLen();
		int yLen = parser.getYLen();
		myGrid = parser.makeCellsGrid(cellsMap, xLen, yLen);
		statesMap = parser.getStatesMap();
		ArrayList<Double> paramsList = parser.getParamsList();
		ArrayList<Integer> statesList = parser.getStatesList();
		mySim = setSim(parser.getSimType(), paramsList, statesList);
		myStage.setTitle(mySim.returnTitle());
		myDisplay.setGraphTitle(mySim.returnTitle());
		myDisplay.draw(myGrid, statesMap);
	}

	private void step() {
		if(statesMap.size() > 0)
			myDisplay.draw(mySim.step(), statesMap);
	}

}