<%@page import="java.util.ArrayList"%>
<%@page import="model.Produto"%>
<%@page import="dao.ProdutoDao"%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<% 
    ProdutoDao novo = new ProdutoDao();
    ArrayList<Produto> pizza = (ArrayList<Produto>) request.getAttribute("pizza");
    ArrayList<Produto> ingredienteList = new ArrayList();
%>
<h1>Cardápio</h1>
<br>
<h1>Pizzas</h1>
<table class="table table-condensed">
    <thead>
        <tr>
            <th>Nome</th>
            <th>Preço</th>
            <th>Ingredientes</th>
            <c:if test="${user.getId() != null}">
                <th></th>
            </c:if>
        </tr>
    </thead>
    <tbody>
        <%
            for(Produto pizzas : pizza)
            { 
        %>
                <tr>
                    <td><%= pizzas.getNome() %></td>
                    <td><%="R$ " + pizzas.getPreco()%></td>
                    <td>
                        
                    
                
        <%
                ingredienteList=novo.listIngrediente(pizzas.getId());
                for(Produto ingredientes : ingredienteList)
                {
                    %>
                    <%= ingredientes.getNome() %><br>                        
                    <%
                }
                %>
                </td>
                    <c:if test="${user.getId() != null}">
                        <td>
                            <a href="CarrinhoController?action=add&produto=<%= pizzas.getId() %>">
                                <button type="button" class="btn btn-success btn-sm">
                                    <span class="glyphicon glyphicon-shopping-cart"></span> Comprar
                                </button>
                            </a>
                        </td>
                    </c:if>
                </tr>
                <%
            }
        %>
    </tbody>
</table>

<h1>Bebidas</h1>
<table class="table table-condensed">
    <thead>
        <tr>
            <th>Nome</th>
            <th>Preço</th>
            <c:if test="${user.getId() != null}">
                <th></th>
            </c:if>
        </tr>
    </thead>
    <tbody>

        <c:forEach items="${bebida}" var="bebida">
            <tr>
                <td><c:out value="${bebida.getNome()}" /></td>
                <td><c:out value="${bebida.getPreco()}" /></td>
                <c:if test="${user.getId() != null}">
                    <td>
                        <a href="CarrinhoController?action=add&produto=<c:out value="${bebida.getId()}" />">
                            <button type="button" class="btn btn-success btn-sm">
                                <span class="glyphicon glyphicon-shopping-cart"></span> Comprar
                            </button>
                        </a>
                    </td>
                </c:if>
            </tr>
        </c:forEach>

    </tbody>
</table>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">
            <span class="glyphicon glyphicon-shopping-cart"></span> Meu carrinho
        </h4>
      </div>
      <div class="modal-body">          
        <table class="table table-condensed">
            <thead>
                <tr>
                    <th>Item</th>
                    <th>Preço Unit.</th>
                    <th>Qtd.</th>                    
                    <th>Subtotal</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${carrinho.getItens()}" var="item">
                    <tr>
                        <td><c:out value="${item.getProduto().getNome()}"/></td>
                        <td>
                            <input type="hidden" id="produto<c:out value="${item.getProduto().getId()}"/>" value="<c:out value="${item.getProduto().getId()}"/>"/>
                            <input type="text" id="preco<c:out value="${item.getProduto().getId()}"/>" value="<c:out value="${item.getProduto().getPreco()}" />" disabled />
                        </td>
                        <td>
                            <input type="number" min="1" max="10" value="<c:out value="${item.getQuantidade()}"/>"  id="qtd<c:out value="${item.getProduto().getId()}"/>" />
                        </td>    
                        <td>
                            <c:out value="${item.getSubtotal()}" />
                        </td>
                        <td>
                            <a href="CarrinhoController?action=remove&produto=<c:out value="${item.getProduto().getId()}"/>">
                                <button type="button" class="btn btn-danger btn-xs">
                                    <span class="glyphicon glyphicon-remove"></span>
                                </button>
                            </a>
                        </td>
                    </tr>
                    
                    <script>
                        $('#qtd<c:out value="${item.getProduto().getId()}"/>').blur(function(){
                            document.location.href = 'CarrinhoController?action=update&produto='+${item.getProduto().getId()}+'&qtd='+$(this).val();
                        });
                        
                        $('#qtd<c:out value="${item.getProduto().getId()}"/>').change(function(){
                            document.location.href = 'CarrinhoController?action=update&produto='+${item.getProduto().getId()}+'&qtd='+$(this).val();
                        });
                    </script>
                </c:forEach>
                <tr class="active">
                    <th></th>
                    <th></th>
                    <th><strong>Total</strong></th>
                    <th><div><strong></strong></div></th>
                    <th></th>
                </tr>
            </tbody>
        </table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary pull-left" data-dismiss="modal">
            <span class="glyphicon glyphicon-arrow-left"></span> Continuar comprando
        </button>
        <a href="PedidoController?action=add">
            <button type="button" class="btn btn-success">
                Finalizar pedido <span class="glyphicon glyphicon-ok"></span>
            </button>
        </a>
        <a href="CarrinhoController?action=limpar">
            <button type="button" class="btn btn-danger">
                Cancelar pedido <span class="glyphicon glyphicon-remove"></span>
            </button>
        </a>
      </div>
    </div>
  </div>
</div>

<script>
    $(document).ready(function() {
        if (${modal} === true)
            $("#myModal").modal('show');
    });
</script>