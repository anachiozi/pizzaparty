<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<h1>Lista de Usuário</h1>


<table class="table table-condensed">
    <thead>
        <tr>
            <th>Nome</th>
            <th>Login</th>
            <th>Tipo</th>
            <th>Opções</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${usr}" var="usr">
            <tr>
                <td><c:out value="${usr.getNome()}" /></td>
                <td><c:out value="${usr.getUsername()}" /></td>
                <td>
                    <c:if test="${usr.getTipo() == true}"> 
                        Funcionário
                    </c:if>
                    <c:if test="${usr.getTipo() == false}"> 
                        Cliente
                    </c:if>    
                </td>
                <td>
                    <c:if test="${usr.getTipo() == true}"> 
                        <button class="btn btn-danger btn-sm" data-toggle="modal" data-target="#myModal<c:out value="${usr.getId()}" />"><span class="glyphicon glyphicon-remove"></span></button>   
                    </c:if>
                    <c:if test="${usr.getTipo() == false}"> 
                        <button class="btn btn-success btn-sm" data-toggle="modal" data-target="#myModal<c:out value="${usr.getId()}" />"><span class="glyphicon glyphicon-ok"></span></button>   
                    </c:if> 
                    
                </td>
            </tr>
            
            
            
            
           
            
            <form role="form<c:out value="${usr.getId()}" />" action="UsuarioController?action=activate&id=<c:out value="${usr.getId()}" />&tipo=<c:out value="${usr.getTipo()}" />" method="POST">
                <div class="modal fade" id="myModal<c:out value="${usr.getId()}" />" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                <c:if test="${usr.getTipo() == true}"> 
                                    <center><h1 class="modal-title" id="myModalLabel">Desativar <c:out value="${usr.getNome()}" /></h1></center>
                                </c:if>
                                <c:if test="${usr.getTipo() == false}"> 
                                    <center><h1 class="modal-title" id="myModalLabel">Ativar <c:out value="${usr.getNome()}" /></h1></center>
                                </c:if> 
                                
                            </div>
                            <div class="modal-body">
                                Digita a chave:

                                    <input type="password" name="chave"  class="form-control" id="chave" placeholder="Entre com a chave">

                            </div>
                            <div class="modal-footer">

                                <c:if test="${usr.getTipo() == true}"> 
                                    <center><button type="submit" class="btn btn-danger">Desativar</button></center>
                                </c:if>
                                <c:if test="${usr.getTipo() == false}"> 
                                    <center><button type="submit" class="btn btn-success">Ativar</button></center>
                                </c:if> 
                                
                            </div>
                        </div>
                    </div>
                </div>
            </form>
           
    
        </c:forEach>
    
    </tbody>
</table>
