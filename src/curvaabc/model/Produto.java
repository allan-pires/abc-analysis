/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curvaabc.model;

import curvaabc.ConexaoDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    
    // Retorna o valor total de uma lista de produtos
    private Double getValorTotal(ArrayList<Produto> produtos)  {

        double v = 0.0;
        
        for (int i = 0; i < produtos.size(); i++) {
             v += produtos.get(i).getValorTotal();
        }
        
        return v;
    }
    
    public String getClasse(Produto p) throws SQLException{
        CurvaABC curva = new CurvaABC();
        if (p.getPorcentagem_acumulada()*100 < curva.getClasseA_porcentagem()) {
            return "A";
        } else if (p.getPorcentagem_acumulada()*100 < curva.getClasseA_porcentagem() + curva.getClasseB_porcentagem()) {
            return "B";
        } else {
            return "C";
        }
    }
    
    
    // Retorna a porcentagem do produto em relação ao valor total
    public double getPorcentagemValor(ArrayList<Produto> produtos, Produto produto) {
        return produto.getValorTotal() / getValorTotal(produtos);
    }
    
    // Popula a lista de produtos com dados do banco
    public ArrayList<Produto> popularArrayListProdutos(ArrayList<Produto> produtos) throws SQLException {
        produtos = new ArrayList<Produto>();
        
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
            produtos.add(p);

        }
        return produtos;
    }
    
    public void atualizarClasses(ArrayList<Produto> produtos) throws SQLException {
        for (int i = 0; i < produtos.size(); i++) {
            String classe = getClasse(produtos.get(i));
            produtos.get(i).setClasse(classe);
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
    
    public void atualizarPorcentagemAcumulada(ArrayList<Produto> produtos) {

        for (int i = 0; i < produtos.size(); i++) {
            double p;
            double v;
            
            if (i > 0) {
                p = getPorcentagemValor(produtos, produtos.get(i)) + produtos.get(i-1).getPorcentagem_acumulada();
                v = produtos.get(i).getValorTotal() + produtos.get(i-1).getValor_acumulado();
                
            } else {
                p = getPorcentagemValor(produtos, produtos.get(i));
                v = produtos.get(i).getValorTotal();
            }
            
            produtos.get(i).setPorcentagem_acumulada(p);
            produtos.get(i).setValor_acumulado(v);
        }
    }
    
}
