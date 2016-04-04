package src.main;

import src.controller.SimController;

/**
 * Created by Bas Haaksema on 04-Apr-16.
 */
public class Simulator {

    public static SimController sim;
    public static Main main;

    public Simulator(){
        sim = new SimController();

        main = new Main();
        //TEMPORARY split between main and SimController can be merged later on.
        //For testing purposes it has been split
        // Comment a line out to test one or the other
    }
}
