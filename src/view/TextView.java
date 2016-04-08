package src.view;

import src.model.*;

import javax.swing.*;
import java.awt.*;

/**
 * View subclass.
 * Created by Bas Haaksema on 05-Apr-16.
 */
public class TextView extends AbstractView {

    private JLabel amountOfCarsInTheParkLabel = new JLabel("Amount of cars in the park: ");
    private JLabel amountOfCarsInTheParkValue = new JLabel();

    private JLabel amountOfRowsInTheParkValue = new JLabel();

    private JLabel amountOfFloorsInTheParkValue = new JLabel();

    private JLabel amountOfPlacesInTheParkValue = new JLabel();



    /**
     * Constructor for objects of class CarPark
     */
    public TextView(CarParkModel carParkModel) {
        super(carParkModel);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); //vertical layout
        setBounds(61, 11, 81, 140);


        JPanel amountOfCarsInThePark = new JPanel(); //subpanel
        amountOfCarsInThePark.add(amountOfCarsInTheParkLabel);
        amountOfCarsInThePark.add(amountOfCarsInTheParkValue);
        add(amountOfCarsInThePark); //this panel
        amountOfCarsInThePark.setMaximumSize(new Dimension(800,0)); //set the maximum size so it does not get stretched on the whole screen


        JPanel amountOfRowsInThepark = new JPanel(); //subpanel
        amountOfRowsInThepark.add(new JLabel("The carpark has: "));
        amountOfRowsInThepark.add(amountOfRowsInTheParkValue);
        add(amountOfRowsInThepark); //this panel
        amountOfRowsInThepark.setMaximumSize(new Dimension(800,0)); //set the maximum size so it does not get stretched on the whole screen


        JPanel amountOfFloorsInThePark = new JPanel(); //subpanel
        amountOfFloorsInThePark.add(new JLabel("The carpark has: ")); //@bug somehow doesn't show this label
        amountOfFloorsInThePark.add(amountOfFloorsInTheParkValue);
        add(amountOfFloorsInThePark); //this panel
        amountOfFloorsInThePark.setMaximumSize(new Dimension(800,0)); //set the maximum size so it does not get stretched on the whole screen

        JPanel amountOfPlacesInThePark = new JPanel();
        amountOfPlacesInThePark.add(new JLabel("The carpark has: ")); //@bug somehow doesn't show this label
        amountOfPlacesInThePark.add(amountOfPlacesInTheParkValue);
        add(amountOfPlacesInThePark);
        amountOfPlacesInThePark.setMaximumSize(new Dimension(800,0)); //set the maximum size so it does not get stretched on the whole screen

        add(Box.createVerticalGlue()); //"fills" the rest of the layout so the spacing won't get weird

        updateView(); //update the view & set the current text values
    }

    /**
     * Actions that are performed when the view is updated
     * Sets the current text values
     */
    public void updateView() {
        amountOfCarsInTheParkValue.setText(String.valueOf(carParkModel.getAmountOfCarsInThePark())); //get the string value of the integer amountOfCarsInThePark
        amountOfRowsInTheParkValue.setText(String.valueOf(carParkModel.getNumberOfRows()) + " rows");
        amountOfFloorsInTheParkValue.setText(String.valueOf(carParkModel.getNumberOfFloors()) + " floors");
        amountOfPlacesInTheParkValue.setText(String.valueOf(carParkModel.getNumberOfPlaces()) + " places");
    }
}
