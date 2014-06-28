package controller;

import dao.PedidoDao;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Carrinho;
import model.Pedido;
import model.Usuario;
import util.Message;

public class PedidoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            
            String action = request.getParameter("action");            
            PedidoDao pedidodao = new PedidoDao();       
            RequestDispatcher view = request.getRequestDispatcher("index.jsp");
            HttpSession session = request.getSession(true);
            Pedido pedido;
            
            if (session.getAttribute("user") != null) {
                switch (action) {
                    case "list":
                        Usuario usuario = (Usuario) session.getAttribute("user");
                        
                        if (usuario.getTipo()) {
                            request.setAttribute("page", "view/pedido/list.jsp");
                            request.setAttribute("pedidos", pedidodao.list(-1));
                        } else {
                            Message message = Message.singleton();
                            message.addWarning("Você não tem permissão para acessar essa página.");
                            request.setAttribute("message", message);
                            request.setAttribute("page", "index.jsp");
                        }
                    break;

                    case "historico":
                        int usuario_id = ((Usuario) session.getAttribute("user")).getId();
                        request.setAttribute("page", "view/pedido/historico.jsp");
                        request.setAttribute("pedidos", pedidodao.list(usuario_id));
                    break;

                    case "view":
                        request.setAttribute("page", "view/pedido/view.jsp");

                        pedido = pedidodao.getPedido((request.getParameter("id")));
                        request.setAttribute("pedido", pedido);
                        request.setAttribute("itens", pedido.getItens());
                    break;

                    case "add":
                        request.setAttribute("page", "view/pedido/add.jsp");
                    break;    
                }
            } else {
                Message message = Message.singleton();
                message.addWarning("Você não tem permissão para acessar essa página.");
                request.setAttribute("message", message);
                request.setAttribute("page", "view/usuario/login.jsp");
            }
            
            view.forward(request, response);
            
        } catch (ServletException | IOException e) {
            System.out.println("Erro na requisição GET em PedidoController: "+e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            
            String action = request.getParameter("action");
            HttpSession session = request.getSession(true);
            PedidoDao pedidodao = new PedidoDao();
            
            if (session.getAttribute("user") != null) {
                switch (action) {
                    case "add":
                        Usuario usuario = (Usuario) session.getAttribute("user");
                        Pedido pedido = new Pedido();
                        pedido.setUsuario(usuario);
                        pedido.setTipo_interacao(request.getParameter("interacao"));

                        /*se delivery, pagamento = cartão e mesa = null*/
                        if (pedido.getTipo_interacao().equalsIgnoreCase("RM")) {
                            pedido.setForma_pag(false);
                            pedido.setMesa(-1);
                        } else {
                            pedido.setForma_pag(Boolean.parseBoolean(request.getParameter("pagamento")));
                            pedido.setMesa(Integer.parseInt(request.getParameter("mesa")));
                        }

                        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
                        pedido.setItens(carrinho.getItens());

                        if (pedidodao.create(pedido)) {
                            response.sendRedirect("PedidoController?action=historico");
                            session.setAttribute("carrinho", new Carrinho());
                        } else {
                            Message message = Message.singleton();
                            message.addWarning("Erro: não foi possível inserir seu pedido no sistema. Por favor, tente novamente.");
                            request.setAttribute("message", message);
                            request.setAttribute("page", "view/produto/cardapio.jsp");
                        }
                    break;
                }
            } else {
                Message message = Message.singleton();
                message.addWarning("Você não tem permissão para acessar essa página.");
                request.setAttribute("message", message);
                request.setAttribute("page", "view/usuario/login.jsp");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro na requisição POST em PedidoController: "+e);
        }
    }
}
