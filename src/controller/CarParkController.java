package src.controller;

import javax.swing.*;
import java.awt.*;
import src.model.*;

/**
 * Controller subclass.
 * Created by Bas Haaksema on 10-Apr-16.
 */
public class CarParkController extends AbstractController {

    public CarParkController(CarParkModel carParkModel) {
        super(carParkModel);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); //vertical layout
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

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

        add(Box.createRigidArea(new Dimension(0,5))); //create spacing between buttons and labels

        JButton reserveButton = new JButton("Reserve spot"); // Create Button
        reserveButton.addActionListener(e -> carParkModel.reserve()); // Add Listener
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(reserveButton); // Add to JPanel

        setVisible(true);
    }
}
