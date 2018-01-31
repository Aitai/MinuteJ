package main;

import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.*;

import model.Simulator;
import view.GarageView;
import view.InfoView;
import controller.GarageController;

public class Garage {

	private JFrame window;
	private Simulator simulator;
	private GarageView garageView;
	private GarageController garageController;
	private InfoView info;

	public Garage() {

		simulator = new Simulator();
		garageView = new GarageView(simulator);
		garageController = new GarageController(simulator);
		info = new InfoView(simulator);
		window = new JFrame("Parkeergarage simulatie");

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container garageAndButtons = window.getContentPane();
		Container infoPane = window.getContentPane();
		garageAndButtons.add(garageController, BorderLayout.SOUTH);
		garageAndButtons.add(garageView, BorderLayout.CENTER);
		infoPane.add(info, BorderLayout.EAST);

		window.pack();
		simulator.tick();

		window.setVisible(true);
	}
}