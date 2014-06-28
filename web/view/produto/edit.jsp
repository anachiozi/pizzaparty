<%@page import="model.Produto"%>

<%
    Produto produto = new Produto();
    produto = (Produto) request.getAttribute("produto");
%>

<h1>Cadastro de Produto</h1>

<form role="form" action="ProdutoController?action=update&id=<%= produto.getId()%>" method="POST">
    <div class="form-group">
        <label for="nome">Nome</label>
        <input type="text" name="nome"  class="form-control" id="nome" placeholder="Entre com o nome" value="<%= produto.getNome()%>">
    </div>

    <%if (!produto.getTipo().equals("Pizza")) {%>
    <div class="form-group">
        <label for="qtd">Quantidade</label>
        <input type="number" name="qtd"  class="form-control" id="qtd" placeholder="Entre com a quantidade" value="<%= produto.getQtd()%>">
    </div>
    <%}%>

    <div class="form-group">
        <label for="tipo">Tipo</label>
        <select name="tipo" id="tipo">
            <option selected="<%= produto.getTipo()%>"><%= produto.getTipo()%></option>
            <%if (!produto.getTipo().equals("Ingrediente")){%>
            <option value="Ingrediente">Ingrediente</option>
            <%}%>
            <%if (!produto.getTipo().equals("Pizza")){%>
            <option value="Pizza">Pizza</option>
            <%}%>
            <%if (!produto.getTipo().equals("Outro")){%>
            <option value="Outro">Outro</option>
            <%}%>
            <%if (!produto.getTipo().equals("Bebida")){%>
            <option value="Bebida">Bebida</option>
            <%}%>
        </select>

    </div>

    <div class="form-group">
        <label for="preco">Preço</label>
        <input type="text" name="preco"  class="form-control" id="preco" placeholder="Entre com o preco" value="<%= produto.getPreco()%>">
    </div>

    <button type="submit" class="btn btn-default">Cadastrar</button>
</form>