package it.unibo.dashboard;

import it.unibo.dashboard.api.CommChannel;
import it.unibo.dashboard.api.Dashboard;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

/**
 * Attempts to detect the port on which Arduino is connected, assuming that in the setup a message containing "ArduinoUno" is sent from the MCU.
 * There is also the option to provide the port via args with either {./gradlew run --args="portname"} or {java -jar ./dashboard-all.jar "portname"}.
 */
public class Main {
    private static volatile boolean portFound = false;
    private static final int TIMEOUT_DELTA = 5000;

    public static void main(final String[] args) {
        if (args.length == 0) {
            final String[] ports = SerialPortList.getPortNames();
            if (ports.length == 0) {
                System.out.println("No active ports");
            } else {
                for (final String port : ports) {
                    if (portFound) {
                        break;
                    }
                    System.out.println("Testing port: " + port);
                    new Thread(() -> {
                        try {
                            tryPort(port);
                        } catch (SerialPortException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            }
        } else {
            try {
                tryPort(args[0]);
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
    }

    private static void tryPort(final String portName) throws SerialPortException {
        final StringBuilder receivedData = new StringBuilder("");
        final SerialPort serialPort = new SerialPort(portName);
        serialPort.openPort();
        serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
    
        final long timeout = System.currentTimeMillis() + TIMEOUT_DELTA;
    
        serialPort.addEventListener(new SerialPortEventListener() {
            @Override
            public void serialEvent(final SerialPortEvent event) {
                if (event.isRXCHAR() && !portFound) {
                    try {
                        String received = serialPort.readString(event.getEventValue());
                        received = received.trim();
                        if (received != null) {
                            receivedData.append(received);
                            if (receivedData.toString().equals("ArduinoUno")) {
                                System.out.println("Arduino detected on " + portName);
                                portFound = true;
                                serialPort.closePort();
                                startApplication(portName);
                            }
                        }
                    } catch (final SerialPortException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    
        while (System.currentTimeMillis() <= timeout) {
            if (portFound) {
                if (serialPort.isOpened()) {
                    serialPort.closePort();
                }
                return;
            }
            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("No response from " + portName);
        if (serialPort.isOpened()) {
            serialPort.closePort();
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
