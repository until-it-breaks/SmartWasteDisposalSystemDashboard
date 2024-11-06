package it.unibo.dashboard.api;

/**
 * A dashboard that has a history log, temperature and liquid level sensor.
 */
public interface Dashboard {

    void addLogEntry(String string);

    void updateTemp(String string);

    void updateLevel(String string);

}