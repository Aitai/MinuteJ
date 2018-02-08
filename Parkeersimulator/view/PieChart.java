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

public class PieChart extends AbstractView{
	
	public DefaultPieDataset dataset;
	public JFreeChart chart;
	public PiePlot plot;
	public Simulator simulator;
	public GarageModel garageModel;
	
    public PieChart(Simulator simulator) {
    	this.simulator = simulator;
		simulator.addView(this);

		dataset = new DefaultPieDataset();
        
        chart = ChartFactory.createPieChart("Aantal auto's in de parkeergarage", dataset, false, false, false);
        plot = (PiePlot) chart.getPlot();
        
        plot.setForegroundAlpha(1);
        plot.setBackgroundImageAlpha(0);
        plot.setOutlineVisible(false);
        plot.setCircular(true);
        plot.setShadowYOffset(0);
        plot.setShadowXOffset(0);
        
        plot.setSectionPaint("Ad hoc auto's", Color.red);
        plot.setSectionPaint("Abonnement auto's", Color.blue);
        plot.setSectionPaint("Gereserveerde auto's", Color.green);
        plot.setSectionPaint("Vrije plaatsen", Color.white);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(300, 300));
        this.add(chartPanel);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 400);
    }

	@Override
	public void updateView() {	
	}
}