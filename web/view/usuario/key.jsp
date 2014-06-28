<h1>Editar chave</h1>

<form role="form" action="UsuarioController?action=key" method="POST">
    
    <div class="form-group">
        <label for="oldkey">Chave atual</label>
        <input type="password" name="oldkey"  class="form-control" id="oldkey" placeholder="Entre com o chave atual">
    </div>

    <div class="form-group">
        <label for="newkey">Nova chave</label>
        <input type="password" name="newkey"  class="form-control" id="newkey" placeholder="Entre com a nova chave">
    </div>
    
    <button type="submit" class="btn btn-default">Alterar</button>
    
</form>

