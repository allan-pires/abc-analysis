/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curvaabc.model;

import curvaabc.model.Produto;
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

    }

    // Construtor com parâmetros
    public CurvaABC(double a, double b, double c) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        //Instancia porcentagem de classes
        this.classeA_porcentagem = a;
        this.classeB_porcentagem = b;
        this.classeC_porcentagem = c;

        // Zera o valor acumulado
        this.valor_total = 0;
        
        produtos = new ArrayList<>();
        valor_acumulado = new ArrayList<>();
        porcentagem_acumulada = new ArrayList<>();
        quantidade_acumulada = new ArrayList<>();
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
    
    public void addQuantidade_acumulada(Double v) {
        this.quantidade_acumulada.add(v);
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    // Retorna a porcentagem do produto em relação ao valor total
    public double getPorcentagemValor(Produto produto) {
        return produto.getValorTotal() / valor_total;
    }

    // Retorna a porcentagem do produto em relação ao total de produtos vendidos
    public double getPorcentagemQuantidade(Produto produto) {
        return ((double) produto.getVendidos()) / quantidade_total;
    }
    
    
    public void addPorcentagem_acumulada(double p) {
       this.porcentagem_acumulada.add(p);
    }

    public void addValor_acumulado(double v) {
        this.valor_acumulado.add(v);
    }

    

    public static void main(String[] args) {
        InicioView t = new InicioView();
        t.show();
    }


}
