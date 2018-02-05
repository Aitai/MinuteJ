package model;
/**
 * 
 * @author MinuteJ
 * @version 1.0.0
 */
import view.AbstractView;

import java.util.ArrayList;
import java.util.List;

public abstract class ViewModel {
    final List<AbstractView> views;

    ViewModel() {
        views = new ArrayList<>();
    }

    public void addView(AbstractView view) {
        views.add(view);
    }
}