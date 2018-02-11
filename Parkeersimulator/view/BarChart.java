package view;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * De klasse voor het weergeven van de omzet als een barchart
 */
public class BarChart extends JPanel {

    private final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    /**
     * Maakt een nieuwe barchart aan
     *
     * @param chartTitle de titel van de nieuwe barchart
     */
    public BarChart(String chartTitle) {

        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Type",
                "Omzet in euro's",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));

        this.add(chartPanel);
    }

    /**
     * Creeer een nieuwe dataset voor de barchart
     *
     * @return de dataset
     */
    public CategoryDataset createDataset() {
        return dataset;
    }

    /**
     * Update de barchart met nieuwe gegevens
     *
     * @param d de verwachte omzet in euro's
     * @param e de totale behaalde omzet
     */
    public void updateBarChart(double d, double e) {
        dataset.setValue(d, "Verwachte omzet", "Omzet");
        dataset.setValue(e, "Totale omzet", "Omzet");
    }
}