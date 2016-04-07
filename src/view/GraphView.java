package src.view;
import src.model.CarParkModel;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;


import src.model.GraphModel;

/**
 * Created by jouke on 4/4/16.
 */
public class GraphView extends AbstractView {
    private Dimension size;
    private GraphModel graphModel;
    List<Double> scores = new ArrayList<>();
    GraphModel mainPanel = new GraphModel(scores);

    public GraphView(CarParkModel carParkModel){
        super(carParkModel);
        updateView();
        mainPanel.setPreferredSize(new Dimension(500, 400));
        add(mainPanel); //add it to the panel so it gets displayed
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
        return new Dimension(800, 500);
    }

    public void updateView() {
        int currentAmount = carParkModel.getAmountOfCarsInThePark(); //get the current amount of cars in the park
        //System.out.println(currentAmount);
        scores.add((double) currentAmount); //y value added with x+1

    }
}