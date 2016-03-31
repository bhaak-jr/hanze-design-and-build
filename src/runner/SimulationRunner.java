package src.runner;

import src.controller.Simulator;

/**
 * Entry point of our application.
 * It creates a new Simulator object.
 */
public class SimulationRunner {

    public static Simulator sim;

    public static void main(String[] args) {
        sim = new Simulator();
    }

}
