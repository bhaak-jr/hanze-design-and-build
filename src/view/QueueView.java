package src.view;

import src.model.*;
import javax.swing.*;
import java.awt.*;

/**
 * Created by Jouke & Bas.
 */
public class QueueView extends AbstractView {

    private JLabel entranceCarQueueValue = new JLabel();
    private JLabel paymentCarQueueValue = new JLabel();
    private JLabel exitCarQueueValue = new JLabel();
    private JSlider entranceSlider = new JSlider(SwingConstants.HORIZONTAL);
    private JSlider paymentSlider = new JSlider(SwingConstants.HORIZONTAL);
    private JSlider exitSlider = new JSlider(SwingConstants.HORIZONTAL);


    /**
     * Constructor for objects of class QueueView
     */
    public QueueView(CarParkModel carParkModel) {
        super(carParkModel);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); //vertical layout
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel entranceQueue = new JPanel(new FlowLayout(FlowLayout.LEFT));
        entranceQueue.add(new JLabel("Entrance Queue: "));
        entranceQueue.add(entranceCarQueueValue);
        add(entranceQueue);
        entranceQueue.setMaximumSize(new Dimension(800,0));

        entranceSlider.setValue(carParkModel.getEntranceCarQueueSize());
        entranceSlider.setEnabled(false);
        add(entranceSlider);
        entranceSlider.setMaximumSize(new Dimension(800,0));

        JPanel paymentQueue = new JPanel(new FlowLayout(FlowLayout.LEFT));
        paymentQueue.add(new JLabel("Payment Queue: "));
        paymentQueue.add(paymentCarQueueValue);
        add(paymentQueue);
        paymentQueue.setMaximumSize(new Dimension(800,0));

        paymentSlider.setValue(carParkModel.getEntranceCarQueueSize());
        paymentSlider.setEnabled(false);
        add(paymentSlider);
        paymentSlider.setMaximumSize(new Dimension(800,0));


        JPanel exitQueue = new JPanel(new FlowLayout(FlowLayout.LEFT));
        exitQueue.add(new JLabel("Exit Queue: "));
        exitQueue.add(exitCarQueueValue);
        add(exitQueue);
        exitQueue.setMaximumSize(new Dimension(800,0));

        exitSlider.setValue(carParkModel.getEntranceCarQueueSize());
        exitSlider.setEnabled(false);
        add(exitSlider);
        exitSlider.setMaximumSize(new Dimension(800,0));

        add(Box.createVerticalGlue()); //"fills" the rest of the layout so the spacing won't get weird
        updateView(); //update the view & set the current text values
    }

    /**
     * Update the view
     */
    public void updateView(){
        entranceCarQueueValue.setText(String.valueOf(carParkModel.getEntranceCarQueueSize()));
        paymentCarQueueValue.setText(String.valueOf(carParkModel.getPaymentCarQueueSize()));
        exitCarQueueValue.setText(String.valueOf(carParkModel.getExitCarQueueSize()));
        entranceSlider.setValue(carParkModel.getEntranceCarQueueSize());
        paymentSlider.setValue(carParkModel.getPaymentCarQueueSize());
        exitSlider.setValue(carParkModel.getPaymentCarQueueSize());
    }
}