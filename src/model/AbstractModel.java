package src.model;

import src.view.*;
import java.util.*;

/**
 * Abstract superclass for Models.
 * Created by Bas Haaksema on 04-Apr-16.
 */
public abstract class AbstractModel {
    private List<AbstractView> views;

    AbstractModel() {
        views=new ArrayList<>();
    }

    public void addView(AbstractView view) {
        views.add(view);
    }

    public void notifyViews() {
        views.stream().forEach(AbstractView::updateView);
    }
}
