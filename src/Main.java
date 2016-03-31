package src;
import src.controller.Simulator;

/**
 * Entry point of our application.
 * It creates a new Simulator object method.
 */
public class Main {

    //Creating new public static Simulator.
    //The Simulator needs to be public because the SimulatorView is using it.
    //Please think about better ways of doing this and share it with me ~Bas.
    public static Simulator sim = new Simulator();

    public static void main(String[] args) {

    }

}
