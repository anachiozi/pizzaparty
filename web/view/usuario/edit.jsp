<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.Usuario"%>

<%
    Usuario usuario = new Usuario();
    usuario = (Usuario) request.getAttribute("usuario");
%>

<h1>Editar Usu�rio</h1>

<form role="form" action="UsuarioController?action=update&id=<%= usuario.getId()%>" method="POST">
    <div class="form-group">
        <label for="nome">Nome</label>
        <input type="text" name="nome"  class="form-control" id="nome" placeholder="Entre com o nome" value="<%= usuario.getNome()%>">
    </div>

    <div class="form-group">
        <label for="endereco">Endere�o</label>
        <input type="text" name="endereco"  class="form-control" id="endereco" placeholder="Entre com o endere�o" value="<%= usuario.getEndereco()%>">
    </div>

    <div class="form-group">
        <label for="telefone">Telefone</label>
        <input type="tel" name="telefone"  class="form-control" id="telefone" placeholder="Entre com o telefone" value="<%= usuario.getTelefone()%>">
    </div>

    <button type="submit" class="btn btn-default">Editar</button>
</form>