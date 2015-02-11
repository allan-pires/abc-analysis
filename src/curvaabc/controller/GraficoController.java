/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curvaabc.controller;

import curvaabc.model.CurvaABC;
import java.awt.BasicStroke;
import java.awt.Color;
import curvaabc.model.Produto;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import curvaabc.model.Grafico;
import static curvaabc.view.TabelaABCView.round;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
/**
 *
 * @author doisl_000
 */
public class GraficoController {  
    
    private Grafico grafico;

    public Grafico getGrafico() {
        return grafico;
    }

    public GraficoController() {
        CategoryDataset dataset = null;
        try {
            dataset = createDataset();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GraficoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(GraficoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GraficoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        JFreeChart chart = createChart(dataset);
        this.grafico = new Grafico(dataset, chart);
    }
  
    public CategoryDataset createDataset() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        // row keys...
        final String series1 = "% Itens";
        final String series2 = "% Valor";

        // column keys...
        final String classeA = "Classe A";
        final String classeB = "Classe B";
        final String classeC = "Classe C";
        
        //Data
        // Inicia lista de classes
        ArrayList<Produto> a = new ArrayList<Produto>();
        ArrayList<Produto> b = new ArrayList<Produto>();
        ArrayList<Produto> c = new ArrayList<Produto>();
        
        CurvaABCController c_controller = new CurvaABCController();
        
        // Atualiza lista de classes
        CurvaABC curva = new CurvaABC();
        ArrayList<Produto> produtos = c_controller.getCurva().getProdutos();
        for (int i = 0; i < produtos.size(); i++){
            String classe = c_controller.getClasse(i);
            switch(classe){
                case "A": a.add(produtos.get(i)); break;
                case "B": b.add(produtos.get(i)); break;
                case "C": c.add(produtos.get(i)); break;
            }
        }

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        double porcentagem_itens = 0.0;
        
        //Porcentagem de A
        porcentagem_itens = ((double)a.size()/(double)produtos.size())*100.0;
        dataset.addValue(porcentagem_itens, series1, classeA);
        
        //Porcentagem de B
        porcentagem_itens = ((double)b.size()/(double)produtos.size())*100.0;
        dataset.addValue(porcentagem_itens, series1, classeB);
        
        // Porcentagem de C
        porcentagem_itens = ((double)c.size()/(double)produtos.size())*100.0;
        dataset.addValue(porcentagem_itens, series1, classeC);
        
        //Calcula porcentagem Acumulada de A
        int size = 0;
        double pa = 0.0;
        if (a.size() > 0){
            size = a.size()-1;
            pa = (curva.getPorcentagem_acumulada().get(size)*100);
        }
        double porcentagem_acumuladaA = round(pa, 2);
        dataset.addValue(porcentagem_acumuladaA, series2, classeA);
        
        //Calcula porcentagem Acumulada de B
        double pb_acumulado = 0.0;
        if (a.size() > 0 && b.size() > 0){
            size += b.size();
            pb_acumulado = round(((curva.getPorcentagem_acumulada().get(size))*100),2);
        }
        else if (b.size() > 0){
            size += b.size()-1;
            pb_acumulado = round(((curva.getPorcentagem_acumulada().get(size))*100),2);
        }
        
        //Calcula porcentagem Acumulada de C
        double pb = round(pb_acumulado-pa, 2);
        dataset.addValue(pb, series2, classeB);
        
        double pc_acumulado = 0.0;
        if (b.size() > 0 && c.size() > 0){
            size += c.size();
            pc_acumulado = round(((curva.getPorcentagem_acumulada().get(size))*100),2);
        }
        else if (c.size() > 0){
            size += c.size()-1;
            pc_acumulado = round(((curva.getPorcentagem_acumulada().get(size))*100),2);
        }
        
        double pc = round(pc_acumulado-pa-pb, 2);
        dataset.addValue(pc, series2, classeC);
        
        return dataset;
        
    }
    
    private JFreeChart createChart(final CategoryDataset dataset) {
        
       // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
            "Gráfico de Produtos por Classe",      // chart title
            "Classe",                      // x axis label
            "% Valor",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );
        
        BarRenderer renderer = new BarRenderer();
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        chart.getCategoryPlot().setRenderer(renderer);
        
        return chart;
    }
    
}
