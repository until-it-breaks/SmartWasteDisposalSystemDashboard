package it.unibo.dashboard;

import java.time.format.DateTimeFormatter;

import it.unibo.dashboard.api.CommChannel;
import it.unibo.dashboard.api.State;
import it.unibo.dashboard.api.Dashboard;

public class DashboardController {

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
        channel.sendMsg("RESTORE");
    }

    public void sendProceedSignal() {
        channel.sendMsg("PROCEED");
    }

    public void closeChannel() {
        this.channel.close();
    }

    public void alert() {
        try {
            final Message message = MessageParser.parse(channel.receiveMsg());
            if (message != null) {
                this.updateTemp(message.getTemperature() + " celsius");
                this.updateLevel(message.getLevel() + "%");
                if (message.getState() != this.currentState) {
                    this.addLogEntry(message.getTimeStamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                        ":" + message.getState() +
                        ": " + message.getState().getDescription() +
                        "\n");
                    this.currentState = message.getState();
                }
            }
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }
}
