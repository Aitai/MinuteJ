package view;

import controller.GarageController;
import model.GarageModel;
import model.Simulator;

import javax.swing.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainView{

    public PieChart pieChart;
    public BarChart barChart;
    GarageModel garageModel;

    public MainView(Simulator simulator)

    {
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

        GarageView garageView = new GarageView(simulator);
        pieChart = new PieChart();
        barChart = new BarChart("Test");
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
                window.setSize(1200, 420);
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
        window.add(barChart);
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
        garageController.setBounds(10, 320, 730, 40);
        // garageController.setBackground(Color.red);
        info.setBounds(750, 10, 430, 240);
        legenda.setBounds(660, 180, 430, 300);
        barChart.setBounds(10, 300, 400, 290);
        // graph.setBackground(Color.lightGray);
        pieChart.setBounds(360, 360, 400, 400);

        pieChart.createPiePiece("Ad hoc auto's", simulator.getNumberOfAdHocCars());
        pieChart.createPiePiece("Abonnement auto's", simulator.getNumberOfParkingPassCars());
		pieChart.createPiePiece("Gereserveerde auto's", simulator.getNumberOfResCars());
		pieChart.createPiePiece("Vrije ad hoc plaatsen", garageModel.getNumberOfOpenFreeSpots());
		pieChart.createPiePiece("Vrije abonnee plaatsen", garageModel.getNumberOfOpenPassSpots());

        pieChart.plot.setBackgroundPaint(window.getBackground());
        pieChart.chart.setBackgroundPaint(window.getBackground());
        
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);

        // Teken de garage en de labels zonder een tick uit te voeren.
        simulator.updateViews();
        simulator.setLabels();


    }

}
