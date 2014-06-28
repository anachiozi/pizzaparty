package controller;

import dao.IngredienteDao;
import dao.ProdutoDao;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Ingrediente;
import util.Message;

public class IngredienteController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try
        {
            Message message = Message.singleton();
            String action = request.getParameter("action");
            String id = request.getParameter("id");
            IngredienteDao ingredientedao = new IngredienteDao();
            ProdutoDao pdao = new ProdutoDao();
            RequestDispatcher view = request.getRequestDispatcher("index.jsp");
            Ingrediente i = new Ingrediente();
            switch(action)
            {
                case "create": 
                    int qtd = Integer.parseInt(request.getParameter("qtd"));
                    int cpz = Integer.parseInt(request.getParameter("cod_pizza"));
                    int cpd = Integer.parseInt(request.getParameter("cod_produto"));
                    i.setQuantidade(qtd);
                    i.setCod_pizza(cpz);
                    i.setCod_produto(cpd);
                    ingredientedao.create(i);
                    request.setAttribute("produto", pdao.list());
                    request.setAttribute("modal", cpz);
                    request.setAttribute("page", "view/produto/list.jsp");
                break; 
                case "update": 
                    i=ingredientedao.getIngrediente2(Integer.parseInt(id));
                    i.setQuantidade(Integer.parseInt(request.getParameter("qtd")));
                    ingredientedao.update(i);
                    request.setAttribute("produto", pdao.list());
                    request.setAttribute("modal", i.getCod_pizza());
                    request.setAttribute("page", "view/produto/list.jsp");
                break; 
                case "delete": 
                    int pizza = ingredientedao.getIngrediente2(Integer.parseInt(id)).getCod_pizza();
                    ingredientedao.delete(Integer.parseInt(id));
                    request.setAttribute("produto", pdao.list());
                    request.setAttribute("modal", pizza);
                    request.setAttribute("page", "view/produto/list.jsp");
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
}