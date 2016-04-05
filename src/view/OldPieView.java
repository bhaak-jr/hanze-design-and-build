package src.view;

import java.awt.*;

/**
 * Created by jouke on 4/4/16.
 */
public class OldPieView extends OldAbstractView {
    private Dimension size;
    private Image carParkImage;

    public OldPieView(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
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
        // Calculates the max amount minus the free location amount. 540 max spots - 540 free spots = 0 cars
        // 540 max spots - 500 free spots = 40 cars, and so on
        int currentAmount = (maxAmount - getFreeLocationAmount());
        //JOptionPane.showMessageDialog(null, currentAmount); // TODO If you uncheck this, you can see it works
        int arc = currentAmount * 360 / maxAmount;

        g.setColor(Color.BLUE);
        g.fillArc(10, 10, 180, 180, 0, arc);
        System.out.println(arc);
    }
}




