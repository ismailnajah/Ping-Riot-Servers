package PingPackage;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.io.*;
import java.net.*;
import java.util.Date;


class ICMP {
    private Label pingLB;


   public void sendPingRequest(String ipAddress) {
        try {
            InetAddress geek = InetAddress.getByName(ipAddress);
            Date now = new Date();
            long msSend = now.getTime();

            if (geek.isReachable(5000)) {
                now = new Date();
                long msReceived = now.getTime();
                long latency= msReceived - msSend;
                Platform.runLater(() -> pingLB.setText(latency + ""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ICMP(Label label){
        pingLB = label;
    }

}
