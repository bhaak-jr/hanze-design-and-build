package src.view;
import src.model.*;
import javax.swing.*;
import java.awt.*;

/**
 * Created by jouke on 4/8/16.
 */
public class TextViewQueue extends AbstractView {

    private JLabel entranceCarQueue = new JLabel();
    private JLabel paymentCarQueue = new JLabel();
    private JLabel exitCarQueue = new JLabel();


    /**
     * Constructor for objects of class TextViewQueue
     */
    public TextViewQueue(CarParkModel carParkModel) {
        super(carParkModel);
        add(entranceCarQueue);
        add(paymentCarQueue);
        add(exitCarQueue);
    }

    /**
     * Update the view
     */
    public void updateView(){
        System.out.println(carParkModel.getEntranceCarQueueSize());
        entranceCarQueue.setText(String.valueOf(carParkModel.getEntranceCarQueueSize()));
        paymentCarQueue.setText(String.valueOf(carParkModel.getPaymentCarQueueSize()));
        exitCarQueue.setText(String.valueOf(carParkModel.getExitCarQueueSize()));
    }



}
