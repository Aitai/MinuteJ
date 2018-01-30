package main;

import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.*;

import model.Simulator;
import view.GarageView;
import controller.GarageController;

public class Garage {

	private JFrame window;
	private Simulator simulator;
	private GarageView garageView;
	private GarageController garageController;

	public Garage() {

		simulator = new Simulator();
		garageView = new GarageView(simulator);
		garageController = new GarageController(simulator);
		window = new JFrame("Parkeergarage simulatie");

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = window.getContentPane();
		contentPane.add(garageController, BorderLayout.SOUTH);
		contentPane.add(garageView, BorderLayout.CENTER);
		
		window.pack();
		simulator.tick();
		
		window.setVisible(true);
	}
}