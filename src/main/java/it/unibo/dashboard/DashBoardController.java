package it.unibo.dashboard;

import java.time.format.DateTimeFormatter;

import it.unibo.dashboard.api.CommChannel;
import it.unibo.dashboard.api.State;
import it.unibo.dashboard.api.Dashboard;

public class DashBoardController {

    private Dashboard dashboard;
    private CommChannel channel;
    private State currentState;

    public void setCommChannel(final CommChannel commChannel) {
        this.channel = commChannel;
        this.currentState = null;
    }

    public void setView(final Dashboard view) {
        this.dashboard = view;
    }

    public void addLogEntry(final String string) {
        this.dashboard.addLogEntry(string);
    }

    public void updateTemp(final String string) {
        this.dashboard.updateTemp(string);
    }

    public void updateLevel(final String string) {
        this.dashboard.updateLevel(string);
    }

    public void sendRestoreSignal() {
        channel.sendMsg("0");
    }

    public void sendProceedSignal() {
        channel.sendMsg("1");
    }

    public void closeChannel() {
        this.channel.close();
    }

    public void alert() {
        try {
            final Message message = MessageParser.parse(channel.receiveMsg());
            if (message != null) {
                this.updateTemp(message.getTemperature() + " celsius");
                this.updateLevel(Double.valueOf(message.getLevel())*100 + "%");
                if (message.getState() != this.currentState) {
                    this.addLogEntry(message.getTimeStamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        + ": "
                        + message.getState().getDescription()
                        + "\n");
                    this.currentState = message.getState();
                }
            }
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }
}
