package it.unibo.dashboard;

import java.io.Closeable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SerialCommChannel implements CommChannel, SerialPortEventListener {

    private SerialPort serialPort;
    private BlockingQueue<String> queue;
    private StringBuffer currentMsg = new StringBuffer("");

    public SerialCommChannel(String port, int rate) throws SerialPortException {
        this.queue = new ArrayBlockingQueue<>(100);
        this.serialPort = new SerialPort(port);
        this.serialPort.openPort();
        this.serialPort.setParams(rate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        this.serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
        this.serialPort.addEventListener(this);
    }

    @Override
    public void sendMsg(String msg) {   
        char[] array = (msg+"\n").toCharArray();
        byte[] bytes = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            bytes[i] = (byte) array[i];
        }
        try {
            synchronized (serialPort) {
                serialPort.writeBytes(bytes);
            }
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String receiveMsg() throws InterruptedException {
        return this.queue.take();
    }

    @Override
    public boolean isMsgAvailable() {
        return !this.queue.isEmpty();
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if (serialPortEvent.isRXCHAR()) {
            try {
                String msg = serialPort.readString(serialPortEvent.getEventValue());
                msg = msg.replaceAll("\r", "");
                currentMsg.append(msg);

                boolean goAhead = true;

                while (goAhead) {
                    String msg2 = currentMsg.toString();
                    int index = msg2.indexOf("\n");
                    if (index >= 0) {
                        queue.put(msg2.substring(0, index));
                        currentMsg = new StringBuffer("");
                        if (index + 1 < msg2.length()) {
                            currentMsg.append(msg2.substring(index + 1));
                        }
                    } else {
                        goAhead = false;
                    }
                }
            } catch (SerialPortException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        try {
            if (serialPort != null) {
                serialPort.removeEventListener();
                serialPort.closePort();
            }
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }
}
