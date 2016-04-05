package src.main;

import src.controller.OldController;

/**
 * Created by Bas Haaksema on 04-Apr-16.
 *
 * This simulator object holds 2 static fields and are instantiated
 * when creating a new Simulator class.
 */
public class Simulator {

    public static OldController sim;
    public static Main main;

    public Simulator(){
        sim = new OldController();

        main = new Main();
        //TEMPORARY split between main and OldController can be merged later on.
        //For testing purposes it has been split
        // Comment a line out to test one or the other
    }
}
