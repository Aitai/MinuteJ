package model;

import java.util.ArrayList;

import view.AbstractView;

public abstract class ViewModel {
	public ArrayList<AbstractView> views;
	
	public ViewModel() {
		views = new ArrayList<>();
	}
	
	public void addView(AbstractView views) {
		views.add(views);
	}
}