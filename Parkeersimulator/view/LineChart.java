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
/**
 * De klasse waar het lijndiagram wordt aangemaakt
 *
 * @author MinuteJ
 * @version 1.0.0
 */
public class LineChart extends JPanel{

	public DefaultCategoryDataset dataset;
	public JFreeChart chart;
	public PiePlot plot;
	public Simulator simulator;
	public GarageModel garageModel;

    /**
    * Maakt een lijndiagram aan met van tevoren bepaalde instellingen
    */
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


    /**
    * Maakt een nieuwe dataset aan
    */
    private DefaultCategoryDataset createDataset() {
      dataset = new DefaultCategoryDataset();
      return dataset;
   }

   /**
   * Updatet het lijndiagram
   */
    public void updateChart(int val, String row, String col) {
    	dataset.addValue(val, row, col);
    }

    /**
    * Maakt de lijndiagram leeg
    */
    public void clearChart() {
    	dataset.clear();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 400);
    }
}