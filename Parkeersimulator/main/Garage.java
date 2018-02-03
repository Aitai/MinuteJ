package main;

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
		
		window.setSize(1200, 700);
		window.setResizable(false);
		window.setLayout(null);
		window.getContentPane().add(garageView);
		window.getContentPane().add(info);
		window.getContentPane().add(garageController);
		window.getContentPane().add(graph);
		
		garageView.setBounds(-50,20,800,300);
		garageController.setBounds(10,320,730,30);
		garageController.setBackground(Color.red);
		info.setBounds(750,10,430,400);
		info.setBackground(Color.black);
		graph.setBounds(10,360,400,300);
		graph.setBackground(Color.lightGray);

//		Container garageAndButtons = window.getContentPane();
//		Container infoPane = window.getContentPane();
//		Container graphPane = window.getContentPane();
//		graphPane.setLayout(new BorderLayout());
//		garageAndButtons.add(garageController, BorderLayout.SOUTH);
//		garageAndButtons.add(garageView, BorderLayout.CENTER);
//		infoPane.add(info, BorderLayout.EAST);
//		graphPane.add(graph,BorderLayout.LINE_START);
//
//		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		simulator.tick();
	}
	public static void main(String[] args) {
		new Garage();
	}
}