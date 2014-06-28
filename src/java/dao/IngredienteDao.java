package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Ingrediente;
import model.Produto;
import util.ConnectionFactory;

public class IngredienteDao {

    private Connection connection;
    
    public IngredienteDao(){    
        
        try
        {
            this.connection =  ConnectionFactory.getConnection() ;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void create(Ingrediente i) {

        try
        {
           String sql = "INSERT INTO ingredientes (quantidade, cod_pizza, cod_produto) VALUES ('"+i.getQuantidade()+"', '"+i.getCod_pizza()+"', '"+i.getCod_produto()+"')";
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
            String sql = "SELECT * FROM produtos where tipo='Ingrediente'";
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
    
    public void update(Ingrediente i)
    {
        try
        {
           String sql = "UPDATE ingredientes SET quantidade = '"+i.getQuantidade()+"', cod_pizza = '"+i.getCod_pizza()+"' , cod_produto = '"+i.getCod_produto()+"'  WHERE id = " + i.getId();
           PreparedStatement stmt = this.connection.prepareStatement(sql);
           stmt.executeUpdate();
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        } 
    }
    
    public void delete(int id)
    {
        try
        {
           String sql = "DELETE FROM ingredientes WHERE id = " + id;          
           PreparedStatement stmt = this.connection.prepareStatement(sql);
           stmt.executeUpdate();
          
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        } 
    }
    
    public Ingrediente getIngrediente(int cod_pizza, int cod_produto) {

        try
        {
            String sql = "SELECT * FROM ingredientes WHERE cod_pizza = " + cod_pizza +"AND cod_produto ="+cod_produto;            
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())   
            {
                Ingrediente i = new Ingrediente();
                i.setId(rs.getInt("id"));
                i.setQuantidade(rs.getInt("quantidade"));
                i.setCod_pizza(rs.getInt("cod_pizza"));
                i.setCod_produto(rs.getInt("cod_produto"));
                return i;
                
            }
            
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        } 
        return null;
    }
    
    public Ingrediente getIngrediente2(int id) {

        try
        {
            String sql = "SELECT * FROM ingredientes WHERE id=" + id;            
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())   
            {
                Ingrediente i = new Ingrediente();
                i.setId(rs.getInt("id"));
                i.setQuantidade(rs.getInt("quantidade"));
                i.setCod_pizza(rs.getInt("cod_pizza"));
                i.setCod_produto(rs.getInt("cod_produto"));
                return i;
                
            }
            
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        } 
        return null;
    }
    
    public void getIngredienteQuantidade(int id, int qtd) {
            ProdutoDao pdao = new ProdutoDao();
        try
        {
            String sql = "select p.*, i.quantidade from produtos as p inner join ingredientes as i on p.id=i.cod_produto where i.cod_pizza=" + id;            
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())   
            {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setTipo(rs.getString("tipo"));
                p.setPreco(rs.getInt("preco"));
                p.setQtd((rs.getInt("qtd"))-(qtd*(rs.getInt("quantidade"))));
                pdao.update(p);
            }
            
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        } 
    }
}