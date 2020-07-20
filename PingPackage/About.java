package PingPackage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class About {
    private Stage stage;
    private boolean opened = false;
    public About(){
        try {
            Parent layout = FXMLLoader.load(getClass().getResource("./AboutScene.fxml"));
            Scene scene = new Scene(layout);
            stage = new Stage();
            stage.setTitle("About");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("Assets/icon.png")));
            stage.setOnCloseRequest(event -> opened=false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show(){
        if(!opened){
            stage.show();
            opened=true;
        }
    }

}
