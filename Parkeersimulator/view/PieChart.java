package view;

import java.awt.Color;
import java.awt.Dimension;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;

/**
 * De klasse waar het taart diagram wordt aangemaakt
 * 
 * @author MinuteJ
 * @version 1.0.0
 */
public class PieChart extends JPanel{
	
	public DefaultPieDataset dataset;
	public JFreeChart chart;
	public PiePlot plot;
	
	/**
	 * Maakt een nieuwe gegevens zet en diagram aan
	 */
    public PieChart() {
		dataset = new DefaultPieDataset();
		
		// Maakt een chart aan met als titel Aantal auto's in de parkeergarage
        chart = ChartFactory.createPieChart("Aantal auto's in de parkeergarage", dataset, false, false, false);
        plot = (PiePlot) chart.getPlot();
        
        // Zet de instellingen van het diagram
        plot.setOutlineVisible(false);
        plot.setCircular(true);
        plot.setShadowYOffset(4);
        plot.setShadowXOffset(4);
        
        // Bepaal de kleur van de verschillende categorieën
        plot.setSectionPaint("Ad hoc auto's", Color.red);
        plot.setSectionPaint("Abonnement auto's", Color.blue);
        plot.setSectionPaint("Gereserveerde auto's", Color.green);
        plot.setSectionPaint("Vrije ad hoc plaatsen", Color.white);
        plot.setSectionPaint("Vrije abonnee plaatsen", Color.gray);
        
        // Maakt een nieuw diagram paneel aan en bepaald de afmetingen van het paneel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 400));
        this.add(chartPanel);        
    }
    
    /**
     * Bepaal de waarde van de gegevens zet met twee parameters
     * 
     * @param name
     * @param value
     */
    public void createPiePiece(String name, int value) {
		dataset.setValue(name, value);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 400);
    }
}