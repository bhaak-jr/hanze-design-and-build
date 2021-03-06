package src.view;

import javax.swing.*;
import src.model.*;

/**
 * Abstract superclass for Views.
 * Created by Bas Haaksema on 05-Apr-16.
 */

public abstract class AbstractView extends JPanel {
    CarParkModel carParkModel;

    AbstractView(CarParkModel carParkModel) {
        this.carParkModel = carParkModel;
        carParkModel.addView(this);
    }

    CarParkModel getCarParkModel() {
        return carParkModel;
    }

    public void updateView() {
        repaint();
    }
}
