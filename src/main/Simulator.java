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
    private OldAbstractView carParkView;
    private OldAbstractView testView;
    private SimController simController;

    public Simulator(){
        //after refactoring ColdController should be disposed of.
        sim = new OldController();

        //main class for testing.
        main = new Main();


        //code based on the main class, check project MVCDDynamicModelThreadGeneralized for reference. WIP
        model = new Model();
        simController = new SimController(model);
        carParkView = new OldCarParkView(3, 6, 30);
        testView = new OldTestView(3, 6, 30);
        JTabbedPane jtp = new JTabbedPane(); //new tabbed pane

        screen.setSize(1000, 500); // set size of the frame
        screen.getContentPane().add(jtp); //add the tabbed pane

        //Create a panel for each tab
        JPanel jp1 = new JPanel(new BorderLayout());// This will create the first tab
        JPanel jp2 = new JPanel(new BorderLayout());// This will create the second tab

        //Add tabs
        jtp.addTab("OldCarParkView", jp1);
        jtp.addTab("OldTestView", jp2);

        jp1.add(simController, BorderLayout.NORTH); //add simController to jp1 (buttons)
        jp1.add(carParkView, BorderLayout.CENTER); //add carParkView to jp1 (view)
        jp2.add(testView, BorderLayout.CENTER); //add carParkView to jp2 (view)

        screen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        screen.setVisible(true);

    }
}
