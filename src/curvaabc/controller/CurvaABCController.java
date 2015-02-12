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
import java.util.Arrays;
import static java.util.Collections.sort;
import org.apache.derby.iapi.services.io.ArrayUtil;

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

    public CurvaABCController() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        // Conecta ao banco de dados
        conectarBD();

        // Popula o array de produtos e atualiza variáveis
        curva = new CurvaABC();

        atualizarConfiguração();
        popularArrayListProdutos();
        sortByValorTotal(this.curva.getProdutos());
        /*atualizarQuantidadeTotal(this.curva.getProdutos());*/
        atualizarValorTotal(this.curva.getProdutos());
        atualizarPorcentagemAcumulada(this.curva);
        atualizarClasses(this.curva);
    }
    
    public CurvaABCController(CurvaABC curva) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        // Conecta ao banco de dados
        conectarBD();

        atualizarConfiguração();
        popularArrayListProdutos();
        sortByValorTotal(this.curva.getProdutos());
        /*atualizarQuantidadeTotal(this.curva.getProdutos());*/
        atualizarValorTotal(this.curva.getProdutos());
        atualizarPorcentagemAcumulada(this.curva);
        atualizarClasses(this.curva);
    }
    
    
    // Inicia variável de conexão com banco de dados
    private void conectarBD() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        //String driver = "org.apache.derby.jdbc.ClientDriver";
        //Class.forName(driver).newInstance();
        db_con = ConexaoDB.getConexao();
    }
    
    // Atualizar configuração
    private void atualizarConfiguração() throws SQLException {
        String sql = "SELECT * FROM CLASSECONFIG";
        ResultSet list = ConexaoDB.executarQuery(sql);

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
    }

    // Popula a lista de produtos com dados do banco
    private void popularArrayListProdutos() throws SQLException {
  
        // Pega os dados do banco de dados
        String sql = "SELECT * FROM PRODUTO";
        ResultSet list = ConexaoDB.executarQuery(sql);

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
    /*
    public void atualizarPorcentagemAcumulada(CurvaABC curva) {
        ArrayList<Produto> produtos = curva.getProdutos();
        curva.setPorcentagem_acumulada(new ArrayList<Double>());
        curva.setQuantidade_acumulada(new ArrayList<Double>());
        curva.setValor_acumulado(new ArrayList<Double>());
        
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
    */
    
    
    public void atualizarPorcentagemAcumulada(CurvaABC curva) {
        ArrayList<Produto> produtos = curva.getProdutos();
        /*curva.setPorcentagem_acumulada(new ArrayList<Double>());
        curva.setQuantidade_acumulada(new ArrayList<Double>());
        curva.setValor_acumulado(new ArrayList<Double>());*/
        
        for (int i = 0; i < produtos.size(); i++) {
            double p;
            double v;
            
            if (i > 0) {
                p = curva.getPorcentagemValor(produtos.get(i)) + produtos.get(i-1).getPorcentagem_acumulada();
                v = produtos.get(i).getValorTotal() + produtos.get(i-1).getValor_acumulado();
                
            } else {
                p = curva.getPorcentagemValor(produtos.get(i));
                v = produtos.get(i).getValorTotal();
            }
            
            produtos.get(i).setPorcentagem_acumulada(p);
            produtos.get(i).setValor_acumulado(v);
        }
    }

    // Retorna a classe do produto
    public String getClasse(Produto p) {
        if (p.getPorcentagem_acumulada()*100 < this.curva.getClasseA_porcentagem()) {
            return "A";
        } else if (p.getPorcentagem_acumulada()*100 < this.curva.getClasseA_porcentagem() + this.curva.getClasseB_porcentagem()) {
            return "B";
        } else {
            return "C";
        }
    }
    
    public void atualizarClasses(CurvaABC curva){
        ArrayList<Produto> produtos = curva.getProdutos();
        for (int i = 0; i < produtos.size(); i++) {
            String classe = getClasse(produtos.get(i));
            produtos.get(i).setClasse(classe);
        }
    }
    
    /*
    private int atualizarQuantidadeTotal(ArrayList<Produto> p) {
        int t = 0;
        for (int i = 0; i < p.size(); i++) {
            t += p.get(i).getVendidos();
        }

        curva.setQuantidade_total(t);
        return t;
    }
    */
    
    public ArrayList<Produto> sortByCriterio(ArrayList<Produto> produtos){
        ArrayList<Produto> a = new ArrayList<>();
        ArrayList<Produto> b = new ArrayList<>();
        ArrayList<Produto> c = new ArrayList<>();
        
        for (Produto produto : produtos) {
            if ("A".equals(produto.getClasse())) {
                a.add(produto);
            }
            if ("B".equals(produto.getClasse())) {
                b.add(produto);
            }
            if ("C".equals(produto.getClasse())) {
                c.add(produto);
            }
        }
        
        a = sortByCriticidade(a);
        b = sortByCriticidade(b);
        c= sortByCriticidade(c);
        
        produtos.clear();
        produtos.addAll(a);
        produtos.addAll(b);
        produtos.addAll(c);
        
        return produtos;
    }
    
    public ArrayList<Produto> sortByCriticidade(ArrayList<Produto> produtos){
        //Sorting
        Collections.sort(produtos, new Comparator<Produto>() {
        @Override
        public int compare(Produto p1, Produto  p2)
        {
            return p2.getCriticidade().compareTo(p1.getCriticidade());
        }
        });
        
        return produtos;
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
