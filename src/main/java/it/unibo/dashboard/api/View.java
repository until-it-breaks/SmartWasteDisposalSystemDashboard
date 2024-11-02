package it.unibo.dashboard.api;

public interface View {

    void addLogEntry(String string);

    void updateTemp(String string);

    void updateLevel(String string);

}