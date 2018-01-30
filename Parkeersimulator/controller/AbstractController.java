package controller;

import javax.swing.JPanel;

import model.Simulator;

public abstract class AbstractController extends JPanel{
	protected Simulator simulator;
	
	public AbstractController(Simulator simulator) {
		this.simulator = simulator;
	}
}
