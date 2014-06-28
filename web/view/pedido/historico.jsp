<%@page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<h1>Histórico de Pedidos</h1>

<table class="table table-condensed">
    <thead>
        <tr class="active">
            <th>Data</th>
            <th>Valor total</th>
            <th>Tipo de interação</th>
            <th>Forma de pagamento</th>
        </tr>
    </thead>
    
    <tbody>
        <c:forEach items="${pedidos}" var="pedido">
            <tr>
                <td><a href="PedidoController?action=view&id=<c:out value="${pedido.getId()}" />"><c:out value="${pedido.getData_hora()}" /></a></td>
                <td><c:out value="${pedido.getTotal()}" /></td>
                <td><c:out value="${pedido.getInteracao()}" /></td>
                <td><c:out value="${pedido.getPag()}" /></td>
            </tr>
        </c:forEach>
    </tbody>
</table>