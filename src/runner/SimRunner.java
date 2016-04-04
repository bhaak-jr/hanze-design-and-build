package src.runner;

import src.controller.SimController;

/**
 * Entry point of our application.
 * It creates a new SimController object.
 */
public class SimRunner {

    public static SimController sim;

    public static void main(String[] args) {
        sim = new SimController();
    }

}
