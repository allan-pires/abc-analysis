/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curvaabc.controller;

import curvaabc.model.CurvaABC;
import static curvaabc.view.TabelaABCView.round;
import curvaabc.model.Relatorio;
import curvaabc.model.Produto;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author allan
 */
public class RelatorioController {
    
    Relatorio relatorio = new Relatorio();
    
    // Cria o relatório
    public String print() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        CurvaABCController c_controller = new CurvaABCController();
        CurvaABC curva = c_controller.getCurva();
        
        // Atualiza porcentagem
        double porcentagemA = (curva.getClasseA_porcentagem());
        double porcentagemB = (curva.getClasseB_porcentagem());
        double porcentagemC = (curva.getClasseC_porcentagem());
        
        // Inicia lista de classes
        ArrayList<Produto> a = new ArrayList<Produto>();
        ArrayList<Produto> b = new ArrayList<Produto>();
        ArrayList<Produto> c = new ArrayList<Produto>();
        ArrayList<Produto> produtos = curva.getProdutos();
        
        // Atualiza lista de classes
        for (int i = 0; i < produtos.size(); i++){
            String classe = produtos.get(i).getClasse();
            switch(classe){
                case "A": a.add(produtos.get(i)); break;
                case "B": b.add(produtos.get(i)); break;
                case "C": c.add(produtos.get(i)); break;
            }
        }
        
        // Título de seções do relatório
        String relatorio_completo = "<html>";
        String relatorio_A = "<b>Classe A - Até "+porcentagemA+"% do valor monetário total acumulado de consumo</b><br>\n";
        String relatorio_B = "<b>Classe B - Entre "+(porcentagemA+1.0)+" e "+(porcentagemA+porcentagemB)+"% do valor monetário total acumulado de consumo</b><br>\n";
        String relatorio_C = "<b>Classe C - Entre "+((porcentagemA+porcentagemB)+1.0)+" e "+(porcentagemA+porcentagemB+porcentagemC)+"% do valor monetário total acumulado de consumo</b><br>\n";
        
        // Relatório de itens classe A
        relatorio_A += " - Itens: ";
        if(a.size() != 0){
            for (int i = 0; i < a.size()-1; i++){
                relatorio_A += (a.get(i).getId())+",";
            }
            relatorio_A += (a.get(a.size()-1).getId());
        }
        else relatorio_A += "Nenhum item";
        double porcentagem_itensAcumulado = 0.0;
        double porcentagem_itensA = (((double)a.size()/(double)produtos.size())*100.0);
        relatorio_A += " = "+ porcentagem_itensA + "% dos itens<br>\n";
        porcentagem_itensAcumulado += porcentagem_itensA;
        
        // Relatório de itens classe B
        relatorio_B += " - Itens: ";
        if(b.size() != 0){
            for (int i = 0; i < b.size()-1; i++){
                relatorio_B += (b.get(i).getId())+",";
            }
            relatorio_B += (b.get(b.size()-1).getId());
        }
        else relatorio_B += "Nenhum item";
        double porcentagem_itensB = (((double)b.size()/(double)produtos.size())*100.0);
        porcentagem_itensAcumulado += porcentagem_itensB;
        relatorio_B += " = "+ porcentagem_itensB + "% dos itens ("+porcentagem_itensAcumulado+"% - "+porcentagem_itensA+"% Classificação A)<br>\n";
        
        // Relatório de itens classe C
        relatorio_C += " - Itens: ";
        if(c.size() != 0){
            for (int i = 0; i < c.size()-1; i++){
                relatorio_C += (c.get(i).getId())+",";
            }
            relatorio_C += (c.get(c.size()-1).getId());
        }
        else relatorio_A += "Nenhum item";
        double porcentagem_itensC = (((double)c.size()/(double)produtos.size())*100.0);
        porcentagem_itensAcumulado += porcentagem_itensC;
        relatorio_C += " = "+ porcentagem_itensC + "% dos itens ("+porcentagem_itensAcumulado+"% - "+(porcentagem_itensB+porcentagem_itensA)+"% Classificação B)<br>\n";
        
        /*ArrayList<Double> porcentagem_acumulada = curva.getPorcentagem_acumulada();*/
        // Relatório de análise classe A
        int size = 0;
        double pa = 0.0;
        if (a.size() > 0){
            size = a.size()-1;
            pa = (a.get(size).getPorcentagem_acumulada()*100);
        }
        double porcentagem_acumuladaA = round(pa, 2);
        relatorio_A += " - Análise: <b>"+porcentagem_itensA+"%</b> dos itens representam <b>"+porcentagem_acumuladaA+"%</b> do valor monetário total acumulado movimentado pelo estoque -> <b>Classificação A</b><br><br>\n\n";
        
        // Relatório de análise classe B
        double pb_acumulado = 0.0;
        if (a.size() > 0 && b.size() > 0){
            size = b.size()-1;
            pb_acumulado = round(((b.get(size).getPorcentagem_acumulada())*100),2);
        }
        else if (b.size() > 0){
            size = b.size()-1;
            pb_acumulado = round((b.get(size).getPorcentagem_acumulada()*100),2);
        }
        
        double pb = round(pb_acumulado-pa, 2);
        relatorio_B += " - Análise: <b>"+porcentagem_itensB+"%</b> dos itens representam <b>"+pb+"%</b> do valor monetário total acumulado movimentado pelo estoque ("+pb_acumulado+"% - "+round(pa,2)+"% marcação classificação A) -> <b>Classificação B</b><br><br>\n\n";
        
        // Relatório de análise classe C
        double pc_acumulado = 0.0;
        if (b.size() > 0 && c.size() > 0){
            size = c.size()-1;
            pc_acumulado = round((c.get(size).getPorcentagem_acumulada()*100),2);
        }
        else if (c.size() > 0){
            size = c.size()-1;
            pc_acumulado = round((c.get(size).getPorcentagem_acumulada()*100),2);
        }
        
        double pc = round(pc_acumulado-pa-pb, 2);
        relatorio_C += " - Análise: <b>"+porcentagem_itensC+"%</b> dos itens representam <b>"+pc+"%</b> do valor monetário total acumulado movimentado pelo estoque ("+pc_acumulado+"% - "+round(pa+pb,2)+"% marcação classificação B) -> <b>Classificação C</b><br><br>\n\n";
        
        relatorio_completo += relatorio_A + relatorio_B + relatorio_C;
        relatorio_completo += "</html>";
        
        this.relatorio.setTexto(relatorio_completo);
        return relatorio_completo;
    }
}
