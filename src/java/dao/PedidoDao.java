package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Pedido;
import model.Usuario;
import util.ConnectionFactory;

public class PedidoDao {
    private Connection connection;

    public PedidoDao() {
        try {
            this.connection = ConnectionFactory.getConnection();
        }
        catch (SQLException e) {
            System.err.println("Erro ao estabelecer conexão: "+e);
        }
    }
    
    public boolean create(Pedido pedido) {
        try {
            String sql;
            
            if (pedido.getMesa() == -1)
                 sql = "INSERT INTO pedidos (cod_usuario, tipo_interacao, forma_pag) VALUES "
                         + "("+pedido.getUsuario().getId()+", "
                         + "'"+pedido.getTipo_interacao()+"', "
                         + ""+pedido.getForma_pag()+")";
            else {
                sql = "INSERT INTO pedidos (mesa, cod_usuario, tipo_interacao, forma_pag) VALUES "
                        + "("+pedido.getMesa()+", "
                        + ""+pedido.getUsuario().getId()+", "
                        + "'"+pedido.getTipo_interacao()+"', "
                        + ""+pedido.getForma_pag()+")";
            }
            
            PreparedStatement stmt = this.connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            
            if (rs.next()) {
                pedido.setId(rs.getInt("id"));
                ItemPedidoDao itempedidodao = new ItemPedidoDao();
                itempedidodao.create(pedido);
            } else {
                System.err.println("Erro na inserção de itens: ID do Pedido não encontrada.");
                return false;
            }
            
            return true;
        } catch (SQLException e) {
            System.err.println("Erro na inserção de novo pedido: "+e);
            return false;
        }
    }
    
    public ArrayList<Pedido> list (int usuario) {
        try {
            String sql;
            
            if (usuario == -1) 
                sql = "SELECT * from pedidos ORDER BY data_hora DESC";
            else 
                sql = "SELECT * from pedidos WHERE cod_usuario = "+usuario+" ORDER BY data_hora DESC";
                        
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            ArrayList<Pedido> pedidos = new ArrayList();
            UsuarioDao usuariodao = new UsuarioDao();
            
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(Integer.parseInt(rs.getString("id")));
                pedido.setData_hora(rs.getString("data_hora"));
                
                if (rs.getString("mesa") == null) pedido.setMesa(-1);
                else pedido.setMesa(rs.getInt("mesa"));
                
                Usuario user = usuariodao.getUsuario(Integer.parseInt(rs.getString("cod_usuario")));
                pedido.setUsuario(user);
                
                pedido.setTipo_interacao(rs.getString("tipo_interacao"));
                pedido.setForma_pag(rs.getBoolean("forma_pag"));
                pedido.setTotal(Double.parseDouble(rs.getString("total")));
                
                pedidos.add(pedido);
            }
            
            return pedidos;
        } catch (SQLException e) {
            System.err.println("Erro na listagem de Pedidos: "+e);
            return null;
        }
    }
    
    public Pedido getPedido(String id) {
        try {
            String sql = "SELECT * from pedidos WHERE id = "+id;

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(Integer.parseInt(rs.getString("id")));
                pedido.setData_hora(rs.getString("data_hora"));
                
                if (rs.getString("mesa") == null) pedido.setMesa(-1);
                else pedido.setMesa(rs.getInt("mesa"));
                
                UsuarioDao usuariodao = new UsuarioDao();
                Usuario user = usuariodao.getUsuario(Integer.parseInt(rs.getString("cod_usuario")));
                pedido.setUsuario(user);                
                
                pedido.setTipo_interacao(rs.getString("tipo_interacao"));
                pedido.setForma_pag(rs.getBoolean("forma_pag"));
                
                ItemPedidoDao itempedidodao = new ItemPedidoDao();
                itempedidodao.getItens(pedido);
                
                //pedido.setTotal(Double.parseDouble(rs.getString("total")));
                
                return pedido;
            }
            
            return null;
        } catch (SQLException e) {
            System.err.println("Erro na visualização do Pedido: "+e);
            return null;
        }
    }
    
    public boolean delete(String id) {
        try {
            String sql = "DELETE FROM pedidos WHERE id = "+id;
            
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            System.err.println("Erro na deleção de Pedido: "+e);
            return false;
        }
    }
}
