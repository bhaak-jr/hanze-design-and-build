package src.main;

import src.controller.OldController;

import javax.swing.*;
import java.awt.*;

import src.model.*;
import src.view.*;
import src.controller.*;


/**
 * Created by Bas Haaksema on 04-Apr-16.
 *
 * This simulator object holds 2 static fields and are instantiated
 * when creating a new Simulator class.
 */
public class Simulator {

    public static OldController sim;
    public static Main main;

    private Model model;
    private JFrame screen = new JFrame("Simulator");
    private AbstractView carParkView;
    private AbstractView testView;
    private SimController simController;

    public Simulator(){
        sim = new OldController();

        main = new Main();
        //TEMPORARY split between main and OldController can be merged later on.
        //For testing purposes it has been split
        // Comment a line out to test one or the other

        model = new Model();
        simController = new SimController(model);
        carParkView = new CarParkView(3, 6, 30); //needs model
        testView = new TestView(3, 6, 30); //needs model
        JTabbedPane jtp = new JTabbedPane(); //new tabbed pane

        screen.setSize(1000, 500); // set size of the frame
        screen.getContentPane().add(jtp); //add the tabbed pane

        //Create a panel for each tab
        JPanel jp1 = new JPanel(new BorderLayout());// This will create the first tab
        JPanel jp2 = new JPanel(new BorderLayout());// This will create the second tab

        //Add tabs
        jtp.addTab("CarParkView", jp1);
        jtp.addTab("TestView", jp2);

        jp1.add(simController, BorderLayout.NORTH);
        jp1.add(carParkView, BorderLayout.CENTER);
        jp2.add(testView, BorderLayout.CENTER);

        screen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        screen.setVisible(true);

    }
}
