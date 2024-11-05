package it.unibo.dashboard;

import java.time.LocalDateTime;

import it.unibo.dashboard.api.State;

public class Message {
    private State state;
    private String temperature;
    private String level;
    private LocalDateTime timeStamp;

    public Message() {
    }

    public State getState() {
        return state;
    }

    public void setState(final State state) {
        this.state = state;
    }

    public String getTemperature() {
        return temperature;
    }

    public LocalDateTime getTimeStamp() {
        return this.timeStamp;
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

    public void setTimeStamp() {
        this.timeStamp = LocalDateTime.now();
    }
}
