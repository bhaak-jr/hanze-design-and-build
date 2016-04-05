package src.model;

import src.view.*;
import java.util.*;

/**
 * Created by Bas Haaksema on 04-Apr-16.
 */
public abstract class AbstractModel {
    private List<AbstractView> views;

    public AbstractModel() {
        views=new ArrayList<>();
    }

    public void addView(AbstractView view) {
        views.add(view);
    }

    public void notifyViews() {
        //for(AbstractView v: views) v.updateView(); //updateView() not yet implemented
    }
}
