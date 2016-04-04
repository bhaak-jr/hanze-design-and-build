package src.main;

import javax.swing.*;
import java.awt.event.*;
import src.view.*;

/**
 * Created by jouke on 4/4/16.
 */
public class Main {
        private JFrame screen;
        private AbstractView carParkView;
        private TestView testView;

        public Main() {
            carParkView = new CarParkView(3, 6, 30);
            testView = new TestView(3, 6, 30);
            screen=new JFrame("Model View Controller Test");
            screen.setSize(1000, 1000) ;
            screen.getContentPane().add(carParkView);
            screen.getContentPane().add(testView);
            screen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            screen.setVisible(true);
        }
    }


