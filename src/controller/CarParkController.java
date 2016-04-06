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
    private JButton plusOneButton;
    private JButton plusHundredButton;

    public CarParkController(CarParkModel carParkModel) {
        super(carParkModel);

        setSize(450, 50);

        startButton = new JButton("Start");
        startButton.addActionListener(this);
        add(startButton);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(this);
        add(stopButton);

        plusOneButton = new JButton("+1 tick");
        plusOneButton.addActionListener(this);
        add(plusOneButton);

        plusHundredButton = new JButton("+100 ticks");
        plusHundredButton.addActionListener(this);
        add(plusHundredButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            carParkModel.start();
        }
        if (e.getSource() == stopButton) {
            carParkModel.stop();
        }
        if (e.getSource() == plusOneButton) {
            carParkModel.run(1);
        }
        if (e.getSource() == plusHundredButton) {
            carParkModel.run(100);
        }

    }
}
