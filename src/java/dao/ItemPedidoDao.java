package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.ItemPedido;
import model.Pedido;
import model.Produto;
import util.ConnectionFactory;

public class ItemPedidoDao {
    private Connection connection;

    public ItemPedidoDao() {
        try {
            this.connection = ConnectionFactory.getConnection();
        } catch (SQLException e) {
            System.err.println("Erro na conexão em ItemPedidoDao: "+e);
        }
    }
    
    public void create(Pedido pedido) {
        try {
            String sql;
            
            for (ItemPedido item : pedido.getItens()) {
                sql = "INSERT INTO pedido_itens (cod_produto, quantidade, cod_pedido) VALUES ("
                        +item.getProduto().getId()+", "
                        +item.getQuantidade()+", "
                        +pedido.getId()+")";
                
                PreparedStatement stmt = this.connection.prepareStatement(sql);
                stmt.executeUpdate();
                if(item.getProduto().getTipo().equals("Pizza"))
                {
                    IngredienteDao idao= new IngredienteDao();
                    idao.getIngredienteQuantidade(item.getProduto().getId(), item.getQuantidade());
                }
            }
        } catch(SQLException e) {
            System.err.println("Erro na inserção de itens: "+e);
        }
    }
    
    public void getItens(Pedido pedido) {
        try {
            String sql = "SELECT * FROM pedido_itens WHERE cod_pedido = "+pedido.getId();
            
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                ItemPedido item = new ItemPedido();
                
                item.setId(Integer.parseInt(rs.getString("id")));
                item.setQuantidade(Integer.parseInt(rs.getString("quantidade")));
                item.setSubtotal(Double.parseDouble(rs.getString("subtotal")));
                
                ProdutoDao produtodao = new ProdutoDao();
                Produto produto = produtodao.getProduto(Integer.parseInt(rs.getString("cod_produto")));
                item.setProduto(produto);
                
                item.setPedido(pedido.getId());
                
                pedido.addItem(item);
                
            }
            
        } catch (SQLException e) {
            System.err.println("Erro na listagem de itens de pedido: "+e);
        }
    }
}
