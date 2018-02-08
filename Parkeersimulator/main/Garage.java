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

	private Garage() { }

	public static void main(String[] args) {
		new Simulator();
	}
}