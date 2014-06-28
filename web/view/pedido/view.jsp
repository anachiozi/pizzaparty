<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<h1>Visualizar Pedido</h1>
<small>
    <span class="glyphicon glyphicon-calendar"></span> <c:out value="${pedido.getData_hora()}" /> |
    <span class="glyphicon glyphicon-user"></span> <c:out value="${pedido.getUsuario().getNome()}" /> @ 
    <c:choose>
        <c:when test="${pedido.getMesa() == -1}">
            <c:out value="residencia"/>
        </c:when>
        <c:otherwise>
            <c:out value="mesa ${pedido.getMesa()}"/>
        </c:otherwise>
    </c:choose>
</small><br>


<h2>Itens</h2> 
<table class="table table-condensed">
    <thead>
        <tr class="active">
            <th>Produto</th>
            <th>Preço unit.</th>
            <th>Qtd.</th>
            <th>Subtotal</th>
        </tr>
    </thead>
    
    <tbody>
        <c:forEach items="${itens}" var="item">
            <tr>
                <td><c:out value="${item.getProduto().getNome()}" /></td>
                <td><c:out value="${item.getProduto().getPreco()}" /></td>
                <td><c:out value="${item.getQuantidade()}" /></td>
                <td><c:out value="${item.getSubtotal()}" /></td>
            </tr>
        </c:forEach>
        
        <tr class="active">
            <th></th>
            <th></th>
            <th><strong>Total</strong></th>
            <th><strong><c:out value="${pedido.getTotal()}" /></strong></th>
        </tr>
    </tbody>
</table>