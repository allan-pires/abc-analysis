/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curvaabc;

import java.sql.Connection;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
/**
 *
 * @author doisl_000
 */
public class GraficoABC {  
    
    public XYDataset createDataset() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        CurvaABC c = new CurvaABC();
        c.sortByValorTotal(c.produtos);
        
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
        
        for (int i = 0; i < c.produtos.size(); i++){
            Produto p = c.produtos.get(i);
            
            // Define os valores de X e Y
            double x, y;
            x = (double)(((double)i+1)/(double)c.produtos.size())*100.0;
            y = c.porcentagem_acumulada.get(i)*100.0;
            
            // Pega a classe do produto
            String classe = c.getClasse(i);
            
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
    
}
