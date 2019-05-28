package PingPackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;



import java.net.URL;
import java.util.*;


public class Controller implements Initializable {

    Hashtable<String,String> ips = new Hashtable<>();
    ICMP icmp;
    Timer timer;
    @FXML
    public Label pingLB;
    @FXML
    public ComboBox<String> serversCB;
    @FXML
    public Pane pane;
/*
    NA - ping 104.160.131.3
    "EUW","104.160.141.3"
    "EUNE","104.160.142.3"
    "OCE","104.160.156.1"
    "LAN","104.160.136.3"
    "BR","104.160.152.3"
*/

    public Controller()
    {
        ips.put("NA","104.160.131.3");
        ips.put("EUW","104.160.141.3");
        ips.put("EUNE","104.160.142.3");
        ips.put("OCE","104.160.156.1");
        ips.put("LAN","104.160.136.3");
        ips.put("BR","104.160.152.3");
        timer = new Timer();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> servers = FXCollections.observableArrayList("NA","EUW","EUNE","OCE","LAN","BR");
        serversCB.setItems(servers);
        icmp = new ICMP(pingLB);

        pingLB.textProperty().addListener((ov, t, t1) -> {
            int latency = Integer.parseInt(t1);
            if(latency < 150){
                pingLB.setTextFill(Color.GREEN);
            }else if(latency < 280){
                pingLB.setTextFill(Color.ORANGE);
            }else{
                pingLB.setTextFill(Color.RED);
            }
        });

        serversCB.setOnAction(e->{
            int index = serversCB.getSelectionModel().getSelectedIndex();
            String serever = serversCB.getItems().get(index);
            startPinging(ips.get(serever));
        });
        serversCB.getSelectionModel().select(1);
        startPinging(ips.get("EUW"));
        System.out.println("W : "+pane.getWidth()+"   H: "+pane.getHeight());
    }

    private void startPinging(String ip) {
        timer.cancel();
        timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run()
            {
                icmp.sendPingRequest(ip);
            }
        };

        timer.schedule( task, 0L, 30 );
    }
}
