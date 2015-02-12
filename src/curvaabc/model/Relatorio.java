/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curvaabc.model;

/**
 *
 * @author allan
 */
public class Relatorio {
    
    private String texto;

    public Relatorio(String texto) {
        this.texto = texto;
    }

    public Relatorio() {
        this.texto = "";
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    
}
