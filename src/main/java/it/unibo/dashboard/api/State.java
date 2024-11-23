package it.unibo.dashboard.api;

public enum State {
    
    IDLE("System is idle"),
    ENTERING_WASTE("Awaiting for waste"),
    WASTE_RECEIVED("Waste was received"),
    CONTAINER_FULL("Container is full"),
    EMPTYING_PROCESS("Container is being emptied"),
    DANGEROUS_TEMP("Temperature is dangerously high");

    private final String description;

    private State(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
