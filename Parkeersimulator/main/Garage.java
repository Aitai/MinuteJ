package main;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;
import java.awt.*;

import model.Simulator;
import view.CarGraph;
import view.GarageView;
import view.InfoView;
import controller.GarageController;

public class Garage {

	private JFrame window;
	private Simulator simulator;
	private GarageView garageView;
	private GarageController garageController;
	private InfoView info;
	private CarGraph graph;

	public Garage() {
		// generate random numbers for testing purposes
		List<Integer> list = new ArrayList<Integer>();
	      Random random = new Random();
	      int maxDataPoints = 24;
	      int maxScore = 20;
	      for (int i = 0; i < maxDataPoints ; i++) {
	         list.add(random.nextInt(maxScore));
	      }

		simulator = new Simulator();
		garageView = new GarageView(simulator);
		graph = new CarGraph(simulator, list);
		garageController = new GarageController(simulator);
		info = new InfoView(simulator);
		window = new JFrame("Parkeergarage simulatie");

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container garageAndButtons = window.getContentPane();
		Container infoPane = window.getContentPane();
		Container graphPane = window.getContentPane();
		graphPane.setLayout(new BorderLayout());
		garageAndButtons.add(garageController, BorderLayout.SOUTH);
		garageAndButtons.add(garageView, BorderLayout.CENTER);
		infoPane.add(info, BorderLayout.EAST);
		graphPane.add(graph,BorderLayout.LINE_START);

		window.pack();
		window.setVisible(true);
		simulator.tick();
	}
	public static void main(String[] args) {
		new Garage();
	}
}