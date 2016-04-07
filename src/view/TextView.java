package src.view;

import src.model.*;

import javax.swing.*;

/**
 * View subclass.
 * Created by Bas Haaksema on 05-Apr-16.
 */
public class TextView extends AbstractView {

    /**
     * Constructor for objects of class CarPark
     */
    public TextView(CarParkModel carParkModel) {
        super(carParkModel);
    }

    // TODO create basic functionality
    public void updateView() {
        System.out.println(carParkModel.getAmountOfCarsInThePark());
    }
}
