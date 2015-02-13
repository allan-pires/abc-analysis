/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curvaabc.model;

import curvaabc.model.Produto;
import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;  
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException; 
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author doisl_000
 */
public class ConexaoDB {

    // Conecta ao banco de dados
    public static Connection iniciarDB(){
        
        System.out.println("Conectando ao banco de dados...");
        try {
            // Caso não exista, o banco de dados será criado
            String db_url = "jdbc:derby:curva_db;create=true";
            Connection db_con = DriverManager.getConnection(db_url);
            // Se conectou, verifica tabelas
            if (db_con != null) {
                System.out.println("Conectado ao banco de dados"); 
                if (tabelaProdutoExiste() == false) criarTabelaProduto();
                if (tabelaClasseConfigExiste() == false) criarTabelaClasseConfig();
            }
            return db_con;

        } 
        catch (SQLException ex) {
            //ex.printStackTrace();
        }
        
        return null;
    }
    
    // Verifica se existe a tabela PRODUTO no banco de dados
    private static boolean tabelaProdutoExiste() throws SQLException{
        String sql = "select s.schemaname || '.' || t.tablename \n" +
        "     from sys.systables t, sys.sysschemas s \n" +
        "     where t.schemaid = s.schemaid\n" +
        "          and t.tabletype = 'T'\n" +
        "     order by s.schemaname, t.tablename";
        
        ResultSet rs = executarQuery(sql);
    
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
    
    private static boolean tabelaClasseConfigExiste() throws SQLException{
        
        ResultSet rs = executarQuery("select s.schemaname || '.' || t.tablename \n" +
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
    private static void criarTabelaProduto() throws SQLException {
            
            String sqlCreate = "CREATE TABLE PRODUTO"
            + "  (ID INT NOT NULL,"
            + "   PRECO DOUBLE NOT NULL,"
            + "   VENDIDOS INT NOT NULL,"
            + "   CRITICIDADE INT NOT NULL,"
            + "   PRIMARY KEY (ID))";
            executarUpdate(sqlCreate);
            System.out.println("Criada tabela PRODUTO");
            
        }
    
    // Cria a tabela CLASSECONFIG
    private static void criarTabelaClasseConfig() throws SQLException {
            
            String sqlCreate = "CREATE TABLE CLASSECONFIG"
            + "  (A INT NOT NULL,"
            + "   B INT NOT NULL,"
            + "   C INT NOT NULL)";
            
            executarUpdate(sqlCreate);
            
            // Cria o comando SQL para inserir configuração padrão
            String sqlInsert = "INSERT INTO CLASSECONFIG (A, B, C) VALUES "
                 +"(70.0, 20.0, 10.0)";
            
            executarUpdate(sqlInsert);
            
            System.out.println("Criada tabela CLASSECONFIG");
        }
    
    // Adiciona o produto no banco de dados
    public static void adicionarProduto(Component view, Produto p) throws SQLException{

            // Cria o comando SQL para inserir produto
            String sql = "INSERT INTO PRODUTO (ID, PRECO, VENDIDOS, CRITICIDADE) VALUES "
                 +"("+p.getId()+","+p.getPreco()+","+p.getVendidos()+","+p.getCriticidade()+")";
            
            
            executarUpdate(sql);
            
            //Feedback
            System.out.println("Adicionado produto.");
            JOptionPane.showMessageDialog(view, "Produto adicionado com sucesso!");

    }
    
    public static void adicionarProdutosTeste() throws SQLException{
        executarUpdate("DROP TABLE PRODUTO");
        criarTabelaProduto();
        executarUpdate("INSERT INTO PRODUTO (ID, PRECO, VENDIDOS, CRITICIDADE) VALUES (1, 0.16, 20000, 1)");
        executarUpdate("INSERT INTO PRODUTO (ID, PRECO, VENDIDOS, CRITICIDADE) VALUES (2, 0.16, 4000, 2)");
        executarUpdate("INSERT INTO PRODUTO (ID, PRECO, VENDIDOS, CRITICIDADE) VALUES (3, 0.20, 1000, 1)");
        executarUpdate("INSERT INTO PRODUTO (ID, PRECO, VENDIDOS, CRITICIDADE) VALUES (4, 0.04, 1000, 2)");
        executarUpdate("INSERT INTO PRODUTO (ID, PRECO, VENDIDOS, CRITICIDADE) VALUES (5, 0.08, 20000, 2)");
        executarUpdate("INSERT INTO PRODUTO (ID, PRECO, VENDIDOS, CRITICIDADE) VALUES (6, 0.04, 25000, 3)");
        executarUpdate("INSERT INTO PRODUTO (ID, PRECO, VENDIDOS, CRITICIDADE) VALUES (7, 0.36, 20000, 1)");
        executarUpdate("INSERT INTO PRODUTO (ID, PRECO, VENDIDOS, CRITICIDADE) VALUES (8, 0.02, 5000, 3)");
        executarUpdate("INSERT INTO PRODUTO (ID, PRECO, VENDIDOS, CRITICIDADE) VALUES (9, 0.20, 4500, 3)");
        executarUpdate("INSERT INTO PRODUTO (ID, PRECO, VENDIDOS, CRITICIDADE) VALUES (10, 0.02, 5000, 3)");
        executarUpdate("INSERT INTO PRODUTO (ID, PRECO, VENDIDOS, CRITICIDADE) VALUES (11, 0.10, 5000, 2)");
        executarUpdate("INSERT INTO PRODUTO (ID, PRECO, VENDIDOS, CRITICIDADE) VALUES (12, 0.08, 1500, 1)");
        executarUpdate("INSERT INTO PRODUTO (ID, PRECO, VENDIDOS, CRITICIDADE) VALUES (13, 0.12, 1500, 3)");
        executarUpdate("INSERT INTO PRODUTO (ID, PRECO, VENDIDOS, CRITICIDADE) VALUES (14, 0.04, 1000, 1)");
        executarUpdate("INSERT INTO PRODUTO (ID, PRECO, VENDIDOS, CRITICIDADE) VALUES (15, 4.90, 300, 3)");
        executarUpdate("INSERT INTO PRODUTO (ID, PRECO, VENDIDOS, CRITICIDADE) VALUES (16, 2.40, 500, 3)");
        executarUpdate("INSERT INTO PRODUTO (ID, PRECO, VENDIDOS, CRITICIDADE) VALUES (17, 2.08, 65, 3)");
        executarUpdate("INSERT INTO PRODUTO (ID, PRECO, VENDIDOS, CRITICIDADE) VALUES (18, 1.60, 255, 3)");
        executarUpdate("INSERT INTO PRODUTO (ID, PRECO, VENDIDOS, CRITICIDADE) VALUES (19, 1.68, 90, 3)");
        executarUpdate("INSERT INTO PRODUTO (ID, PRECO, VENDIDOS, CRITICIDADE) VALUES (20, 1.60, 75, 3)");
    }
    
    // Adiciona o produto no banco de dados
    public static void alterarProduto(Component view, Produto p) throws SQLException{

            // Cria o comando SQL para inserir produto
            String sql = "UPDATE PRODUTO SET ID="+p.getId()+", PRECO="+p.getPreco()+", VENDIDOS="+p.getVendidos()+", CRITICIDADE="+p.getCriticidade()+" WHERE "
                 +"ID="+p.getId();
        
            executarUpdate(sql);
            
            //Feedback
            System.out.println("Produto alterado.");
            JOptionPane.showMessageDialog(view, "Produto modificado com sucesso!");

    }
    
    public static void removerProduto(Component view, Produto p)  throws SQLException{
            
            // Cria o comando SQL para inserir produto
            String sql = "DELETE FROM PRODUTO WHERE "
                 +"ID="+p.getId();
        
            executarUpdate(sql);
            
            //Feedback
            System.out.println("Produto removido.");
            JOptionPane.showMessageDialog(view, "Produto removido!");
            
    }
    
    public static ResultSet executarQuery (String sql) throws SQLException{
        String db_url = "jdbc:derby:curva_db;create=true";
        Connection db_con = DriverManager.getConnection(db_url);      
        Statement stmt = db_con.createStatement();
        ResultSet list = stmt.executeQuery(sql);
        return list;
        
    }
    
    public static void executarUpdate(String sql) throws SQLException{
        String db_url = "jdbc:derby:curva_db;create=true";
        try (Connection db_con = DriverManager.getConnection(db_url); Statement stmt = db_con.createStatement()) {
            stmt.executeUpdate(sql);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
  }
    

