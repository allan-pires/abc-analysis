/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curvaabc.controller;

import curvaabc.ConexaoDB;
import curvaabc.model.CurvaABC;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author allan
 */
public class CurvaController {
    
    //Conexão com o banco de dados
    private Connection db_con;
    
    //Variavel de curva
    private CurvaABC curva;
 
    public CurvaController() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        // Conecta ao banco de dados
        conectarBD();

        // Popula o array de produtos e atualiza variáveis
        curva = new CurvaABC();

    }
    
    public CurvaController(CurvaABC curva) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        // Conecta ao banco de dados
        conectarBD();

    }
    
    public CurvaABC getCurva() {
        return curva;
    }
    // Inicia variável de conexão com banco de dados
    private void conectarBD()  {
        db_con = ConexaoDB.getConexao();
    }
    
   
    
}
