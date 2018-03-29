package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    private Stage stage;
    private FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        Controller control = loader.getController();
        control.setMainApp(this, root, stage);
    }

    public Stage getStage() {
        return stage;
    }

    public static void main(String args[]) {
        launch(args) ;
    }
}
