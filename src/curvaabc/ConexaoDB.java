/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curvaabc;

import java.sql.Connection;
import java.sql.DriverManager;  
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException; 
import java.sql.Statement;

/**
 *
 * @author doisl_000
 */
public class ConexaoDB {
    
    // Conecta ao banco de dados
    public static Connection getConexao(){
        
        System.out.println("Conectando ao banco de dados...");
        try {
            // Caso não exista, o banco de dados será criado
            String db_url = "jdbc:derby:curva_db;create=true";
            Connection con = DriverManager.getConnection(db_url);
            // Se conectou, verifica tabelas
            
            if (con != null) {
                System.out.println("Conectado ao banco de dados"); 
                if (!tabelaProdutoExiste(con)) criarTabelaProduto(con);
                if (!tabelaClasseConfigExiste(con)) criarTabelaClasseConfig(con);
            }
            return con;

        } 
        catch (SQLException ex) {
        }
        
        return null;
    }
    
    // Verifica se existe a tabela PRODUTO no banco de dados
    private static boolean tabelaProdutoExiste(Connection con) throws SQLException{
        
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select s.schemaname || '.' || t.tablename \n" +
        "     from sys.systables t, sys.sysschemas s \n" +
        "     where t.schemaid = s.schemaid\n" +
        "          and t.tabletype = 'T'\n" +
        "     order by s.schemaname, t.tablename");
    
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        String rs_string = "";
    
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = rs.getString(i);
                rs_string += columnValue;
            }
        }
        
        return rs_string.contains("PRODUTO");
    }
    
    private static boolean tabelaClasseConfigExiste(Connection con) throws SQLException{
        
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select s.schemaname || '.' || t.tablename \n" +
        "     from sys.systables t, sys.sysschemas s \n" +
        "     where t.schemaid = s.schemaid\n" +
        "          and t.tabletype = 'T'\n" +
        "     order by s.schemaname, t.tablename");
    
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        String rs_string = "";
    
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = rs.getString(i);
                rs_string += columnValue;
            }
        }
        
        return rs_string.contains("CLASSECONFIG");
    }
    
    // Cria a tabela PRODUTO
    private static void criarTabelaProduto(Connection con) throws SQLException {
            
            String sqlCreate = "CREATE TABLE PRODUTO"
            + "  (ID INT NOT NULL,"
            + "   PRECO DOUBLE NOT NULL,"
            + "   VENDIDOS INT NOT NULL,"
            + "   CRITICIDADE INT NOT NULL,"
            + "   PRIMARY KEY (ID))";
            
            Statement stmt = con.createStatement();
            stmt.execute(sqlCreate);
            System.out.println("Criada tabela PRODUTO");
        }
    

// Cria a tabela CLASSECONFIG
    private static void criarTabelaClasseConfig(Connection con) throws SQLException {
            
            String sqlCreate = "CREATE TABLE CLASSECONFIG"
            + "  (A INT NOT NULL,"
            + "   B INT NOT NULL,"
            + "   C INT NOT NULL)";
            
            Statement stmt = con.createStatement();
            stmt.execute(sqlCreate);
            
            // Cria o comando SQL para inserir configuração padrão
            String sqlInsert = "INSERT INTO CLASSECONFIG (A, B, C) VALUES "
                 +"(70.0, 20.0, 10.0)";
            stmt.execute(sqlInsert);
            
            System.out.println("Criada tabela CLASSECONFIG");
        }
    }
    

