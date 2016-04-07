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
    private List<Double> scores;


   /* public GraphView(CarParkModel carParkModel) {
        super(carParkModel);
        List<Double> scores = new ArrayList<Double>();
        scores.add((double) 5);
        scores.add((double) 10);
        GraphModel graph = new GraphModel(scores);
        graph.setPreferredSize(new Dimension(800, 600));
        updateView();
    }*/
    public GraphView(CarParkModel carParkModel){
        super(carParkModel);
        List<Double> scores = new ArrayList<>();
        scores.add((double) 5);
        scores.add((double) 10);
        GraphModel mainPanel = new GraphModel(scores);
        mainPanel.setPreferredSize(new Dimension(500, 400));
        add(mainPanel); //add it to the panel so it gets displayed
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
        return new Dimension(800, 500);
    }

}