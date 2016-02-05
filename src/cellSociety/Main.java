package cellSociety;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cell Society");
        MainDriver driver = new MainDriver(primaryStage);
    }

}
