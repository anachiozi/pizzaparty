package controller;

import dao.ProdutoDao;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Carrinho;
import model.ItemPedido;
import model.Produto;
import util.Message;

public class CarrinhoController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String action = request.getParameter("action");
            HttpSession session = request.getSession(true);
            Carrinho carrinho;
            
            if (session.getAttribute("user") != null) {
                switch (action) {
                    case "add":
                        carrinho = (Carrinho) session.getAttribute("carrinho");
                        int produto_id = Integer.parseInt(request.getParameter("produto"));

                        //vê se já não tá na lista
                        int qtd = carrinho.buscaProduto(produto_id);
                        if (qtd != -1)
                            carrinho.updateQtd(produto_id, qtd+1);
                        else {
                            //pega produto
                            ProdutoDao produtodao = new ProdutoDao();
                            Produto produto = produtodao.getProduto(produto_id);

                            //seta no item
                            ItemPedido item = new ItemPedido();
                            item.setProduto(produto);
                            item.setQuantidade(1);
                            item.calculaSubtotal();

                            //adiciona no carrinho
                            carrinho.addItem(item);
                            carrinho.setTotal(carrinho.getTotal() + item.getSubtotal());

                            //disponibiliza pra sessão
                            session.setAttribute("carrinho", carrinho);
                        }
                        response.sendRedirect("ProdutoController?action=cardapio&modal=true");
                    break;

                    case "limpar":        
                        carrinho = (Carrinho) session.getAttribute("carrinho");
                        carrinho.clear();
                        session.setAttribute("carrinho", carrinho);
                        response.sendRedirect("ProdutoController?action=cardapio");
                    break; 

                    case "update":
                        carrinho = (Carrinho) session.getAttribute("carrinho");
                        carrinho.updateQtd(Integer.parseInt(request.getParameter("produto")), Integer.parseInt(request.getParameter("qtd")));
                        session.setAttribute("carrinho", carrinho);
                        response.sendRedirect("ProdutoController?action=cardapio&modal=true");
                    break;

                    case "remove":
                        carrinho = (Carrinho) session.getAttribute("carrinho");
                        carrinho.removeItem(Integer.parseInt(request.getParameter("produto")));
                        session.setAttribute("carrinho", carrinho);
                        response.sendRedirect("ProdutoController?action=cardapio&modal=true");
                    break;    
                }
            } else {
                Message message = Message.singleton();
                message.addWarning("Você não tem permissão para acessar essa página.");
                request.setAttribute("message", message);
                request.setAttribute("page", "view/usuario/login.jsp");
                RequestDispatcher view = request.getRequestDispatcher("index.jsp");
                view.forward(request, response);
            }
                    
        } catch (NumberFormatException e) {
            System.err.println("Erro na requisição POST em CarrinhoController: "+e);
        }        
    }
    
    private boolean carrinhoExists(HttpSession session) {
        return session.getAttribute("carrinho") != null;
    }
}
