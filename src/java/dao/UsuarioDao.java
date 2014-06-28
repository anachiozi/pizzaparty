package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Usuario;
import util.ConnectionFactory;
import util.Md5;

public class UsuarioDao {

    private Connection connection;
    
    public UsuarioDao(){    
        
        try
        {
            this.connection =  ConnectionFactory.getConnection() ;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void create(Usuario usuario) {

        try
        {
           String sql = "INSERT INTO users (nome, cpf, endereco, telefone, username, password) VALUES ('"+usuario.getNome()+"', '"+usuario.getCpf()+"', '"+usuario.getEndereco()+"' , '"+usuario.getTelefone()+"', '"+usuario.getUsername()+"', '"+usuario.getPassword()+"')";
           PreparedStatement stmt = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
           stmt.executeUpdate(); 
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        } 
        
    }
    
        public ArrayList<Usuario> list() {

        try
        {
            String sql = "SELECT * FROM users";
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            ArrayList<Usuario> usr = new ArrayList();
            
            while(rs.next())   
            {
                
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setEndereco(rs.getString("endereco"));
                usuario.setTelefone(rs.getString("telefone"));
                usuario.setTipo(rs.getBoolean("tipo"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                usr.add(usuario);
            }
            
            return usr;
        }
        
        catch (SQLException e)
        {
           e.printStackTrace();
        } 
        return null;
        
    }
    
    public void updateUser(Usuario usuario)
    {
        try
        {
           String sql = "UPDATE users SET nome = '"+usuario.getNome()+"', endereco = '"+usuario.getEndereco()+"' , telefone= '"+usuario.getTelefone()+"' WHERE id = " + usuario.getId();
           PreparedStatement stmt = this.connection.prepareStatement(sql);
           stmt.executeUpdate();
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        } 
    }
    
    public Usuario getUsuario(int id) {

        try
        {
            String sql = "SELECT * FROM users WHERE id = " + id;            
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            if(rs.next())   
            {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setEndereco(rs.getString("endereco"));
                usuario.setTelefone(rs.getString("telefone"));
                usuario.setTipo(rs.getBoolean("tipo"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                
                return usuario;
            }
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        } 
        
        return null;
    }
    
    public Usuario authenticateUser(String login, String password) {

        try
        {
            String sql = "SELECT * FROM users WHERE username = '" + login + "' AND password = '" + Md5.encrypt(password) + "'" ;
            
            Statement stmt = this.connection.createStatement();
            
            ResultSet rs = stmt.executeQuery(sql);
            
            if(rs.next())   
            {
                Usuario user = new Usuario();
                
                user.setId(rs.getInt("id"));
                
                user.setUsername(rs.getString("username"));
                
                user.setPassword(rs.getString("password"));
                
                user.setNome(rs.getString("nome"));

                user.setCpf(rs.getString("cpf"));
                
                user.setEndereco(rs.getString("endereco"));
                
                user.setTelefone(rs.getString("telefone"));
                
                if (rs.getString("tipo").equalsIgnoreCase("t")) user.setTipo(true);
                else user.setTipo(false);
                
                return user;
            }
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        } 
        
        return null;
    }
    
    public Boolean authenticateKey(String oldkey) {

        try
        {
            String sql = "SELECT chave FROM chaves WHERE chave = '" + oldkey + "'" ;
            
            Statement stmt = this.connection.createStatement();
            
            ResultSet rs = stmt.executeQuery(sql);
            
            if(rs.next())   
            {
                return true;
            }
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        } 
        
        return false;
    }
    
    
    public void updateKey(String newkey)
    {
        try
        {
            String sql = "UPDATE chaves SET chave = '" + newkey + "'";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        } 
    }
        
    public void activate_desactivate(int id, boolean action)
    {
        try
        {
            String sql;
            if(action == false)
                sql = "UPDATE users SET tipo = 'true' WHERE id = "+ id;
            else
                sql = "UPDATE users SET tipo = 'false' WHERE id = "+ id;
            
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
           e.printStackTrace();
        } 
    }
    
}