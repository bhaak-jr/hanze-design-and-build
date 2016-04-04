package src.view;

import src.model.Car;
import src.model.Location;

import java.awt.*;

/**
 * Created by jouke on 4/4/16.
 */
public class TestView extends AbstractView {
    private Dimension size;
    private Image carParkImage;

    public TestView(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        super(numberOfFloors, numberOfRows, numberOfPlaces);
        setSize(200, 200);
        size = new Dimension(0, 0);
    }


    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 200, 200);
        g.setColor(Color.RED);

        for (int y = 70; y <= 180; y += 5) {
            for (int x = 20; x <= 180; x += 5) {
                g.fillRect(x, y, 3, 3);

            }
        }
    }


}




