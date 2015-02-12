/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curvaabc.model;

import java.util.ArrayList;

/**
 *
 * @author Allan Victor
 */


public class Produto{

    private String id;
    
    private Double preco;
    
    private Integer vendidos;
   
    private Integer criticidade;
    
    private Double valor_acumulado;
    
    private Double porcentagem_acumulada;
    
    private String classe;
    
    
    public Produto() {
    }

    public Produto(String id, Double preco, Integer vendidos, Integer criticidade) {
        this.id = id;
        this.preco = preco;
        this.vendidos = vendidos;
        this.criticidade = criticidade;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Produto> retornarProdutos(){
        ArrayList<Produto> produtos = new ArrayList<>();    
        return produtos;
    }
    
    public void setId(String id) {
        String oldId = this.id;
        this.id = id;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        Double oldPreco = this.preco;
        this.preco = preco;
    }

    public Integer getVendidos() {
        return vendidos;
    }

    public void setVendidos(Integer vendidos) {
        Integer oldVendidos = this.vendidos;
        this.vendidos = vendidos;
    }

    public Integer getCriticidade() {
        return criticidade;
    }

    public void setCriticidade(Integer criticidade) {
        Integer oldCriticidade = this.criticidade;
        this.criticidade = criticidade;
    }
    
    // Retorna o valor total de vendas
    public double getValorTotal(){
        return (this.getPreco() * this.getVendidos());
    }
    
    public String getClasse(){
        return classe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
    @Override
    public String toString() {
        return "Produto[ id=" + id + " ; preço="+preco+"; vendidos="+vendidos+"; criticidade="+criticidade+"]\n";
    }

    public Double getValor_acumulado() {
        return valor_acumulado;
    }

    public void setValor_acumulado(Double valor_acumulado) {
        this.valor_acumulado = valor_acumulado;
    }

    public Double getPorcentagem_acumulada() {
        return porcentagem_acumulada;
    }

    public void setPorcentagem_acumulada(Double porcentagem_acumulada) {
        this.porcentagem_acumulada = porcentagem_acumulada;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    
}
