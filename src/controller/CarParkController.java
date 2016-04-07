package src.controller;

import javax.swing.*;
import src.model.*;
import java.awt.event.*;
import javax.swing.event.*;

/**
 * Controller subclass.
 * Created by Bas Haaksema on 05-Apr-16.
 */
public class CarParkController extends AbstractController implements ActionListener, ChangeListener {

    private JButton startButton;
    private JButton stopButton;
    private JButton plusOneButton;
    private JButton plusHundredButton;
    private JButton resetButton;
    private JSlider speedSlider;

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
        //add(plusHundredButton);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        //add(resetButton);

        speedSlider = new JSlider(SwingConstants.HORIZONTAL);
        speedSlider.setMajorTickSpacing(1);
        speedSlider.addChangeListener(this);
        add(speedSlider);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton)       carParkModel.start();
        if (e.getSource() == stopButton)        carParkModel.stop();
        if (e.getSource() == plusOneButton)     carParkModel.run(1);
        if (e.getSource() == plusHundredButton) carParkModel.run(100);
        if (e.getSource() == resetButton){
            carParkModel.notifyViews();
            // TODO Implement reset functionality
            // carParkModel.reset();
        }
    }

    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            carParkModel.setTickPause(100 - source.getValue());
            System.out.print(100 - source.getValue());
        }
    }
}
