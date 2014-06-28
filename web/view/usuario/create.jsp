<h1>Cadastro de Usuário</h1>
<form role="form" action="UsuarioController?action=create" method="POST">
    <div class="form-group">
        <label for="nome">Nome</label>
        <input type="text" name="nome"  class="form-control" id="nome" placeholder="Entre com o nome">
    </div>

    <div class="form-group">
        <label for="cpf">CPF</label>
        <input type="text" name="cpf"  class="form-control" id="cpf" placeholder="xxx.xxx.xxx-xx">
    </div>

    <div class="form-group">
        <label for="endereco">Endereço</label>
        <input type="text" name="endereco"  class="form-control" id="endereco" placeholder="Endereço, nº">
    </div>

    <div class="form-group">
        <label for="telefone">Telefone</label>
        <input type="tel" name="telefone"  class="form-control" id="telefone" placeholder="(xx) xxxx-xxxx">
    </div>
    
    <div class="form-group">
        <label for="username">Login</label>
        <input type="text" name="username"  class="form-control" id="username" placeholder="Entre com o login">
    </div>
    
    <div class="form-group">
        <label for="password">Senha</label>
        <input type="password" name="password"  class="form-control" id="password" placeholder="Entre com a senha">
    </div>


    <button type="submit" class="btn btn-default">Cadastrar</button>
</form>