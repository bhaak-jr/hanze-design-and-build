package src.controller;

import src.model.*;
import javax.swing.*;

/**
 * Abstract superclass for controllers.
 * Created by Bas Haaksema on 04-Apr-16.
 */
abstract class AbstractController extends JPanel {
    protected CarParkModel carParkModel;

    AbstractController(CarParkModel carParkModel) {
        this.carParkModel = carParkModel;
    }
}