package src.view;

import src.model.*;

import javax.swing.*;

/**
 * View subclass.
 * Created by Bas Haaksema on 05-Apr-16.
 */
public class TextView extends AbstractView {

    private JLabel amountOfCarsInTheParkLabel = new JLabel("Amount of cars in the park: ");
    private JLabel amountOfCarsInTheParkValue = new JLabel();
    /**
     * Constructor for objects of class CarPark
     */
    public TextView(CarParkModel carParkModel) {
        super(carParkModel);
        add(amountOfCarsInTheParkLabel);
        add(amountOfCarsInTheParkValue); //add it to the panel so it gets displayed
        updateView();
    }

    /**
     * Actions that are performed when the view is updated
     */
    public void updateView() {
        amountOfCarsInTheParkValue.setText(String.valueOf(carParkModel.getAmountOfCarsInThePark())); //get the string value of the integer amountOfCarsInThePark
    }
}
