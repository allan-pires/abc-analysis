/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curvaabc.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import curvaabc.model.Grafico;
import java.sql.SQLException;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
/**
 *
 * @author doisl_000
 */
public class GraficoController {  
    
    private final Grafico grafico;

    public Grafico getGrafico() {
        return grafico;
    }

    public GraficoController() throws SQLException {
        Grafico g = new Grafico();
        CategoryDataset dataset = null;
        try {
            dataset = g.createDataset();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(GraficoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        JFreeChart chart = g.createChart(dataset);
        this.grafico = new Grafico(dataset, chart);
    }
  
    
    
}
