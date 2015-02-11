/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curvaabc.controller;

import model.CurvaABC;
import java.awt.BasicStroke;
import java.awt.Color;
import model.Produto;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Grafico;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
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
        XYDataset dataset = null;
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
  
    private XYDataset createDataset() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        CurvaABCController c_controller = new CurvaABCController();
        CurvaABC c = c_controller.getCurva();
        ArrayList<Produto> produtos = c.getProdutos();
        c_controller.sortByValorTotal(produtos);
        
        // Cria as séries de linhas
        //Serie 1 = Linha todas as classes
        //Serie 2 = Linha classe A
        //Serie 3 = Linha classe B
        //Serie 4 = Linha classe C
        final XYSeries classeA = new XYSeries("Classe A");
        final XYSeries classeB = new XYSeries("Classe B");
        final XYSeries classeC = new XYSeries("Classe c");
        final XYSeries all = new XYSeries("");
        
        // Adiciona o ponto inicial
        all.add(0.0,0.0);
        
        for (int i = 0; i < produtos.size(); i++){
            Produto p = produtos.get(i);
            
            // Define os valores de X e Y
            double x, y;
            x = (double)(((double)i+1)/(double)produtos.size())*100.0;
            y = c.getPorcentagem_acumulada().get(i)*100.0;
            
            // Pega a classe do produto
            String classe = c_controller.getClasse(i);
            
            // Adiciona na série correspondente
            all.add(x, y);
            switch(classe){
                case "A": classeA.add(x, y); break;
                case "B": classeB.add(x, y); break;
                case "C": classeC.add(x, y); break;
            }
            
        }
                
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(all);
        dataset.addSeries(classeA);
        dataset.addSeries(classeB);
        dataset.addSeries(classeC);
                
        return dataset;    
    }
    
    private JFreeChart createChart(final XYDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Gráfico de Produtos por Classe",      // chart title
            "% Produtos",                      // x axis label
            "% Valor",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);
        
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.white);
        
        // plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.gray);
        plot.setRangeGridlinePaint(Color.gray);
        
        //Serie 1 = Linha todas as classes
        //Serie 2 = Linha classe A
        //Serie 3 = Linha classe B
        //Serie 4 = Linha classe C
        
        // Mostra linha de todas as classes
        final XYSplineRenderer renderer = new XYSplineRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesVisibleInLegend(0, false);
        renderer.setSeriesPaint(0, Color.black);
        renderer.setSeriesPaint(3, Color.MAGENTA);
        
        // Não mostra as linhas de classes A,B e C
        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesLinesVisible(2, false);
        renderer.setSeriesLinesVisible(3, false);
        
        // Não mostra os símbolos de Serie 1
        renderer.setSeriesShapesVisible(0, false);
        renderer.setStroke(new BasicStroke(1.0f));

 
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.
                
        return chart;
    }
    
}
