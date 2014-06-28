<h1>Login</h1>

<form role="form" action="UsuarioController?action=login" method="POST">
    
    <div class="form-group">
        <label for="username">Login</label>
        <input type="text" name="username"  class="form-control" id="username" placeholder="Entre com o login">
    </div>

    <div class="form-group">
        <label for="password">Senha</label>
        <input type="password" name="password"  class="form-control" id="password" placeholder="Entre com a senha">
    </div>
    
    <button type="submit" class="btn btn-default">Logar</button>
    
</form>
