package controller;

import dao.IngredienteDao;
import dao.ProdutoDao;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Carrinho;
import model.Produto;
import util.Message;

public class ProdutoController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Message message = Message.singleton();
            String action = request.getParameter("action");
            String id = request.getParameter("id");
            ProdutoDao produtodao = new ProdutoDao();
            IngredienteDao ingredao = new IngredienteDao();
            RequestDispatcher view = request.getRequestDispatcher("index.jsp");
            switch (action) {
                case "create":
                    request.setAttribute("page", "view/produto/create.jsp");
                    break;
                case "update":
                    request.setAttribute("produto", produtodao.getProduto(Integer.parseInt(id)));
                    request.setAttribute("page", "view/produto/edit.jsp");
                    break;
                case "list":
                    request.setAttribute("produto", produtodao.list());
                    request.setAttribute("page", "view/produto/list.jsp");
                    break;

                case "cardapio":
                    request.setAttribute("pizza", produtodao.listPizza());
                    request.setAttribute("bebida", produtodao.listBebida());
                    request.setAttribute("page", "cardapio.jsp");
                    
                    /*carrinho*/
                    HttpSession session = request.getSession(true);
                    Carrinho carrinho;
                    
                    if (carrinhoExists(session)) 
                        carrinho = (Carrinho) session.getAttribute("carrinho");
                    else carrinho = new Carrinho();
                    
                    if (request.getParameter("modal") != null) {
                        if ("true".equals(request.getParameter("modal")))
                            request.setAttribute("modal", true);
                    } else request.setAttribute("modal", false);                        
                    
                    session.setAttribute("carrinho", carrinho);
                    break;
            }
            request.setAttribute("message", message);
            view.forward(request, response);
        }
        catch (Exception ex) 
        {
            //Logger.getLogger(AuthenticateController.class.getName()).log(Level.SEVERE, null, ex); 
        }  
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try
        {
            Message message = Message.singleton();
            String action = request.getParameter("action");
            String id = request.getParameter("id");
            ProdutoDao produtodao = new ProdutoDao();
            RequestDispatcher view = request.getRequestDispatcher("index.jsp");
            switch(action)
            {
                case "create":
                case "update":

                    String nome = request.getParameter("nome");
                    String tipo = request.getParameter("tipo");
                    double preco = Double.parseDouble(request.getParameter ("preco"));
                    int qtd;
                    
                    if (tipo.equalsIgnoreCase("Pizza")) qtd = 0;
                    else qtd = Integer.parseInt(request.getParameter("qtd"));

                    Produto prooduto = new Produto();
                    prooduto.setNome(nome);
                    prooduto.setTipo(tipo);
                    prooduto.setPreco(preco);
                    prooduto.setQtd(qtd);

                    if(id == null)
                    {
                        produtodao.create(prooduto);
                        message.addMessage("Produto inserido com sucesso!");
                    }
                    else
                    {
                        prooduto.setId(Integer.parseInt(id));
                        produtodao.update(prooduto);
                        message.addMessage("Produto modificado com sucesso!");
                    }
                    request.setAttribute("message", message);
                    request.setAttribute("page", "view/produto/list.jsp");
                    request.setAttribute("produto", produtodao.list());
                    view.forward(request, response);
                break;
            }
        }
        catch (Exception ex) 
        {
            //Logger.getLogger(AuthenticateController.class.getName()).log(Level.SEVERE, null, ex); 
        }  
    }
    
    private boolean carrinhoExists(HttpSession session) {
        return session.getAttribute("carrinho") != null;
    }
}