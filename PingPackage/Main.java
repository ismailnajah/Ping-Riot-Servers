package PingPackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static Main application;
    @Override
    public void start(Stage primaryStage){
        application = this;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("Assets/icon.png")));
        primaryStage.setTitle("Ping League of Legends Servers");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
