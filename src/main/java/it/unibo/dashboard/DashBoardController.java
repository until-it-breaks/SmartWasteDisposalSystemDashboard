package it.unibo.dashboard;

import java.time.format.DateTimeFormatter;

import it.unibo.dashboard.api.CommChannel;
import it.unibo.dashboard.api.State;
import it.unibo.dashboard.api.View;

public class DashBoardController {

    private View dashboard;
    private CommChannel channel;
    private State currentState;

    public void setCommChannel(CommChannel commChannel) {
        this.channel = commChannel;
        this.currentState = null;
    }

    public void setView(View view) {
        this.dashboard = view;
    }

    public void addLogEntry(String string) {
        this.dashboard.addLogEntry(string);
    }

    public void updateTemp(String string) {
        this.dashboard.updateTemp(string);
    }

    public void updateLevel(String string) {
        this.dashboard.updateLevel(string);
    }

    public void sendRestoreSignal() {
        channel.sendMsg("RESTORE");
    }

    public void sendProceedSignal() {
        channel.sendMsg("PROCEED");
    }

    public void alert() {
        try {
            Message message = MessageParser.parse(channel.receiveMsg());
            this.updateTemp(message.getTemperature() + " celsius");
            this.updateLevel(message.getLevel() + "%");
            if (message.getState() != this.currentState) {
                this.addLogEntry(message.getTimeStamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ":" + message.getState() +  ": " + message.getState().getDescription() + "\n");
                this.currentState = message.getState();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
