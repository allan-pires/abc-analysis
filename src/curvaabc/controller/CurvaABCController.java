/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curvaabc.controller;

import curvaabc.ConexaoDB;
import curvaabc.model.CurvaABC;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import curvaabc.model.Produto;

/**
 *
 * @author allan
 */
public class CurvaABCController {
    
    private CurvaABC curva;

    public CurvaABC getCurva() {
        return curva;
    }
    
    //Conexão com o banco de dados
    private Connection db_con;

    public CurvaABCController() {
        
        try {
            // Conecta ao banco de dados
            conectarBD();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CurvaABCController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(CurvaABCController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CurvaABCController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            // Popula o array de produtos e atualiza variáveis
            curva = new CurvaABC();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CurvaABCController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(CurvaABCController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CurvaABCController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        atualizarConfiguração();
        popularArrayListProdutos();
        sortByValorTotal(curva.getProdutos());
        atualizarQuantidadeTotal(curva.getProdutos());
        atualizarValorTotal(curva.getProdutos());
        atualizarPorcentagemAcumulada();
    }
    
    
    // Inicia variável de conexão com banco de dados
    private void conectarBD() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        //String driver = "org.apache.derby.jdbc.ClientDriver";
        //Class.forName(driver).newInstance();
        db_con = ConexaoDB.getConexao();
    }
    
    // Atualizar configuração
    private void atualizarConfiguração() {
        try {
            // Pega os dados do banco de dados
            Statement stmt = db_con.createStatement();
            ResultSet list = stmt.executeQuery("SELECT * FROM CLASSECONFIG");

            try {
                // Enquanto ainda houver produtos
                while (list.next()) {

                    // Inicia variáveis de acordo o banco de dados
                    double a = Double.valueOf(list.getString(1));
                    double b = Double.valueOf(list.getString(2));
                    double c = Double.valueOf(list.getString(3));

                    // Atualiza variáveis
                    curva.setClasseA_porcentagem (round(a,2));
                    curva.setClasseB_porcentagem (round(b,2));
                    curva.setClasseC_porcentagem (round(c,2));

                }

            } catch (SQLException q) {
            }
        } catch (SQLException e) {
        }

    }

    // Popula a lista de produtos com dados do banco
    private void popularArrayListProdutos() {
        try {
            // Pega os dados do banco de dados
            Statement stmt = db_con.createStatement();
            ResultSet list = stmt.executeQuery("SELECT * FROM PRODUTO");

            try {
                // Enquanto ainda houver produtos
                while (list.next()) {

                    // Inicia variáveis de acordo o banco de dados
                    String id = list.getString(1);
                    Double preco = Double.parseDouble(list.getString(2));
                    int vendidos = Integer.parseInt(list.getString(3));
                    Integer criticidade = Integer.parseInt(list.getString(4));

                    // Cria um novo produto com os dados do banco
                    Produto p = new Produto(id, preco, vendidos, criticidade);

                    // Adiciona o produto numa lista de produtos
                    curva.getProdutos().add(p);

                }

            } catch (SQLException q) {
            }
        } catch (SQLException e) {
        }
        

    }

    // Ordena um arraylist de produtos
    public ArrayList<Produto> sortByValorTotal(ArrayList<Produto> produtos) {
        Collections.sort(produtos, new Comparator<Produto>() {
            @Override
            public int compare(Produto a, Produto b) {
                return (Double.valueOf(a.getPreco() * a.getVendidos())).compareTo(Double.valueOf(b.getPreco()* b.getVendidos()));
            }
        });

        Collections.reverse(produtos);
        System.out.println(produtos.toString());
        return produtos;
    }

    // Atualiza o valor acumulado de produtos
    private void atualizarValorTotal(ArrayList<Produto> produtos) {
        curva.setValor_total(0);

        for (int i = 0; i < produtos.size(); i++) {
            double v = curva.getValor_total() + produtos.get(i).getValorTotal();
            curva.setValor_total(v);
        }
    }
    
    // Retorna a porcentagem acumulada do produto 
    private void atualizarPorcentagemAcumulada() {
        ArrayList<Produto> produtos = curva.getProdutos();
        for (int i = 0; i < produtos.size(); i++) {
            double p;
            double q;
            double v;
            if (i > 0) {
                p = curva.getPorcentagemValor(produtos.get(i)) + curva.getPorcentagem_acumulada().get(i - 1);
                q = curva.getPorcentagemQuantidade(produtos.get(i)) + curva.getQuantidade_acumulada().get(i - 1);
                v = produtos.get(i).getValorTotal() + curva.getValor_acumulado().get(i - 1);
                
            } else {
                p = curva.getPorcentagemValor(produtos.get(i));
                q = curva.getPorcentagemQuantidade(produtos.get(i));
                v = produtos.get(i).getValorTotal();
            }
            
            curva.addPorcentagem_acumulada(p);
            curva.addQuantidade_acumulada(q);
            curva.addValor_acumulado(v);
        }
    }

    // Retorna a classe do produto
    public String getClasse(int i) {
        if (curva.getPorcentagem_acumulada().get(i)*100 < curva.getClasseA_porcentagem()) {
            return "A";
        } else if (curva.getPorcentagem_acumulada().get(i)*100 < curva.getClasseA_porcentagem() + curva.getClasseB_porcentagem()) {
            return "B";
        } else {
            return "C";
        }
    }
    
    private int atualizarQuantidadeTotal(ArrayList<Produto> p) {
        int t = 0;
        for (int i = 0; i < p.size(); i++) {
            t += p.get(i).getVendidos();
        }

        curva.setQuantidade_total(t);
        return t;
    }
    
    // Arrendondamento
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
