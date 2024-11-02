package it.unibo.dashboard;

public enum States {
    
    IDLE("System is idle"),
    SLEEP("System is sleeping"),
    ENTERING_WASTE("Awaiting for waste"),
    WASTE_RECEIVED("Waste was received"),
    CONTAINER_FULL("Container is full"),
    EMPTYING_PROCESS("Container is being emptied"),
    DANGEROUS_TEMP("Temperature is dangerously high");

    private String description;

    private States(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this,description;
    }
}
