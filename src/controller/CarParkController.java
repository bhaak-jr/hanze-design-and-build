package src.controller;

import javax.swing.*;
import src.model.*;

import java.awt.*;

/**
 * Controller subclass.
 * Created by Bas Haaksema on 05-Apr-16.
 */
public class CarParkController extends AbstractController {

    public CarParkController(CarParkModel carParkModel) {
        super(carParkModel);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> carParkModel.start());
        add(startButton);

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
        add(speedSlider);

        setVisible(true);
    }
}
