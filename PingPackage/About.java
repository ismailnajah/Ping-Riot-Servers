package PingPackage;

import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class About{
    private Stage stage;
    private boolean opened = false;

    public About(){
        Parent layout=null;
        try {
            layout = FXMLLoader.load(getClass().getResource("AboutScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(layout);
        stage = new Stage();
        stage.setTitle("About");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("Assets/icon.png")));
        stage.setOnCloseRequest(event -> opened=false);

        Hyperlink githubLink = (Hyperlink) scene.lookup("#githubLink");
        githubLink.setOnMouseClicked(event -> {
            try {
                Desktop.getDesktop().browse(new URL(githubLink.getText()).toURI());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });
    }

    public void show(){
        if(!opened){
            stage.show();
            opened=true;
        }
    }
}
