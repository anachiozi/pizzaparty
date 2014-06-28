<h1>Cadastro de Produto</h1>
<form role="form" action="ProdutoController?action=create" method="POST">
    <div class="form-group">
        <label for="nome">Nome</label>
        <input type="text" name="nome"  class="form-control" id="nome" placeholder="Entre com o nome">
    </div>
    
    <div class="form-group">
        <label for="tipo">Tipo</label>
        <select name="tipo" id="tipo">
            <option value="Ingrediente">Ingrediente</option>
            <option value="Pizza">Pizza</option>
            <option value="Outro">Outro</option>
        </select>
    </div>

    <div class="form-group">
        <label for="qtd">Quantidade</label>
        <input type="number" name="qtd"  class="form-control" id="qtd" placeholder="Entre com a quantidade">
    </div>

    <div class="form-group">
        <label for="preco">Preço</label>
        <input type="text" name="preco"  class="form-control" id="preco" placeholder="Entre com o preco">
    </div>


    <button type="submit" class="btn btn-default">Cadastrar</button>
</form>

<script>
    $('#tipo').blur(function(){
        if ($(this).val() === "Pizza") {
            $('#qtd').prop('disabled', true);
        } else {
            $('#qtd').prop('disabled', false);
        }
    });

    $('#tipo').change(function(){
        if ($(this).val() === "Pizza") {
            $('#qtd').prop('disabled', true);
        } else {
            $('#qtd').prop('disabled', false);
        }
    });
</script>