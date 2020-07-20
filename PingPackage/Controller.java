package PingPackage;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;


import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.*;


public class Controller implements Initializable {
    Hashtable<String,String> ips = new Hashtable<>();
    Timer timer;

    private About about;

    @FXML
    public Label pingLB;

    @FXML
    public Hyperlink aboutButton;

    @FXML
    public ComboBox<String> serversCB;

    @FXML
    public CategoryAxis x;
    @FXML
    public NumberAxis y;
    @FXML
    public LineChart<?,?> pingChart;

    private XYChart.Series series = new XYChart.Series();


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

        pingLB.textProperty().addListener((ov, t, t1) -> {
            int latency = Integer.parseInt(t1.split(" ")[0]);
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
            String server = serversCB.getItems().get(index);
            startPinging(ips.get(server));
        });
        serversCB.getSelectionModel().select(1);
        startPinging(ips.get("EUW"));

        pingChart.getData().add(series);


        //setting up about window
        about = new About();

        aboutButton.setOnMouseClicked(event -> about.show());

    }

    int i=0;
    private void startPinging(String ip) {
        timer.cancel();
        timer = new Timer();
        Rectangle invisibleNode = new Rectangle(0, 0);
        invisibleNode.setVisible(false);

        TimerTask task = new TimerTask() {
            public void run()
            {
                i++;
                long latency = sendPingRequest(ip);
                Platform.runLater(() ->{
                    pingLB.setText(latency+" ms");
                    XYChart.Data data = new XYChart.Data(i+"",latency);
                    data.setNode(invisibleNode);
                    series.getData().add(data);

                    if(series.getData().size() > 200)
                        series.getData().remove(0);
                });
            }
        };

        timer.schedule( task, 0L, 30 );
    }


    public long sendPingRequest(String ipAddress) {
        long latency=0l;
        try {
            InetAddress geek = InetAddress.getByName(ipAddress);
            Date now = new Date();
            long msSend = now.getTime();

            if (geek.isReachable(5000)) {
                now = new Date();
                long msReceived = now.getTime();
                latency= msReceived - msSend;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return latency;
    }

}
