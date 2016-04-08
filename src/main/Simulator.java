package src.main;

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

    public Simulator(){

        JFrame screen = new JFrame("Simulator");
        screen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        screen.setSize(1024, 512); // set size of the frame

        CarParkModel carParkModel = new CarParkModel(3, 6, 30);
        SimController controller = new SimController(carParkModel);
        JTabbedPane jtp = new JTabbedPane(); //new tabbed pane

        screen.getContentPane().add(jtp, BorderLayout.CENTER); //add the tabbed pane
        screen.getContentPane().add(controller, BorderLayout.SOUTH); //add buttons

        // Create a panel for each tab
        JPanel jp1 = new JPanel(new BorderLayout());
        JPanel jp2 = new JPanel(new BorderLayout());
        JPanel jp3 = new JPanel(new BorderLayout());
        JPanel jp4 = new JPanel(new BorderLayout());

        // Add tabs
        jtp.addTab("CarParkView", jp1);
        jtp.addTab("TextView", jp2);
        jtp.addTab("PieView", jp3);
        jtp.addTab("GraphView", jp4);

        // Create views
        AbstractView carParkView = new CarParkView(carParkModel);
        AbstractView textView = new TextView(carParkModel);
        AbstractView pieView = new PieView(carParkModel);
        AbstractView graphView = new GraphView(carParkModel);

        // Add views to tabs
        jp1.add(carParkView, BorderLayout.CENTER);
        jp2.add(textView, BorderLayout.CENTER);
        jp3.add(pieView, BorderLayout.CENTER);
        jp4.add(graphView, BorderLayout.CENTER);

        screen.setVisible(true);
        carParkModel.notifyViews();
    }
}
