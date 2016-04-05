package src.main;

import javax.swing.*;
import java.awt.*;
import src.view.*;

import static src.main.Simulator.sim;

public class Main {
    private JFrame screen = new JFrame("MVC test with tabbed pane");
    private TestView testView;
    private CarParkView carParkView;
    private PieView pieView;
    private JButton button1 = new JButton("One step");
    private JButton button2 = new JButton("100 steps");




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
        pieView = new PieView(3, 6, 30);
        JLabel label1 = new JLabel();
        label1.setText("PieView");
        jp1.add(pieView, BorderLayout.CENTER);

        //create a new carparkview and add this to tab 2
        carParkView = new CarParkView(3, 6, 30);
        JPanel jp2Wrapper = new JPanel(); //wrapper to add the 2 buttons
        jp2Wrapper.setLayout(new BoxLayout(jp2Wrapper, BoxLayout.PAGE_AXIS));
        jp2Wrapper.add(button1);
        jp2Wrapper.add(Box.createRigidArea(new Dimension(0,5)));
        jp2Wrapper.add(button2); //@bug buttons not resizing correctly when resizing the window
        jp2Wrapper.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); //add a border
        jp2.add(jp2Wrapper, BorderLayout.WEST);
        jp2.add(carParkView, BorderLayout.CENTER);

        //Add actions to buttons
        button1.addActionListener(e -> {
            // ^ We used a lambda as a shortcut to create an anonymous inner class
            sim.run(1); // We imported the static Simulator (OldController object) to call this object's run method 1 time.
        });
        button2.addActionListener(e -> {
            // ^ We used a lambda as a shortcut to create an anonymous inner class
            sim.run(100); // We imported the static Simulator (OldController object) to call this object's run method 100 times.
        });
        //@todo currently does not work because simview.tick is called in controller and not abstractview.tick
        //@todo this has to be fixed by adding the views to a collection and looping over it to update the views.
        //@todo the abstractview has to make sure tick and updateview is in the subclasses


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
