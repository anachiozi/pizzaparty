<%@page import="model.Ingrediente"%>
<%@page import="dao.IngredienteDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Produto"%>
<%@page import="dao.ProdutoDao"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<% 
    ProdutoDao produtodao = new ProdutoDao();
    IngredienteDao  ingredientedao = new IngredienteDao();
    ArrayList<Produto> produto = (ArrayList<Produto>) request.getAttribute("produto");
    ArrayList<Produto> ingredienteList = new ArrayList();
    ArrayList<Produto> ingredisp = new ArrayList();
%>
<h1>Lista de Produtos</h1>
<br>
<a href="ProdutoController?action=create"><button type="button" class="btn btn-primary btn-xs"> Adicionar Produto</button></a>
<br>
<table class="table table-condensed">
    <thead>
        <tr>
            <th>Código</th>
            <th>Nome</th>
            <th>Tipo</th>
            <th>Quantidade</th>
            <th>Preço</th>
            <th>Cardapio</th>
            <th>Opções</th>  
        </tr>
    </thead>
    <tbody>
        <%
            for(Produto produtos : produto)
            { 
        %>
                <tr>
                    <td><%= produtos.getId()%></td>
                    <td><%= produtos.getNome()%></td>
                    <td><%= produtos.getTipo()%></td>
                    <td><%= produtos.getQtd()%></td>
                    <td><%="R$ " + produtos.getPreco()%></td>
                    <td><%= produtos.isCardapio()%></td>
                    <td>
                        <%
                            if(!produtos.getTipo().equals("Pizza"))
                            {%>
                                <a href="ProdutoController?action=update&id=<%= produtos.getId()%>"><button type="button" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-pencil"></span></button></a>
                            <%}
                            else 
                            {
                                Ingrediente ingrediente;
                                ingredienteList=produtodao.listIngrediente(produtos.getId());
                        %>
                                <!-- Button trigger modal -->
                                <button class="btn btn-primary btn-xs" data-toggle="modal" data-target="#myModal<%= produtos.getId()%>"><span class="glyphicon glyphicon-search"></span></button>   
                                <!-- Modal -->
                                <div class="modal fade" id="myModal<%= produtos.getId()%>" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                                <center><h1 class="modal-title" id="myModalLabel"><%= produtos.getNome()%></h1></center>
                                            </div>
                                            <div class="modal-body">
                                                <table class="table table-condensed">
                                                    <thead>
                                                        <tr>
                                                            <th>Ingrediente</th>
                                                            <th>Quantidade</th>
                                                            <th></th>
                                                        </tr>
                                                    </thead>
                                                    
                                                    <tbody>
                                                        <%
                                                            ingredisp=produtodao.listIngrediente2(produtos.getId());
                                                                
                                                            for (Produto ingredientes : ingredienteList) 
                                                            {
                                                                ingrediente=ingredientedao.getIngrediente(produtos.getId(), ingredientes.getId());
                                                            %>
                                                            <tr>
                                                               
                                                                    <td><%= ingredientes.getNome()%></td>
                                                                    <td>
                                                                        <input type="number" id="qtd<%= ingrediente.getId()%>" name="qtd<%= ingrediente.getId()%>" min="1" value="<%=ingrediente.getQuantidade()%>"
                                                                    </td>
                                                                    <td>
                                                                        <a href="IngredienteController?action=delete&id=<%= ingrediente.getId()%>"><button type="button" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove"></span></button></a>
                                                                    </td>
                                                                    <script>

                                                                        $('#qtd<%= ingrediente.getId()%>').blur(function(){
                                                                            document.location.href = 'IngredienteController?action=update&id=<%= ingrediente.getId()%>&qtd='+$(this).val();      
                                                                        });
                                                                    </script>
                                                                    <script>

                                                                        $('#qtd<%= ingrediente.getId()%>').change(function(){
                                                                            document.location.href = 'IngredienteController?action=update&id=<%= ingrediente.getId()%>&qtd='+$(this).val();      
                                                                        });
                                                                    </script>
                                                            </tr>                       
                                                            <script>
                                                                $(document).ready(function() {
                                                                    if (${modal} !== null)
                                                                        $("#myModal${modal}").modal('show');
                                                                });
                                                            </script>
                                                        <%
                                                            }
                                                        %>
                                                    </tbody>
                                                </table>
                                                        <fieldset class="scheduler-border">
                                                            <legend class="scheduler-border">Adicionar Ingrediente</legend>
                                                            <form  class="form-inline" role="form<%= produtos.getNome()%>">
                                                                <div class="form-group">              
                                                                    <label for="cod_produto<%= produtos.getId()%>">Nome</label>
                                                                    <select name="cod_produto<%= produtos.getId()%>" id="cod_produto<%= produtos.getId()%>">
                                                                        <%
                                                                            for (Produto i : ingredisp) {
                                                                        %>
                                                                        <option value="<%= i.getId()%>"><%= i.getNome()%></option>
                                                                        <%
                                                                            }
                                                                        %>
                                                                    </select>
                                                                    <label for="qtd<%= produtos.getNome()%>">Quantidade</label>
                                                                    <input type="number" name="qtd<%= produtos.getNome()%>"  class="form-control" id="qtd<%= produtos.getId()%>" min="1" placeholder="Quantidade">
                                                                </div>
                                                                <button id="btn<%= produtos.getId()%>" type="button" class="btn btn-success btn-xs"><span class="glyphicon glyphicon-ok"></span></button>
                                                                <script>
                                                        $('#btn<%= produtos.getId()%>').click(function(){
                                                            var qtd = String ($('#qtd<%= produtos.getId()%>').val());
                                                            var cod_produto = String ($('#cod_produto<%= produtos.getId()%>').val());
                                                            document.location.href = 'IngredienteController?action=create&cod_pizza=<%= produtos.getId()%>&qtd='+qtd+'&cod_produto='+cod_produto;
                                                        });
                                                                </script>
                                                            </form>
                                                        </fieldset>

                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-primary" data-dismiss="modal">Fechar <span class="glyphicon glyphicon-remove"></span></button>
                                            </div>
                                        </div>
                                    </div>
                                </div>                     
                        <%
                            }
                        %>
                    
                    </td>
                </tr>
        <%
            }
        %>
    </tbody>
</table>
    