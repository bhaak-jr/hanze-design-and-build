package src.view;

import javax.swing.*;
import src.model.*;

/**
 * Created by Bas Haaksema on 05-Apr-16.
 */

public abstract class AbstractView extends JPanel {
    protected Model model;

    public AbstractView(Model model) {
        this.model=model;
        model.addView(this);
    }

    public Model getModel() {
        return model;
    }

    public void updateView() {
        repaint();
    }
}
