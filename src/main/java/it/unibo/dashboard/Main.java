package it.unibo.dashboard;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        View dashboard = new Dashboard(controller);
        controller.setView(dashboard);
    }
}