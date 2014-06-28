<%@page import="model.Usuario"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<h1>Pedido: informações</h1><br>

<form class="form-horizontal" role="form" action="PedidoController?action=add" method="POST">
    <!-- se funcionário, interação trava -->
    <c:choose>
        <c:when test="${user.getTipo() == true}">
            <input type="hidden" id="interacao" name="interacao" value="LF" />
        </c:when>
        <c:otherwise>
            <div class="form-group">
                <label for="interacao" class="col-sm-3 control-label">Forma de entrega</label>
                <div class="col-sm-2">
                    <select id="interacao" name="interacao">
                        <option value="LC">Local</option>
                        <option value="RM">Delivery</option>
                    </select>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
    
    <div class="form-group">
        <label for="pagamento" class="col-sm-3 control-label">Forma de pagamento</label>
        <div class="col-sm-2">
            <select id="pagamento" name="pagamento" class="form-control" required>
                <option value="false">Cartão</option>
                <option value="true">Dinheiro</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label for="mesa" class="col-sm-3 control-label">Mesa</label>
        <div class="col-sm-2">
            <input type="number" id="mesa" name="mesa" min="1" max="100" class="form-control" required />
        </div>
    </div>        
           
    <div class="form-group">
        <div class="col-sm-offset-3 col-sm-10">
            <button type="submit" class="btn btn-success">
                Fechar pedido <span class="glyphicon glyphicon-ok"></span>
            </button>
            <a href="CarrinhoController?action=limpar">
                <button type="button" class="btn btn-danger">
                    Cancelar pedido <span class="glyphicon glyphicon-remove"></span>
                </button>
            </a> 
        </div>
    </div>
        
</form>

<script>
    $('#interacao').blur(function(){
        if ($(this).val() === "RM") {
            $('#mesa').prop('disabled', true);
            $('#pagamento').prop('disabled', true);
        }
    });

    $('#interacao').change(function(){
        if ($(this).val() === "RM") {
            $('#mesa').prop('disabled', true);
            $('#pagamento').prop('disabled', true);
        } else {
            $('#mesa').prop('disabled', false);
            $('#pagamento').prop('disabled', false);
        }
    });
</script>
