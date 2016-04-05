package src.controller;

import javax.swing.*;
import src.model.*;
import java.awt.event.*;

/**
 * Created by Bas Haaksema on 05-Apr-16.
 */
public class SimController extends AbstractController implements ActionListener {

    private JButton startButton;
    private JButton stopButton;

    public SimController(Model model) {
        super(model);

        setSize(450, 50);
        startButton = new JButton("Start");
        startButton.addActionListener(this);
        stopButton = new JButton("Stop");
        stopButton.addActionListener(this);

        this.setLayout(null);
        add(startButton);
        add(stopButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==startButton) {
            model.start();
        }
        if (e.getSource()==stopButton) {
            model.stop();
        }

    }
}
