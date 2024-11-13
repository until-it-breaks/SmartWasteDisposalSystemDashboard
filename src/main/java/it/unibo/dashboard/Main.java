package it.unibo.dashboard;

import it.unibo.dashboard.api.CommChannel;
import it.unibo.dashboard.api.Dashboard;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

/**
 * Attempts to detect the port on which Arduino is connected, assuming that in the setup a message containing "ArduinoUno" is sent from the MCU.
 * There is also the option to provide the port via args with either {./gradlew run --args="portname"} or {java -jar ./dashboard-all.jar "portname"}.
 */
public class Main {
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
