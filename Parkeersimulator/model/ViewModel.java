package model;

import view.AbstractView;

import java.util.ArrayList;
import java.util.List;

public abstract class ViewModel {
    public List<AbstractView> views;

    public ViewModel() {
        views = new ArrayList<>();
    }

    public void addView(AbstractView view) {
        views.add(view);
    }
}