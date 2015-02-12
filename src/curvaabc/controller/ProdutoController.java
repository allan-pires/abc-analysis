/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curvaabc.controller;

import curvaabc.ConexaoDB;
import curvaabc.model.Produto;
import curvaabc.view.AddProdutoView;
import java.awt.Component;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author allan
 */
public class ProdutoController {
    
        // Lista de produtos
        private ArrayList<Produto> produtos;
        
        // Valor total de produtos vendidos
        private double valor_total;

        public ProdutoController(ArrayList<Produto> produtos) {
            this.produtos = produtos;
        }

        public ProdutoController() throws SQLException {
            // Zera o valor acumulado
            this.valor_total = 0;
            
            Produto p = new Produto();
            this.produtos = p.popularArrayListProdutos(produtos);
            p.sortByValorTotal(this.produtos);
            atualizarValorTotal(this.produtos);
            p.atualizarPorcentagemAcumulada(this.produtos);
            p.atualizarClasses(this.produtos);
      
        }

        public ArrayList<Produto> getProdutos() {
            return produtos;
        }

        public void setProdutos(ArrayList<Produto> produtos) {
            this.produtos = produtos;
        }
        
        public boolean produtoOk(Produto p){
            
            for (int i = 0; i < produtos.size(); i++){
                String id_atual = produtos.get(i).getId();
                if (p.getId().equals(id_atual)) return false;
            }
            
            if (produtos.contains(p)){
                return false;
            }
            
            if  (p.getVendidos() < 0 && p.getPreco() < 0){
                return false;   
            }
            
            return true;
        }
        
        public double getValor_total() {
            return valor_total;
        }

        public void setValor_total(double valor_total) {
            this.valor_total = valor_total;
        }
        
        // Atualiza o valor acumulado de produtos
        private void atualizarValorTotal(ArrayList<Produto> produtos) {
            setValor_total(0);

            for (int i = 0; i < produtos.size(); i++) {
                double v = getValor_total() + produtos.get(i).getValorTotal();
                setValor_total(v);
            }
        }

    public void sortByCriterio(ArrayList<Produto> produtos) {
        Produto p = new Produto();
        p.sortByCriterio(produtos);
    }

    public String getClasse(Produto p) throws SQLException {
        Produto pc = new Produto();
        return pc.getClasse(p);
    }

    public void alterarProduto(Component view,Produto p) throws SQLException {
        ConexaoDB.alterarProduto(view, p);
    }

    public void adicionarProduto(Component view,Produto p) throws SQLException {
        ConexaoDB.adicionarProduto(view, p);
    }
    
}
