/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curvaabc.model;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author allan
 */
public class Grafico {
    
    private XYDataset dataset;
    
    private JFreeChart chart;
    
    private ChartPanel chartPanel;

    public ChartPanel getChartPanel() {
        return chartPanel;
    }

    public void setChartPanel(ChartPanel chartPanel) {
        this.chartPanel = chartPanel;
    }

    public Grafico(XYDataset dataset, JFreeChart chart) {
        this.dataset = dataset;
        this.chart = chart;
        this.chartPanel = new ChartPanel(chart);
    }

}
