package br.com.lucas.twgerenciadortarefas.repositories;

import br.com.lucas.twgerenciadortarefas.modelos.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioTarefa extends JpaRepository<Tarefa, Long> {
}
