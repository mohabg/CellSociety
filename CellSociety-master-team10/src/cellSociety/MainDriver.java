package cellSociety;
import cell.Cell;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

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

    public MainDriver(Stage stage) {
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
        myDisplay.draw(newGrid);
    }

}