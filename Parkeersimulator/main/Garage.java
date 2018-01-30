package main;

import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.*;

import model.Simulator;
import view.GarageView;

public class Garage {
	
	private JFrame window;
	private Simulator simulator;
	private GarageView garageView;
	
	public Garage() {
		
		simulator = new Simulator();
		garageView = new GarageView(simulator);
		window = new JFrame("Parkeer simulatie");
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = window.getContentPane();
		contentPane.add(garageView, BorderLayout.CENTER);
		window.pack();
		window.setVisible(true);
	}
}