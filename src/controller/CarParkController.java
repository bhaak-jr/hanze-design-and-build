package src.controller;

import javax.swing.*;
import java.util.Hashtable;
import src.model.*;

import java.awt.*;

/**
 * Controller subclass.
 * Created by Bas Haaksema on 05-Apr-16.
 */
public class CarParkController extends AbstractController {

    public CarParkController(CarParkModel carParkModel) {
        super(carParkModel);

        JLabel textRegular = new JLabel("NORMAL");
        JLabel textParking = new JLabel("PASSHOLDER");
        JLabel textBadParking = new JLabel("BAD PARKER");
        JLabel textReservation = new JLabel("RESERVATION");
        textRegular.setForeground(Color.red);
        textParking.setForeground(Color.blue);
        textBadParking.setForeground(Color.green);
        textReservation.setForeground(Color.orange);
        add(textRegular);
        add(textParking);
        add(textBadParking);
        add(textReservation);

        JButton reserveButton = new JButton("Reserve spot"); // Create Button
        reserveButton.addActionListener(e -> carParkModel.reserve()); // Add Listener
        add(reserveButton); // Add to JPanel

        JButton startButton = new JButton("Start"); // Create Button
        startButton.addActionListener(e -> carParkModel.start()); // Add Listener
        add(startButton); // Add to JPanel

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> carParkModel.stop());
        add(stopButton);

        JButton plusOneButton = new JButton("+1 tick");
        plusOneButton.addActionListener(e -> carParkModel.run(1));
        add(plusOneButton);

        JButton plusHundredButton = new JButton("+100 ticks");
        plusHundredButton.addActionListener(e -> carParkModel.run(100));
        // TODO Secure threading
        //add(plusHundredButton);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            // TODO Implement reset functionality
            // carParkModel.reset();
        });
        //add(resetButton);

        JSlider speedSlider = new JSlider(SwingConstants.HORIZONTAL);
        speedSlider.setMajorTickSpacing(5);
        speedSlider.addChangeListener(e -> {
            JSlider source = (JSlider)e.getSource();
            if (!source.getValueIsAdjusting()) {
                carParkModel.setTickPause(source.getMaximum() - source.getValue());
            }
        });
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(speedSlider.getMaximum()/10, new JLabel("Slow"));
        labelTable.put(speedSlider.getMaximum()/10*9, new JLabel("Fast"));
        speedSlider.setLabelTable(labelTable);
        speedSlider.setPaintLabels(true);
        add(speedSlider);

        setVisible(true);
    }
}
