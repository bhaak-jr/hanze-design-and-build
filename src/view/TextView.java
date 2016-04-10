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
    private JLabel currentTimeValue = new JLabel();
    private JLabel exitSpeedValue = new JLabel();
    private JLabel paymentSpeedValue = new JLabel();
    private JLabel enterSpeedValue = new JLabel();



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

        JPanel currentTime = new JPanel();
        currentTime.add(new JLabel("Time values: "));
        currentTime.add(currentTimeValue);
        add(currentTime);
        currentTime.setMaximumSize(new Dimension(800,0)); //set the maximum size so it does not get stretched on the whole screen

        JPanel amountOfRowsInThepark = new JPanel(); //subpanel
        amountOfRowsInThepark.add(new JLabel("The carpark has: "));
        amountOfRowsInThepark.add(amountOfRowsInTheParkValue);
        add(amountOfRowsInThepark); //this panel
        amountOfRowsInThepark.setMaximumSize(new Dimension(800,0)); //set the maximum size so it does not get stretched on the whole screen


        JPanel amountOfFloorsInThePark = new JPanel(); //subpanel
        amountOfFloorsInThePark.add(new JLabel("The carpark has: "));
        amountOfFloorsInThePark.add(amountOfFloorsInTheParkValue);
        add(amountOfFloorsInThePark); //this panel
        amountOfFloorsInThePark.setMaximumSize(new Dimension(800,0)); //set the maximum size so it does not get stretched on the whole screen

        JPanel amountOfPlacesInThePark = new JPanel();
        amountOfPlacesInThePark.add(new JLabel("The carpark has: "));
        amountOfPlacesInThePark.add(amountOfPlacesInTheParkValue);
        add(amountOfPlacesInThePark);
        amountOfPlacesInThePark.setMaximumSize(new Dimension(800,0)); //set the maximum size so it does not get stretched on the whole screen

        JPanel enterSpeed = new JPanel();
        enterSpeed.add(new JLabel("Enter speed: "));
        enterSpeed.add(enterSpeedValue);
        add(enterSpeed);
        enterSpeed.setMaximumSize(new Dimension(800,0)); //set the maximum size so it does not get stretched on the whole screen

        JPanel exitSpeed = new JPanel();
        exitSpeed.add(new JLabel("Exit speed: "));
        exitSpeed.add(exitSpeedValue);
        add(exitSpeed);
        exitSpeed.setMaximumSize(new Dimension(800,0)); //set the maximum size so it does not get stretched on the whole screen

        JPanel paymentSpeed = new JPanel();
        paymentSpeed.add(new JLabel("Payment speed: "));
        paymentSpeed.add(paymentSpeedValue);
        add(paymentSpeed);
        paymentSpeed.setMaximumSize(new Dimension(800,0)); //set the maximum size so it does not get stretched on the whole screen




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
        currentTimeValue.setText(String.valueOf(carParkModel.getCurrentTime()));
        exitSpeedValue.setText(String.valueOf(carParkModel.getExitSpeed()));
        enterSpeedValue.setText(String.valueOf(carParkModel.getEnterSpeed()));
        paymentSpeedValue.setText(String.valueOf(carParkModel.getPaymentSpeed()));
    }
}
