package view;

import java.awt.Color;
import java.awt.Dimension;
import model.GarageModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import model.Simulator;


import javax.swing.*;

public class PieChart extends JPanel{
	
	public DefaultPieDataset dataset;
	public JFreeChart chart;
	public PiePlot plot;
	public Simulator simulator;
	public GarageModel garageModel;
	
    public PieChart() {

		dataset = new DefaultPieDataset();
		
        chart = ChartFactory.createPieChart("Aantal auto's in de parkeergarage", dataset, false, false, false);
        plot = (PiePlot) chart.getPlot();
        
        plot.setOutlineVisible(false);
        plot.setCircular(true);
        plot.setShadowYOffset(4);
        plot.setShadowXOffset(4);
        
        plot.setSectionPaint("Ad hoc auto's", Color.red);
        plot.setSectionPaint("Abonnement auto's", Color.blue);
        plot.setSectionPaint("Gereserveerde auto's", Color.green);
        plot.setSectionPaint("Vrije ad hoc plaatsen", Color.white);
        plot.setSectionPaint("Vrije abonnee plaatsen", Color.gray);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 400));
        this.add(chartPanel);        
    }
    
    public void createPiePiece(String name, int value) {
		dataset.setValue(name, value);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 400);
    }
}