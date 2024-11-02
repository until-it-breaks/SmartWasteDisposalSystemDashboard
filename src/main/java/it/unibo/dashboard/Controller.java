package it.unibo.dashboard;

import java.util.Random;

import it.unibo.dashboard.api.CommChannel;
import it.unibo.dashboard.api.View;

public class Controller {

    private View dashboard;
    private CommChannel channel;

    public void setCommChannel(CommChannel commChannel) {
        this.channel = commChannel;
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
        Random random = new Random();
        this.updateTemp(Integer.toString(random.nextInt(100)) + " celsius");
        this.updateLevel(Integer.toString(random.nextInt(100)) + "%");
        try {
            this.addLogEntry(channel.receiveMsg() + "\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
