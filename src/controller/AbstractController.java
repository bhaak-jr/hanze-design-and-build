package src.controller;

import src.model.*;
import javax.swing.*;

/**
 * Abstract superclass for controllers.
 * Created by Bas Haaksema on 04-Apr-16.
 */
abstract class AbstractController extends JPanel {
    protected SimModel simModel;

    AbstractController(SimModel simModel) {
        this.simModel = simModel;
    }
}