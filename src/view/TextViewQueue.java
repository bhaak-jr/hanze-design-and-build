package src.view;

import src.model.*;
import javax.swing.*;
import java.awt.*;

/**
 * Created by Jouke on 4/8/16.
 */
public class TextViewQueue extends AbstractView {

    private JLabel entranceCarQueueValue = new JLabel();
    private JLabel paymentCarQueueValue = new JLabel();
    private JLabel exitCarQueueValue = new JLabel();


    /**
     * Constructor for objects of class TextViewQueue
     */
    public TextViewQueue(CarParkModel carParkModel) {
        super(carParkModel);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); //vertical layout
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel entranceQueue = new JPanel(new FlowLayout(FlowLayout.LEFT));
        entranceQueue.add(new JLabel("Entrance Queue: "));
        entranceQueue.add(entranceCarQueueValue);
        add(entranceQueue);
        entranceQueue.setMaximumSize(new Dimension(800,0)); //set the maximum size so it does not get stretched on the whole screen

        JPanel paymentQueue = new JPanel(new FlowLayout(FlowLayout.LEFT));
        paymentQueue.add(new JLabel("Payment Queue: "));
        paymentQueue.add(paymentCarQueueValue);
        add(paymentQueue);
        paymentQueue.setMaximumSize(new Dimension(800,0)); //set the maximum size so it does not get stretched on the whole screen


        JPanel exitQueue = new JPanel(new FlowLayout(FlowLayout.LEFT));
        exitQueue.add(new JLabel("Exit Queue: "));
        exitQueue.add(exitCarQueueValue);
        add(exitQueue);
        exitQueue.setMaximumSize(new Dimension(800,0)); //set the maximum size so it does not get stretched on the whole screen

        add(Box.createVerticalGlue()); //"fills" the rest of the layout so the spacing won't get weird
        updateView(); //update the view & set the current text values
    }

    /**
     * Update the view
     */
    public void updateView(){
        //System.out.println(carParkModel.getEntranceCarQueueSize());
        entranceCarQueueValue.setText(String.valueOf(carParkModel.getEntranceCarQueueSize()));
        paymentCarQueueValue.setText(String.valueOf(carParkModel.getPaymentCarQueueSize()));
        exitCarQueueValue.setText(String.valueOf(carParkModel.getExitCarQueueSize()));
    }



}
