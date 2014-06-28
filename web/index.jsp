<%@page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Pizza Party</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="bootstrap/css/custom.css" rel="stylesheet">
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
        <script src="bootstrap/js/bootstrap.min.js"></script>
    </head>
    <body>
        <!--barra de navegação-->
        <nav class="navbar navbar-default" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="PageController?action=home">Pizza Party</a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="ProdutoController?action=cardapio">Cardápio</a></li>
                        <li><a href="PageController?action=sobre">Sobre</a></li>
                    </ul>
                    
                    <ul class="nav navbar-nav navbar-right">
                        <!-- Carrinho -->
                        <c:if test="${user.getId() != null && carrinho.isEmpty() == false}">
                            <li>
                                <button type="button" class="btn btn-success navbar-btn" data-toggle="modal" data-target="#myModal">
                                    <span class="glyphicon glyphicon-shopping-cart"></span> Carrinho
                                </button>
                            </li>
                        </c:if>
                            
                        <c:if test="${user.getId() != null}">
                            <li>    
                                <a href="#" class="dropdown-toggle"><c:out value="${user.getNome()}" /> </a> 
                            </li>
                        </c:if>    
                        
                        <!-- Gerenciamento -->
                        <c:if test="${user.getId() != null && user.getTipo() == true}">
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Gerenciamento <b class="caret"></b></a>
                                <ul class="dropdown-menu">
                                    <li><a href="UsuarioController?action=list">Usuários</a></li>
                                    <li><a href="PedidoController?action=list">Pedidos</a></li>
                                    <li><a href="ProdutoController?action=list">Produtos</a></li>
                                    <li><a href="UsuarioController?action=key">Chave</a></li>
                                </ul>
                            </li>
                        </c:if>
                            
                        <!-- Conta -->
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Minha Conta <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <c:if test="${user.getId() == null}">
                                    <li><a href="UsuarioController?action=login">Entrar</a></li>
                                    <li><a href="UsuarioController?action=create">Cadastre-se</a></li>
                                </c:if> 
                                <c:if test="${user.getId() != null}">
                                    <li><a href="UsuarioController?action=account&id=<c:out value="${user.getId()}"/>">Dados pessoais</a></li>
                                    <li><a href="PedidoController?action=historico">Meus Pedidos</a></li>
                                    <li class="divider"></li>
                                    <li><a href="UsuarioController?action=logout">Sair</a></li>
                                </c:if>
                            </ul>
                        </li>
                    </ul>
                    
                    <c:if test="${user.getId() != null && carrinho.isEmpty() == false}">
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
                                                        <input type="hidden" id="produto<c:out value="${item.getProduto().getId()}"/>" name="produto<c:out value="${item.getProduto().getId()}"/>" value="<c:out value="${item.getProduto().getId()}"/>"/>
                                                        <input type="text" id="preco<c:out value="${item.getProduto().getId()}"/>" name="preco<c:out value="${item.getProduto().getId()}"/>" value="<c:out value="${item.getProduto().getPreco()}" />" disabled />
                                                    </td>
                                                    <td>
                                                        <input type="number" min="1" max="10" value="<c:out value="${item.getQuantidade()}"/>" name="qtd<c:out value="${item.getProduto().getId()}"/>" id="qtd<c:out value="${item.getProduto().getId()}"/>" />
                                                    </td>    
                                                    <td>
                                                        <div id="subtotal<c:out value="${item.getProduto().getId()}"/>">
                                                            <c:out value="${item.getSubtotal()}" />
                                                        </div>
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
                                                        var qtd = Number ($(this).val());
                                                        var preco = Number ($('#preco<c:out value="${item.getProduto().getId()}"/>').val());
                                                        var subtotal = preco*qtd;
                                                        $('#subtotal<c:out value="${item.getProduto().getId()}"/>').text(subtotal);

                                                        document.location.href = 'CarrinhoController?action=update&produto='+${item.getProduto().getId()}+'&qtd='+$(this).val();
                                                    });

                                                    $('#qtd<c:out value="${item.getProduto().getId()}"/>').change(function(){
                                                        var qtd = Number ($(this).val());
                                                        var preco = Number ($('#preco<c:out value="${item.getProduto().getId()}"/>').val());
                                                        var subtotal = preco*qtd;
                                                        $('#subtotal<c:out value="${item.getProduto().getId()}"/>').text(subtotal);

                                                        document.location.href = 'CarrinhoController?action=update&produto='+${item.getProduto().getId()}+'&qtd='+$(this).val();
                                                    });
                                                </script>
                                            </c:forEach>
                                            <tr class="active">
                                                <th></th>
                                                <th></th>
                                                <th><strong>Total</strong></th>
                                                <th><div id="total"><strong><c:out value="${carrinho.getTotal()}" /></strong></div></th>
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
                    </c:if>
                    
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>
        
        <!-- Mensagem -->
        <c:import url = "/helper/message.jsp"/>

        <!-- Conteúdo -->
        <div class="container">
            <c:if test="${page !=  NULL}">
                <c:import url = "${page}"/>
            </c:if>

            <c:if test="${page ==  NULL}">
                <c:import url = "home.jsp"/>
            </c:if>
        </div>
    </body>
</html>
