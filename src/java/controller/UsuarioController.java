package controller;

import dao.UsuarioDao;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Usuario;
import util.Md5;
import util.Message;

public class UsuarioController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try
        {
            Message message = Message.singleton();
            String action = request.getParameter("action");
            String id = request.getParameter("id");
            String usuario = request.getParameter("usuario");
            UsuarioDao usuariodao = new UsuarioDao();
            HttpSession session = request.getSession(true);
            RequestDispatcher view = request.getRequestDispatcher("index.jsp");
            switch(action)
            {
                case "create": 
                    request.setAttribute("page", "view/usuario/create.jsp");
                break; 
                    
                case "update": 
                    request.setAttribute("usuario", usuariodao.getUsuario(Integer.parseInt(id)));
                    request.setAttribute("page", "view/usuario/edit.jsp");
                break; 
                    
                case "list":
                    request.setAttribute("usr", usuariodao.list());
                    request.setAttribute("page", "view/usuario/list.jsp");
                    
                break;
                    
                case "account":
                    request.setAttribute("id", id);
                    request.setAttribute("usuario", usuario);
                    session.setAttribute("user", (Usuario)session.getAttribute("user"));
                    request.setAttribute("page", "view/usuario/account.jsp");
                break;    
                    
                case "login": 
                    request.setAttribute("page", "view/usuario/login.jsp");
                break; 
                    
                case "key": 
                    request.setAttribute("page", "view/usuario/key.jsp");
                break;
                case "logout":
                    session = request.getSession(false);
                    session.setAttribute("user", null);
                    request.setAttribute("user", null);
                break; 
                    
            }
            request.setAttribute("message", message);
            view.forward(request, response);
        }
        catch (Exception ex) 
        {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex); 
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
            UsuarioDao usuariodao = new UsuarioDao();
            HttpSession session = request.getSession(true);
            boolean check;
            
            RequestDispatcher view = request.getRequestDispatcher("index.jsp");
            switch(action)
            {
                case "create":
                case "update":

                    String nome = request.getParameter("nome");
                    String cpf = request.getParameter("cpf");
                    String endereco = request.getParameter("endereco");
                    String telefone = request.getParameter("telefone");
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");

                    Usuario usuario = new Usuario();
                    usuario.setNome(nome);
                    usuario.setEndereco(endereco);
                    usuario.setTelefone(telefone);

                    if(id == null)
                    {
                        usuario.setCpf(cpf);
                        usuario.setUsername(username);
                        usuario.setPassword(Md5.encrypt(password));
                        usuariodao.create(usuario);
                        request.setAttribute("page", "home.jsp");
                        message.addMessage("Usuário inserido com sucesso!");
                    }
                    else
                    {
                        usuario.setId(Integer.parseInt(id));
                        usuario.setCpf(((Usuario)session.getAttribute("user")).getCpf());
                        usuario.setTipo(((Usuario)session.getAttribute("user")).getTipo());
                        usuario.setUsername(((Usuario)session.getAttribute("user")).getUsername());
                        usuario.setPassword(((Usuario)session.getAttribute("user")).getPassword());
                        usuariodao.updateUser(usuario);
                        session.setAttribute("user", usuario);
                        request.setAttribute("page", "view/usuario/account.jsp");
                        message.addMessage("Usuário modificado com sucesso!");
                    }
                break;
                    
                case "login":
                       
                    Usuario user = new Usuario();
                    String login = request.getParameter("username");
                    String senha = request.getParameter("password");
                    
                    user = usuariodao.authenticateUser(login, senha);
              
                    if (user == null) {
                        
                        request.setAttribute("page", "view/usuario/login.jsp");

                        message.addWarning("Usuário ou senha incorreto!");
                    }
                    else
                    {
                        
                        request.setAttribute("page", "home.jsp");

                        session.setAttribute("user", user);
                        
                        request.setAttribute("user", user);
                        
                    }
                    
                break;
                    
                case "key":
                    String oldkey = request.getParameter("oldkey");
                    String newkey = request.getParameter("newkey");
                    
                    check = usuariodao.authenticateKey(oldkey);
                    usuariodao.updateKey(newkey);
                    
                    
                    if (check==true)
                    {
                        request.setAttribute("page", "home.jsp");
                        message.addMessage("Chave alterada com sucesso!");
                    }
                    else
                    {
                        request.setAttribute("page", "view/usuario/key.jsp");
                        message.addWarning("Chave atual incorreta!");
                    }
                break;
                
                case "activate":
                    
                    String tipo = request.getParameter("tipo");
                    check = usuariodao.authenticateKey(request.getParameter("chave"));

                    if (check==true)
                    {
                        if("false".equals(tipo)){
                            usuariodao.activate_desactivate(Integer.parseInt(id), false);
                            message.addMessage("Usuário ativado com sucesso!");
                        }
                        else{
                            usuariodao.activate_desactivate(Integer.parseInt(id), true);    
                            message.addMessage("Usuário desativado com sucesso!");
                        }
                    }
                    else
                    {
                        
                        message.addWarning("Chave incorreta!");
                    }
                    
                    request.setAttribute("usr", usuariodao.list());
                    request.setAttribute("page", "view/usuario/list.jsp");
                break;
            }
            
            request.setAttribute("message", message);

            view.forward(request, response);
           
        }
        catch (Exception ex) 
        {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex); 
        }    
    }
}
