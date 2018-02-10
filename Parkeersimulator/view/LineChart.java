package view;

import java.awt.Color;
import java.awt.Dimension;
import model.GarageModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import model.Simulator;


import javax.swing.*;

public class LineChart extends JPanel{

	public DefaultCategoryDataset dataset;
	public JFreeChart chart;
	public PiePlot plot;
	public Simulator simulator;
	public GarageModel garageModel;

    public LineChart() {

        JFreeChart lineChart = ChartFactory.createLineChart(
        "Aantal auto's",
        "Tijd","Ad hoc auto's",
        createDataset(),
        PlotOrientation.VERTICAL,
        true,true,false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        this.add(chartPanel);
    }


    private DefaultCategoryDataset createDataset() {
      dataset = new DefaultCategoryDataset();
//      dataset.addValue(15, "Ad hoc", "1");
//      dataset.addValue(30, "Ad hoc", "2");
//      dataset.addValue(60, "Ad hoc",  "3");
//      dataset.addValue(120, "Ad hoc", "14:00");
//      dataset.addValue(240, "Ad hoc", "15:00");
//      dataset.addValue(300, "Ad hoc", "16:00");
      return dataset;
   }

    public void updateChart(int val, String row, String col) {
    	dataset.addValue(val, row, col);
    }
    
    public void clearChart() {
    	dataset.clear();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 400);
    }
}