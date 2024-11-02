package it.unibo.dashboard.unused;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;

public class SerialMonitor implements SerialPortEventListener {
    SerialPort serialPort;

    public void start(final String portName) {
        serialPort = new SerialPort(portName);
        try {
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);

            serialPort.addEventListener(this, SerialPort.MASK_RXCHAR);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        new UserInputReaderAgent(serialPort).start();
    }

    public synchronized void close() {
        try {
            if (serialPort != null) {
                serialPort.removeEventListener();
                serialPort.closePort();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void serialEvent(final SerialPortEvent serialPortEvent) {
        if (serialPortEvent.isRXCHAR() && serialPortEvent.getEventValue() > 0) {
            try {
                final String receivedData = serialPort.readString(serialPortEvent.getEventValue());
                System.out.println(receivedData);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    class UserInputReaderAgent extends Thread {
        private final SerialPort port;

        public UserInputReaderAgent(final SerialPort port) {
            this.port = port;
        }

        public void run() {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                try {
                    final String string = reader.readLine();
                    port.writeString(string, "UTF-8");
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
