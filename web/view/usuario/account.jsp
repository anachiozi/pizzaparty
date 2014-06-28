<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<h1>Informações do Usuário</h1>


<table class="table table-condensed">
    <thead>
        <tr>
            <th>Nome</th>
            <th>CPF</th>
            <th>Endereço</th>
            <th>Telefone</th>  
            <th></th>
        </tr>
    </thead>
    <tbody>
            <tr>
                <td><c:out value="${user.getNome()}" /></td>
                <td><c:out value="${user.getCpf()}" /></td>
                <td><c:out value="${user.getEndereco()}" /></td>
                <td><c:out value="${user.getTelefone()}" /></td>
                <td><a href="UsuarioController?action=update&id=<c:out value="${user.getId()}" />"><button type="button" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-pencil"></span></button></a></td>
            </tr>
    </tbody>
</table>
