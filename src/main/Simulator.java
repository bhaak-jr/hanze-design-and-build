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
        CarParkModel carParkModel = new CarParkModel(3, 6, 30);
        CarParkController simController = new CarParkController(carParkModel);
        AbstractView carParkView = new CarParkView(carParkModel);
        AbstractView textView = new TextView(carParkModel);
        AbstractView pieView = new PieView(carParkModel);
        JTabbedPane jtp = new JTabbedPane(); //new tabbed pane

        screen.setSize(1000, 500); // set size of the frame
        screen.getContentPane().add(jtp); //add the tabbed pane

        //Create a panel for each tab
        JPanel jp1 = new JPanel(new BorderLayout());// This will create the first tab
        JPanel jp2 = new JPanel(new BorderLayout());// This will create the second tab
        JPanel jp3 = new JPanel(new BorderLayout());// This will create the second tab


        //Add tabs
        jtp.addTab("CarParkView", jp1);
        jtp.addTab("TextView", jp2);
        jtp.addTab("PieView", jp3);

        jp1.add(simController, BorderLayout.NORTH); //add simController to jp1 (buttons)
        jp1.add(carParkView, BorderLayout.CENTER); //add carParkView to jp1 (view)
        jp2.add(textView, BorderLayout.CENTER); //add carParkView to jp2 (view)
        jp2.add(simController, BorderLayout.NORTH); //add simController to jp2 (buttons)
        jp3.add(pieView, BorderLayout.CENTER); //add pieView to jp3 (view)
        jp3.add(simController, BorderLayout.NORTH); //add simController to jp3 (buttons)

        screen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        screen.setVisible(true);

        carParkModel.updateView();

    }
}
