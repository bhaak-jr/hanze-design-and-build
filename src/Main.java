package src;
import src.controller.Simulator;

/**
 * Entry point of our application.
 * It creates a new Simulator object and calls the run() method
 */
public class Main {

    public static void main(String[] args)
    {
        Simulator sim = new Simulator();
        sim.run();
    }

}
