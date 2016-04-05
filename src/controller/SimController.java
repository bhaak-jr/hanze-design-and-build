package src.controller;

import javax.swing.*;
import src.model.*;
import java.awt.event.*;

/**
 * Created by Bas Haaksema on 05-Apr-16.
 */
public class SimController extends AbstractController implements ActionListener {

    private JButton button1;
    private JButton button2;

    public SimController(Model model) {
        super(model);

        setSize(450, 50);
        button1 = new JButton("One step");
        button1.addActionListener(this);
        button2 = new JButton("100 steps");
        button2.addActionListener(this);

        this.setLayout(null);
        add(button1);
        add(button2);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

    }
}
