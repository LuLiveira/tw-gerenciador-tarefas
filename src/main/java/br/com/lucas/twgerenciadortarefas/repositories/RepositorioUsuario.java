package br.com.lucas.twgerenciadortarefas.repositories;

import br.com.lucas.twgerenciadortarefas.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);
}
