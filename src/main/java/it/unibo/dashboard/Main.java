package it.unibo.dashboard;

import it.unibo.dashboard.api.CommChannel;
import it.unibo.dashboard.api.View;
import jssc.SerialPortException;

public class Main {
    public static void main(String[] args) {
        DashBoardController controller = new DashBoardController();
        View dashboard = new Dashboard(controller);
        controller.setView(dashboard);
        CommChannel commChannel = null;
        try {
            commChannel = new SerialCommChannel("COM4", 9600, controller);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        controller.setCommChannel(commChannel);
    }
}