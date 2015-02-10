/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curvaabc;

import java.util.ArrayList;

/**
 *
 * @author Allan Victor
 */


public class Produto{

    public Integer id;
    
    public Double preco;
    
    public Integer vendidos;
   
    private Integer criticidade;
    
    
    public Produto() {
    }

    public Produto(Integer id, Double preco, Integer vendidos, Integer criticidade) {
        this.id = id;
        this.preco = preco;
        this.vendidos = vendidos;
        this.criticidade = criticidade;
    }

    public Integer getId() {
        return id;
    }

    public ArrayList<Produto> retornarProdutos(){
        ArrayList<Produto> produtos = new ArrayList<>();    
        return produtos;
    }
    
    public void setId(Integer id) {
        Integer oldId = this.id;
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
        return "A";
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

    
}
