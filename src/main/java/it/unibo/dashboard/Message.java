package it.unibo.dashboard;

import java.time.LocalDateTime;

public class Message {
    private String state;
    private String temperature;
    private String level;
    private LocalDateTime timeStamp;

    public Message() {
        timeStamp = LocalDateTime.now();
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(final String temperature) {
        this.temperature = temperature;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(final String level) {
        this.level = level;
    }
}
