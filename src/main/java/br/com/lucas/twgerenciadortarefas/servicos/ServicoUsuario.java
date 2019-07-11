package br.com.lucas.twgerenciadortarefas.servicos;

import br.com.lucas.twgerenciadortarefas.modelos.Usuario;
import br.com.lucas.twgerenciadortarefas.repositories.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServicoUsuario {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Usuario encontrarPorEmail(String email){
        return repositorioUsuario.findByEmail(email);
    }

    public void salvar(Usuario usuario){

        //Criptografando a senha do usuário de forma unidirecional.
        usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
        repositorioUsuario.save(usuario);
    }
}
