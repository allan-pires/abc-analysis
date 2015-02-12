/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curvaabc.model;

import curvaabc.ConexaoDB;
import curvaabc.controller.ProdutoController;
import curvaabc.view.InicioView;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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


    //Construtor sem parâmetros
    public CurvaABC() throws SQLException  {

        //Instancia porcentagem de classes
        this.classeA_porcentagem = 0.7;
        this.classeB_porcentagem = 0.2;
        this.classeC_porcentagem = 0.1;

        atualizarConfiguração();


    }

    // Construtor com parâmetros
    public CurvaABC(double a, double b, double c)  {

        //Instancia porcentagem de classes
        this.classeA_porcentagem = a;
        this.classeB_porcentagem = b;
        this.classeC_porcentagem = c;

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
            setClasseA_porcentagem (round(a,2));
            setClasseB_porcentagem (round(b,2));
            setClasseC_porcentagem (round(c,2));

        }
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
