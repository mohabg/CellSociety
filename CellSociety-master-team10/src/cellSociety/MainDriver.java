package cellSociety;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainDriver {
    private Scene myScene;
    private Group myRoot;
    private Button guiPlay, guiPause, guiStep, guiReload;
    private Slider guiSlider;
    private GridPane myGrid;
    private Canvas myCanvas;

    public MainDriver(){

    }

    public void createScene(Stage stage) {
        myRoot = new Group();

        myGrid = new GridPane();
        myGrid.setPadding(new Insets(5));
        myGrid.setHgap(2);
        myGrid.setVgap(2);
        myGrid.setAlignment(Pos.CENTER);
        final Button[][] btn = new Button[10][10];
        for ( int i = 0; i < btn.length; i++) {
            for ( int j = 0; j < btn.length; j++) {
                btn[i][j] = new Button("");
                btn[i][j].setPrefSize(50, 50);
                myGrid.add(btn[i][j], j, i+2);
                btn[i][j].setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent arg0) {
                        System.out.println("hi");
                    }}
                );
            }
        }
        myRoot.getChildren().add(myGrid);

        guiSlider = new Slider(0, 100, 25);
        guiSlider.setShowTickLabels(true);
        guiSlider.setMajorTickUnit(10.0);
        guiSlider.setBlockIncrement(10.0);
        guiSlider.setMinWidth(225);

        guiPlay = new Button("play");
        guiPause = new Button("pause");
        guiStep = new Button("step");
        guiReload = new Button("\u21BA");
        
        guiPlay.setLayoutX(20);
        guiPlay.setLayoutY(550);
        myRoot.getChildren().add(guiPlay);

        guiPause.setLayoutX(70);
        guiPause.setLayoutY(550);
        myRoot.getChildren().add(guiPause);

        guiStep.setLayoutX(130);
        guiStep.setLayoutY(550);
        myRoot.getChildren().add(guiStep);

        guiReload.setLayoutX(180);
        guiReload.setLayoutY(550);
        myRoot.getChildren().add(guiReload);

        guiSlider.setLayoutX(230);
        guiSlider.setLayoutY(550);
        myRoot.getChildren().add(guiSlider);

        myScene = new Scene(myRoot, 1000, 800);

        // Show it
        stage.setScene(myScene);
        stage.show();


    }
}