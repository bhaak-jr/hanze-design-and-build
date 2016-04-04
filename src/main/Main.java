package src.main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import src.view.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main {
    private JFrame screen = new JFrame("MVC test with tabbed pane");
    private TestView testView;
    private CarParkView carParkView;


    public Main() {
        JTabbedPane jtp = new JTabbedPane(); //new tabbed pane

        screen.setSize(1000, 500); // set size of the frame
        screen.getContentPane().add(jtp); //add the tabbed pane

        //Create a panel for each tab
        JPanel jp1 = new JPanel(new BorderLayout());// This will create the first tab
        JPanel jp2 = new JPanel(new BorderLayout());// This will create the second tab
        JPanel jp3 = new JPanel(new BorderLayout());// This will create the third tab

        //Add tabs
        jtp.addTab("Dummytab", jp1);
        jtp.addTab("CarParkView", jp2);
        jtp.addTab("TestView", jp3);

        //dummy label and add this to tab1
        JLabel label1 = new JLabel();
        label1.setText("This is Tab 1(dummytab)");
        jp1.add(label1, BorderLayout.NORTH);

        //create a new carparkview and add this to tab 2
        carParkView = new CarParkView(3, 6, 30);
        jp2.add(carParkView, BorderLayout.CENTER);

        //create a new testview and add this to tab 3
        testView = new TestView(3, 6, 30);
        JLabel label2 = new JLabel();
        label2.setText("TestView");
        jp3.add(label2, BorderLayout.NORTH);
        jp3.add(testView, BorderLayout.CENTER);

        screen.setVisible(true); // otherwise you won't "see" it

        // Debug updateView()
        carParkView.updateView();
    }
    }
