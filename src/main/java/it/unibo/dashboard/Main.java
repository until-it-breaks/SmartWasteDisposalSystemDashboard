package it.unibo.dashboard;

import it.unibo.dashboard.api.CommChannel;
import it.unibo.dashboard.api.Dashboard;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

/**
 * Smart Waste Disposal System Dashboard
 * Authors: Jiekai Sun, Weijie Fu
 **/

public class Main {
    /**
     * Upon execution with no arguments the appplication will report all active serial ports.
     * On Windows, the user can identify the correct port by using the Device Manager and searching for the COM port used by Arduino.
     * Knowing the correct port the user can then run the application again using {java -jar ./dashboard-all.jar "portname"} or {./gradlew run --args="portname"} when debugging.
     */
    public static void main(final String[] args) {
        if (args.length == 0) {
            final String[] ports = SerialPortList.getPortNames();
            if (ports.length == 0) {
                System.out.println("No ports found");
            } else {
                System.out.println("Run the app with one of the following argument with the command {java -jar ./dashboard-all.jar portname} or {./gradlew run --args=\"portname\"}");
                for (String port : ports) {
                    System.out.println(port);
                }
            }
        } else {
            startApplication(args[0]);
        }
    }

    private static void startApplication(final String portName) {
        final DashBoardController controller = new DashBoardController();
        final Dashboard dashboard = new DashboardImpl(controller);
        CommChannel commChannel = null;
        try {
            commChannel = new SerialCommChannel(portName, SerialPort.BAUDRATE_9600, controller);
        } catch (final SerialPortException e) {
            e.printStackTrace();
        }
        controller.setView(dashboard);
        controller.setCommChannel(commChannel);
    }
}
