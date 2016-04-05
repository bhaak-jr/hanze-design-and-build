package src.view;

import src.model.Car;
import src.model.Location;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jouke on 4/4/16.
 */
public class PieView extends AbstractView {
    private Dimension size;
    private Image carParkImage;

    public PieView(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        super(numberOfFloors, numberOfRows, numberOfPlaces);
        size = new Dimension(0, 0);
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
        return new Dimension(800, 500);
    }

    public void paintComponent(Graphics g) {
        int maxAmount=(getNumberOfPlaces()*getNumberOfFloors()*getNumberOfRows()); //maximum aantal
        int currentAmount = 100; //TODO This has to be the actual value of the current ammount of cars currently not working
        int arc = currentAmount * 360 / maxAmount;

        g.setColor(Color.BLUE);
        g.fillArc(10, 10, 180, 180, 0, arc);
        System.out.println(arc);
    }
}




