package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Produto;
import util.ConnectionFactory;

public class ProdutoDao {

    private Connection connection;
    
    public ProdutoDao(){    
        
        try
        {
            this.connection =  ConnectionFactory.getConnection() ;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void create(Produto prod) {

        try
        {
           String sql = "INSERT INTO produtos (nome, qtd, preco, tipo) VALUES ('"+prod.getNome()+"', '"+prod.getQtd()+"', '"+prod.getPreco()+"' , '"+prod.getTipo()+"')";
            PreparedStatement stmt = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
           stmt.executeUpdate(); 
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        } 
        
    }
    
    public ArrayList<Produto> list() {

        try
        {
            String sql = "SELECT * FROM produtos ORDER BY tipo";
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ArrayList<Produto> prod = new ArrayList();
            while(rs.next())   
            {
                Produto prooduto = new Produto();
                prooduto.setId(rs.getInt("id"));
                prooduto.setNome(rs.getString("nome"));
                prooduto.setTipo(rs.getString("tipo"));
                prooduto.setPreco(rs.getDouble("preco"));
                prooduto.setQtd(rs.getInt("qtd"));
                prooduto.setCardapio(rs.getBoolean("cardapio"));
                prod.add(prooduto);
            }
            
            return prod;
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        } 
        
        return null;
    }
    
    public void update(Produto prod)
    {
        try
        {
           String sql = "UPDATE produtos SET nome = '"+prod.getNome()+"', tipo = '"+prod.getTipo()+"' , qtd = "+prod.getQtd()+", preco = "+prod.getPreco()+"  WHERE id = " + prod.getId();
           PreparedStatement stmt = this.connection.prepareStatement(sql);
           stmt.executeUpdate();
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        } 
    }
    
    public Produto getProduto(int id) {

        try
        {
            String sql = "SELECT * FROM produtos WHERE id = " + id;            
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            if(rs.next())   
            {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setTipo(rs.getString("tipo"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setCardapio(rs.getBoolean("cardapio"));
                produto.setQtd(rs.getInt("qtd"));
                return produto;
            }
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        } 
        
        return null;
    }
    
    public ArrayList<Produto> listPizza() {

        try
        {
            String sql = "SELECT * FROM produtos WHERE tipo = 'Pizza'  AND cardapio = true";
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ArrayList<Produto> prod = new ArrayList();
            while(rs.next())   
            {
                Produto prooduto = new Produto();
                prooduto.setId(rs.getInt("id"));
                prooduto.setNome(rs.getString("nome"));
                prooduto.setTipo(rs.getString("tipo"));
                prooduto.setPreco(rs.getDouble("preco"));
                prooduto.setQtd(rs.getInt("qtd"));
                prooduto.setCardapio(rs.getBoolean("cardapio"));
                prod.add(prooduto);
            }
            
            return prod;
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        } 
        
        return null;
    }
    
    public ArrayList<Produto> listBebida() {

        try
        {
            String sql = "SELECT * FROM produtos WHERE tipo = 'Bebida' AND cardapio = true ";
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ArrayList<Produto> prod = new ArrayList();
            while(rs.next())   
            {
                Produto prooduto = new Produto();
                prooduto.setId(rs.getInt("id"));
                prooduto.setNome(rs.getString("nome"));
                prooduto.setTipo(rs.getString("tipo"));
                prooduto.setPreco(rs.getDouble("preco"));
                prooduto.setQtd(rs.getInt("qtd"));
                prooduto.setCardapio(rs.getBoolean("cardapio"));
                prod.add(prooduto);
            }
            
            return prod;
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        } 
        
        return null;
    }
    
    public ArrayList<Produto> listIngrediente2(int id) {

        try
        {
            String sql = "select * from produtos where tipo = 'Ingrediente' except (SELECT p.* FROM produtos as p INNER JOIN ingredientes as i ON p.id=i.cod_produto WHERE i.cod_pizza="+id+")";
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ArrayList<Produto> prod = new ArrayList();
            while(rs.next())   
            {
                Produto prooduto = new Produto();
                prooduto.setId(rs.getInt("id"));
                prooduto.setNome(rs.getString("nome"));
                prooduto.setTipo(rs.getString("tipo"));
                prooduto.setPreco(rs.getDouble("preco"));
                prooduto.setQtd(rs.getInt("qtd"));
                prooduto.setCardapio(rs.getBoolean("cardapio"));
                prod.add(prooduto);
            }
            
            return prod;
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        } 
        
        return null;
    }
    
    public ArrayList<Produto> listIngrediente(int id) {

        try
        {
            String sql = "SELECT p.* FROM produtos as p INNER JOIN ingredientes as i ON p.id=i.cod_produto WHERE i.cod_pizza="+id;
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ArrayList<Produto> prod = new ArrayList();
            while(rs.next())   
            {
                Produto prooduto = new Produto();
                prooduto.setId(rs.getInt("id"));
                prooduto.setNome(rs.getString("nome"));
                prooduto.setTipo(rs.getString("tipo"));
                prooduto.setPreco(rs.getDouble("preco"));
                prooduto.setQtd(rs.getInt("qtd"));
                prooduto.setCardapio(rs.getBoolean("cardapio"));
                prod.add(prooduto);
            }
            
            return prod;
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        } 
        
        return null;
    }
}