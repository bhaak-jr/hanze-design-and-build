package src.view;

import javax.swing.*;
import src.model.*;

/**
 * Abstract superclass for Views.
 * Created by Bas Haaksema on 05-Apr-16.
 */

public abstract class AbstractView extends JPanel {
    protected SimModel simModel;

    AbstractView(SimModel simModel) {
        this.simModel = simModel;
        simModel.addView(this);
    }

    public SimModel getSimModel() {
        return simModel;
    }

    public void updateView() {
        repaint();
    }
}
