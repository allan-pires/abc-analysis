/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curvaabc;

import curvaabc.view.AddProdutoView;
import curvaabc.view.InicioView;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Allan Victor
 */
public final class CurvaABC {

    // Quantificadores de importância de produtos, onde o valor de porcentagem indica
    // o valor conjunto dos itens daquela classe
    // A -> importância grande
    // B -> importância intermediária
    // C -> importância pequena
    private double classeA_porcentagem;
    
    private double classeB_porcentagem;
    
    private double classeC_porcentagem;

    // Valor total de produtos vendidos
    private double valor_total;
    
    // Quantidade total de produtos vendidos
    private double quantidade_total;

    // Valor acumulado de cada produto
    private ArrayList<Double> valor_acumulado;
    
    // Porcentagem de valor acumulada de cada produto
    private ArrayList<Double> porcentagem_acumulada;
    
    // Quantidade de produto acumulado
    private ArrayList<Double> quantidade_acumulada;

    // Lista de produtos
    private ArrayList<Produto> produtos;

    //Conexão com o banco de dados
    private Connection db_con;

    //Construtor sem parâmetros
    public CurvaABC() throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        //Instancia porcentagem de classes
        this.classeA_porcentagem = 0.7;
        this.classeB_porcentagem = 0.2;
        this.classeC_porcentagem = 0.1;

        // Zera o valor acumulado
        this.valor_total = 0;

        produtos = new ArrayList<>();
        valor_acumulado = new ArrayList<>();
        porcentagem_acumulada = new ArrayList<>();
        quantidade_acumulada = new ArrayList<>();

        // Conecta ao banco de dados
        conectarBD();

        // Popula o array de produtos e atualiza variáveis
        atualizarConfiguração();
        popularArrayListProdutos();
        sortByValorTotal(produtos);
        atualizarQuantidadeTotal(produtos);
        atualizarValorTotal(produtos);
        atualizarPorcentagemAcumulada(produtos);

    }

    // Construtor com parâmetros
    public CurvaABC(double a, double b, double c) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        //Instancia porcentagem de classes
        this.classeA_porcentagem = a;
        this.classeB_porcentagem = b;
        this.classeC_porcentagem = c;

        // Zera o valor acumulado
        this.valor_total = 0;

        // Conecta ao banco de dados
        conectarBD();

        // Popula o array de produtos e atualiza variáveis
        atualizarConfiguração();
        popularArrayListProdutos();
        sortByValorTotal(produtos);
        atualizarQuantidadeTotal(produtos);
        atualizarValorTotal(produtos);
        atualizarPorcentagemAcumulada(produtos);
    }

    public double getClasseA_porcentagem() {
        return classeA_porcentagem;
    }

    public void setClasseA_porcentagem(double classeA_porcentagem) {
        this.classeA_porcentagem = classeA_porcentagem;
    }

    public double getClasseB_porcentagem() {
        return classeB_porcentagem;
    }

    public void setClasseB_porcentagem(double classeB_porcentagem) {
        this.classeB_porcentagem = classeB_porcentagem;
    }

    public double getClasseC_porcentagem() {
        return classeC_porcentagem;
    }

    public void setClasseC_porcentagem(double classeC_porcentagem) {
        this.classeC_porcentagem = classeC_porcentagem;
    }

    public double getValor_total() {
        return valor_total;
    }

    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
    }

    public double getQuantidade_total() {
        return quantidade_total;
    }

    public void setQuantidade_total(double quantidade_total) {
        this.quantidade_total = quantidade_total;
    }

    public ArrayList<Double> getValor_acumulado() {
        return valor_acumulado;
    }

    public void setValor_acumulado(ArrayList<Double> valor_acumulado) {
        this.valor_acumulado = valor_acumulado;
    }

    public ArrayList<Double> getPorcentagem_acumulada() {
        return porcentagem_acumulada;
    }

    public void setPorcentagem_acumulada(ArrayList<Double> porcentagem_acumulada) {
        this.porcentagem_acumulada = porcentagem_acumulada;
    }

    public ArrayList<Double> getQuantidade_acumulada() {
        return quantidade_acumulada;
    }

    public void setQuantidade_acumulada(ArrayList<Double> quantidade_acumulada) {
        this.quantidade_acumulada = quantidade_acumulada;
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    
    
    // Inicia variável de conexão com banco de dados
    private void conectarBD() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        //String driver = "org.apache.derby.jdbc.ClientDriver";
        //Class.forName(driver).newInstance();
        db_con = ConexaoDB.getConexao();
    }
    
    // Atualizar configuração
    public void atualizarConfiguração() {
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
                    this.classeA_porcentagem = round(a,2);
                    this.classeB_porcentagem = round(b,2);
                    this.classeC_porcentagem = round(c,2);

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
                    int id = Integer.parseInt(list.getString(1));
                    Double preco = Double.parseDouble(list.getString(2));
                    int vendidos = Integer.parseInt(list.getString(3));
                    int criticidade = Integer.parseInt(list.getString(4));

                    // Cria um novo produto com os dados do banco
                    Produto p = new Produto(id, preco, vendidos, criticidade);

                    // Adiciona o produto numa lista de produtos
                    produtos.add(p);

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
        valor_total = 0;

        for (int i = 0; i < produtos.size(); i++) {
            valor_total += produtos.get(i).getValorTotal();
        }
    }

    // Retorna a porcentagem do produto em relação ao valor total
    public double getPorcentagemValor(Produto produto) {
        return produto.getValorTotal() / valor_total;
    }

    // Retorna a porcentagem do produto em relação ao total de produtos vendidos
    public double getPorcentagemQuantidade(Produto produto) {
        return ((double) produto.getVendidos()) / quantidade_total;
    }

    // Retorna a porcentagem acumulada do produto 
    private void atualizarPorcentagemAcumulada(ArrayList<Produto> produtos) {
        for (int i = 0; i < produtos.size(); i++) {
            double p;
            double q;
            double v;
            if (i > 0) {
                p = getPorcentagemValor(produtos.get(i)) + porcentagem_acumulada.get(i - 1);
                q = getPorcentagemQuantidade(produtos.get(i)) + quantidade_acumulada.get(i - 1);
                v = produtos.get(i).getValorTotal() + valor_acumulado.get(i - 1);
                
            } else {
                p = getPorcentagemValor(produtos.get(i));
                q = getPorcentagemQuantidade(produtos.get(i));
                v = produtos.get(i).getValorTotal();
            }

            porcentagem_acumulada.add(p);
            quantidade_acumulada.add(q);
            valor_acumulado.add(v);
        }
    }

    // Retorna a classe do produto
    public String getClasse(int i) {
        if (porcentagem_acumulada.get(i)*100 < classeA_porcentagem) {
            return "A";
        } else if (porcentagem_acumulada.get(i)*100 < classeA_porcentagem + classeB_porcentagem) {
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

        this.quantidade_total = t;
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

    public static void main(String[] args) {
        InicioView t = new InicioView();
        t.show();
    }

}
