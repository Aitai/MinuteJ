package main;

import controller.GarageController;
import model.GarageModel;
import model.Simulator;
import view.CarGraph;
import view.GarageView;
import view.InfoView;
import view.LegendaView;
import view.SettingsView;
import view.PieChart;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * De klasse voor het starten en plaatsen van de simulator.
 *
 * @author MinuteJ
 * @version 1.1.2
 */
class Garage {
	GarageModel garageModel;
	/**
	 * Maak een nieuwe simulator aan met daarin alle benodigde items
	 */
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
		JMenuItem hundredItem = new JMenuItem("Spoel 100 stappen vooruit");
		JMenuItem settingsItem = new JMenuItem("Instellingen");
		JMenuItem graphItem = new JMenuItem("Toon/verberg grafiek");
		JMenuItem exitItem = new JMenuItem("Sluiten");
		
		Simulator simulator = new Simulator();
		GarageView garageView = new GarageView(simulator);
		PieChart pieChart = new PieChart(simulator);
		CarGraph graph = new CarGraph(simulator, list);
		GarageController garageController = new GarageController(simulator);
		InfoView info = new InfoView(simulator);
		LegendaView legenda = new LegendaView();
		garageModel = new GarageModel(3, 6, 28);
		JFrame window = new JFrame("Parkeergarage simulatie");

		pauseItem.addActionListener(e -> simulator.startPause());
		hundredItem.addActionListener(e -> simulator.ffHundred());
		settingsItem.addActionListener(e -> new SettingsView(simulator));
		graphItem.addActionListener(e -> {
			graph.setVisible(!graph.isVisible());
			if (graph.isVisible()) {
				window.setSize(1200, 800);
			} else {
				window.setSize(1200, 400);
			}
		});
		exitItem.addActionListener(e -> window.dispose());

		window.setSize(1200, 800);
		window.setResizable(false);
		window.setLayout(null);

		window.add(garageView);
		window.add(info);
		window.add(legenda);
		window.add(garageController);
		window.add(graph);
		window.add(pieChart);
		window.setJMenuBar(menuBar);
		menuBar.add(menu);

		// Voeg de menuitems toe aan de menubalk.
		menu.add(pauseItem);
		menu.add(hundredItem);
		menu.add(settingsItem);
		menu.add(graphItem);
		menu.add(exitItem);

		garageView.setBounds(-50, 20, 800, 300);
		garageController.setBounds(10, 320, 730, 30);
		// garageController.setBackground(Color.red);
		info.setBounds(750, 10, 430, 150);
		legenda.setBounds(750, 150, 430, 300);
		graph.setBounds(10, 360, 400, 290);
		// graph.setBackground(Color.lightGray);
		pieChart.setBounds(360, 360, 400, 400);
		
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		pieChart.plot.setBackgroundPaint(window.getBackground());
		pieChart.chart.setBackgroundPaint(window.getBackground());

//		pieChart.dataset.setValue("Ad hoc auto's", garageModel.getNumberOfAdHocCars());
//		pieChart.dataset.setValue("Vrije plaatsen", garageModel.getNumberOfOpenFreeSpots());
		
		// Teken de garage en de labels zonder een tick uit te voeren.
		simulator.updateViews();
		simulator.setLabels();
	}

	public static void main(String[] args) {
		new Garage();
	}
}