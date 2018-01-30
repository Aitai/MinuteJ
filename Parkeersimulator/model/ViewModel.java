package model;

import java.util.ArrayList;
import java.util.List;

import view.AbstractView;

public abstract class ViewModel {
	public List<AbstractView> views;
	
	public ViewModel() {
		views = new ArrayList<>();
	}
	
	public void addView(AbstractView view) {
		views.add(view);
	}
}