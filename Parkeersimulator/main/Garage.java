package main;

import controller.GarageController;
import model.Simulator;
import view.CarGraph;
import view.GarageView;
import view.InfoView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Garage {

    private Garage() {
        // generate random numbers for testing purposes
        List<Integer> list = new ArrayList<Integer>();
        Random random = new Random();
        int maxDataPoints = 24;
        int maxScore = 20;
        for (int i = 0; i < maxDataPoints; i++) {
            list.add(random.nextInt(maxScore));
        }

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opties");
        JMenuItem settingsItem = new JMenuItem("Instellingen");

        settingsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("setting test");
            }
        });

        Simulator simulator = new Simulator();
        GarageView garageView = new GarageView(simulator);
        CarGraph graph = new CarGraph(simulator, list);
        GarageController garageController = new GarageController(simulator);
        InfoView info = new InfoView(simulator);
        JFrame window = new JFrame("Parkeergarage simulatie");

        window.setSize(1200, 700);
        window.setResizable(false);
        window.setLayout(null);

        window.add(garageView);
        window.add(info);
        window.add(garageController);
        window.add(graph);
        window.setJMenuBar(menuBar);
        menuBar.add(menu);
        menu.add(settingsItem);

        garageView.setBounds(-50, 20, 800, 300);
        garageController.setBounds(10, 320, 730, 30);
        garageController.setBackground(Color.red);
        info.setBounds(750, 10, 430, 340);
        graph.setBounds(10, 360, 400, 290);
        graph.setBackground(Color.lightGray);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        simulator.tick();
    }

    public static void main(String[] args) {
        new Garage();
    }
}