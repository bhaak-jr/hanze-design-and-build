package src.controller;

import javax.swing.*;
import src.model.*;
import java.awt.event.*;

/**
 * Controller subclass.
 * Created by Bas Haaksema on 05-Apr-16.
 */
public class CarParkController extends AbstractController implements ActionListener {

    private JButton startButton;
    private JButton stopButton;

    public CarParkController(CarParkModel carParkModel) {
        super(carParkModel);

        setSize(450, 50);
        startButton = new JButton("Start");
        startButton.addActionListener(this);
        stopButton = new JButton("Stop");
        stopButton.addActionListener(this);

        add(startButton);
        add(stopButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            carParkModel.start();
        }
        if (e.getSource() == stopButton) {
            carParkModel.stop();
        }

    }
}
