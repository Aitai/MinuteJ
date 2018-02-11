package model;

import view.AbstractView;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstracte klasse voor het aanmaken van nieuwe viewdata
 *
 * @author MinuteJ
 * @version 1.0.0
 */
public abstract class ViewModel {
    final List<AbstractView> views;

    ViewModel() {
        views = new ArrayList<>();
    }

    public void addView(AbstractView view) {
        views.add(view);
    }
}