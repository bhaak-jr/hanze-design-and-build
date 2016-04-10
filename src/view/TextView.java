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
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel amountOfCarsInThePark = new JPanel(new FlowLayout(FlowLayout.LEFT)); //subpanelJPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        amountOfCarsInThePark.add(amountOfCarsInTheParkLabel);
        amountOfCarsInThePark.add(amountOfCarsInTheParkValue);
        add(amountOfCarsInThePark); //this panel
        amountOfCarsInThePark.setMaximumSize(new Dimension(800,0)); //set the maximum size so it does not get stretched on the whole screen

        JPanel currentTime = new JPanel(new FlowLayout(FlowLayout.LEFT));
        currentTime.add(new JLabel("Time values: "));
        currentTime.add(currentTimeValue);
        add(currentTime);
        currentTime.setMaximumSize(new Dimension(800,0)); //set the maximum size so it does not get stretched on the whole screen

        JPanel amountOfRowsInThepark = new JPanel(new FlowLayout(FlowLayout.LEFT)); //subpanel
        amountOfRowsInThepark.add(new JLabel("The carpark has: "));
        amountOfRowsInThepark.add(amountOfRowsInTheParkValue);
        add(amountOfRowsInThepark); //this panel
        amountOfRowsInThepark.setMaximumSize(new Dimension(800,0)); //set the maximum size so it does not get stretched on the whole screen

        JPanel amountOfFloorsInThePark = new JPanel(new FlowLayout(FlowLayout.LEFT)); //subpanel
        amountOfFloorsInThePark.add(new JLabel("The carpark has: "));
        amountOfFloorsInThePark.add(amountOfFloorsInTheParkValue);
        add(amountOfFloorsInThePark); //this panel
        amountOfFloorsInThePark.setMaximumSize(new Dimension(800,0)); //set the maximum size so it does not get stretched on the whole screen

        JPanel amountOfPlacesInThePark = new JPanel(new FlowLayout(FlowLayout.LEFT));
        amountOfPlacesInThePark.add(new JLabel("The carpark has: "));
        amountOfPlacesInThePark.add(amountOfPlacesInTheParkValue);
        add(amountOfPlacesInThePark);
        amountOfPlacesInThePark.setMaximumSize(new Dimension(800,0)); //set the maximum size so it does not get stretched on the whole screen

        JPanel enterSpeed = new JPanel(new FlowLayout(FlowLayout.LEFT));
        enterSpeed.add(new JLabel("Enter speed: "));
        enterSpeed.add(enterSpeedValue);
        add(enterSpeed);
        enterSpeed.setMaximumSize(new Dimension(800,0)); //set the maximum size so it does not get stretched on the whole screen

        JPanel exitSpeed = new JPanel(new FlowLayout(FlowLayout.LEFT));
        exitSpeed.add(new JLabel("Exit speed: "));
        exitSpeed.add(exitSpeedValue);
        add(exitSpeed);
        exitSpeed.setMaximumSize(new Dimension(800,0)); //set the maximum size so it does not get stretched on the whole screen

        JPanel paymentSpeed = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
