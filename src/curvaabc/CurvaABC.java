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
public class CurvaABC {

    // Quantificadores de importância de produtos, onde o valor de porcentagem indica
    // o valor conjunto dos itens daquela classe
    // A -> importância grande
    // B -> importância intermediária
    // C -> importância pequena
    double classeA_porcentagem;
    double classeB_porcentagem;
    double classeC_porcentagem;

    // Valor total de produtos vendidos
    double valor_total;
    double quantidade_total;

    // Porcentagem acumulada de cada produto
    public ArrayList<Double> valor_acumulado;
    public ArrayList<Double> porcentagem_acumulada;
    public ArrayList<Double> quantidade_acumulada;

    // Lista de produtos
    public ArrayList<Produto> produtos;

    //Conexão com o banco de dados
    Connection db_con;

    //Construtor sem parâmetros
    public CurvaABC() throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        //Instancia porcentagem de classes
        this.classeA_porcentagem = 0.7;
        this.classeB_porcentagem = 0.2;
        this.classeC_porcentagem = 0.1;

        // Zera o valor acumulado
        this.valor_total = 0;

        produtos = new ArrayList<Produto>();
        valor_acumulado = new ArrayList<Double>();
        porcentagem_acumulada = new ArrayList<Double>();
        quantidade_acumulada = new ArrayList<Double>();

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

    // Inicia variável de conexão com banco de dados
    public void conectarBD() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
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
    public void popularArrayListProdutos() {
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
                return (Double.valueOf(a.preco * a.vendidos)).compareTo(Double.valueOf(b.preco * b.vendidos));
            }
        });

        Collections.reverse(produtos);
        System.out.println(produtos.toString());
        return produtos;
    }

    // Atualiza o valor acumulado de produtos
    public void atualizarValorTotal(ArrayList<Produto> produtos) {
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
        return ((double) produto.vendidos) / quantidade_total;
    }

    // Retorna a porcentagem acumulada do produto 
    public void atualizarPorcentagemAcumulada(ArrayList<Produto> produtos) {
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
    
    // Cria o relatório
    public String printRelatorio(){
        
        // Atualiza porcentagem
        double porcentagemA = (classeA_porcentagem);
        double porcentagemB = (classeB_porcentagem);
        double porcentagemC = (classeC_porcentagem);
        
        // Inicia lista de classes
        ArrayList<Produto> a = new ArrayList<Produto>();
        ArrayList<Produto> b = new ArrayList<Produto>();
        ArrayList<Produto> c = new ArrayList<Produto>();
        
        // Atualiza lista de classes
        for (int i = 0; i < produtos.size(); i++){
            String classe = getClasse(i);
            switch(classe){
                case "A": a.add(produtos.get(i)); break;
                case "B": b.add(produtos.get(i)); break;
                case "C": c.add(produtos.get(i)); break;
            }
        }
        
        // Título de seções do relatório
        String relatorio = "<html>";
        String relatorio_A = "<b>Classe A - Até "+porcentagemA+"% do valor monetário total acumulado de consumo</b><br>\n";
        String relatorio_B = "<b>Classe B - Entre "+(porcentagemA+1.0)+" e "+(porcentagemA+porcentagemB)+"% do valor monetário total acumulado de consumo</b><br>\n";
        String relatorio_C = "<b>Classe C - Entre "+((porcentagemA+porcentagemB)+1.0)+" e "+(porcentagemA+porcentagemB+porcentagemC)+"% do valor monetário total acumulado de consumo</b><br>\n";
        
        // Relatório de itens classe A
        relatorio_A += " - Itens: ";
        if(a.size() != 0){
            for (int i = 0; i < a.size()-1; i++){
                relatorio_A += (a.get(i).id)+",";
            }
            relatorio_A += (a.get(a.size()-1).id);
        }
        else relatorio_A += "Nenhum item";
        double porcentagem_itensAcumulado = 0.0;
        double porcentagem_itensA = (((double) a.size()/(double)produtos.size())*100.0);
        relatorio_A += " = "+ porcentagem_itensA + "% dos itens<br>\n";
        porcentagem_itensAcumulado += porcentagem_itensA;
        
        // Relatório de itens classe B
        relatorio_B += " - Itens: ";
        if(b.size() != 0){
            for (int i = 0; i < b.size()-1; i++){
                relatorio_B += (b.get(i).id)+",";
            }
            relatorio_B += (b.get(b.size()-1).id);
        }
        else relatorio_B += "Nenhum item";
        double porcentagem_itensB = (((double) b.size()/(double)produtos.size())*100.0);
        porcentagem_itensAcumulado += porcentagem_itensB;
        relatorio_B += " = "+ porcentagem_itensB + "% dos itens ("+porcentagem_itensAcumulado+"% - "+porcentagem_itensA+"% Classificação A)<br>\n";
        
        // Relatório de itens classe C
        relatorio_C += " - Itens: ";
        if(c.size() != 0){
            for (int i = 0; i < c.size()-1; i++){
                relatorio_C += (c.get(i).id)+",";
            }
            relatorio_C += (c.get(c.size()-1).id);
        }
        else relatorio_A += "Nenhum item";
        double porcentagem_itensC = (((double) c.size()/(double)produtos.size())*100.0);
        porcentagem_itensAcumulado += porcentagem_itensC;
        relatorio_C += " = "+ porcentagem_itensC + "% dos itens ("+porcentagem_itensAcumulado+"% - "+(porcentagem_itensB+porcentagem_itensA)+"% Classificação B)<br>\n";
        
        // Relatório de análise classe A
        int size = 0;
        double pa = 0.0;
        if (a.size() > 0){
            size = a.size()-1;
            pa = (porcentagem_acumulada.get(size)*100);
        }
        double porcentagem_acumuladaA = round(pa, 2);
        relatorio_A += " - Análise: <b>"+porcentagem_itensA+"%</b> dos itens representam <b>"+porcentagem_acumuladaA+"%</b> do valor monetário total acumulado movimentado pelo estoque -> <b>Classificação A</b><br><br>\n\n";
        
        // Relatório de análise classe B
        double pb_acumulado = 0.0;
        if (a.size() > 0 && b.size() > 0){
            size += b.size();
            pb_acumulado = round(((porcentagem_acumulada.get(size))*100),2);
        }
        else if (b.size() > 0){
            size += b.size()-1;
            pb_acumulado = round(((porcentagem_acumulada.get(size))*100),2);
        }
        
        double pb = round(pb_acumulado-pa, 2);
        relatorio_B += " - Análise: <b>"+porcentagem_itensB+"%</b> dos itens representam <b>"+pb+"%</b> do valor monetário total acumulado movimentado pelo estoque ("+pb_acumulado+"% - "+round(pa,2)+"% marcação classificação A) -> <b>Classificação B</b><br><br>\n\n";
        
        // Relatório de análise classe C
        double pc_acumulado = 0.0;
        if (b.size() > 0 && c.size() > 0){
            size += c.size();
            pc_acumulado = round(((porcentagem_acumulada.get(size))*100),2);
        }
        else if (c.size() > 0){
            size += c.size()-1;
            pc_acumulado = round(((porcentagem_acumulada.get(size))*100),2);
        }
        
        double pc = round(pc_acumulado-pa-pb, 2);
        relatorio_C += " - Análise: <b>"+porcentagem_itensC+"%</b> dos itens representam <b>"+pc+"%</b> do valor monetário total acumulado movimentado pelo estoque ("+pc_acumulado+"% - "+round(pa+pb,2)+"% marcação classificação B) -> <b>Classificação C</b><br><br>\n\n";
        
        relatorio += relatorio_A + relatorio_B + relatorio_C;
        relatorio += "</html>";
        return relatorio;
    }

    /**
     * @param args the command line arguments
     */

    public int atualizarQuantidadeTotal(ArrayList<Produto> p) {
        int t = 0;
        for (int i = 0; i < p.size(); i++) {
            t += p.get(i).vendidos;
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
