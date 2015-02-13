/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curvaabc.controller;

import curvaabc.model.CurvaABC;
import static curvaabc.view.TabelaABCView.round;
import curvaabc.model.Relatorio;
import curvaabc.model.Produto;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author allan
 */
public class RelatorioController {
    
    Relatorio relatorio = new Relatorio();
    
    public RelatorioController() throws SQLException{
        relatorio.setTexto(relatorio.create());
    }
    
    public String getRelatorio(){
        return relatorio.getTexto();
    }
    
}
