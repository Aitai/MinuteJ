package main;
/**
 * 
 * @author MinuteJ
 * @version 2
 */
import controller.GarageController;
import model.Simulator;
import view.CarGraph;
import view.GarageView;
import view.InfoView;
import view.SettingsView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Garage {

	private Garage() {
		// generate random numbers for testing purposes
		List<Integer> list = new ArrayList<>();
		Random random = new Random();
		int maxDataPoints = 24;
		int maxScore = 20;
		for (int i = 0; i < maxDataPoints; i++) {
			list.add(random.nextInt(maxScore));
		}

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Opties");

		JMenuItem pauseItem = new JMenuItem("Start/pause");
		JMenuItem settingsItem = new JMenuItem("Instellingen");
		JMenuItem graphItem = new JMenuItem("Toon/verberg grafiek");
		JMenuItem exitItem = new JMenuItem("Sluiten");

		Simulator simulator = new Simulator();
		GarageView garageView = new GarageView(simulator);
		CarGraph graph = new CarGraph(simulator, list);
		GarageController garageController = new GarageController(simulator);
		InfoView info = new InfoView(simulator);
		JFrame window = new JFrame("Parkeergarage simulatie");

		pauseItem.addActionListener(e -> simulator.startPause());
		settingsItem.addActionListener(e -> new SettingsView(simulator));
		graphItem.addActionListener(e -> {
			graph.setVisible(!graph.isVisible());
			if (graph.isVisible()) {
				window.setSize(1200, 700);
			} else {
				window.setSize(1200, 400);
			}
		});
		exitItem.addActionListener(e -> window.dispose());

		window.setSize(1200, 700);
		window.setResizable(false);
		window.setLayout(null);

		window.add(garageView);
		window.add(info);
		window.add(garageController);
		window.add(graph);
		window.setJMenuBar(menuBar);
		menuBar.add(menu);

		// Voeg de menuitems toe aan de menubalk.
		menu.add(pauseItem);
		menu.add(settingsItem);
		menu.add(graphItem);
		menu.add(exitItem);

		garageView.setBounds(-50, 20, 800, 300);
		garageController.setBounds(10, 320, 730, 30);
		// garageController.setBackground(Color.red);
		info.setBounds(750, 10, 430, 340);
		graph.setBounds(10, 360, 400, 290);
		// graph.setBackground(Color.lightGray);

		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setVisible(true);
		simulator.tick();
	}

	public static void main(String[] args) {
		new Garage();
	}
}