package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Approximator");
        primaryStage.setScene(new Scene(root, 821, 483));
        primaryStage.show();
        primaryStage.getIcons().add(new Image("ic_insert_chart_black_36dp_1x.png"));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
