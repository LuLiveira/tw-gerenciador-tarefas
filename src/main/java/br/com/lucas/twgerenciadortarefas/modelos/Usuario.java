package br.com.lucas.twgerenciadortarefas.modelos;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "usr_usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Long id;

    @Column(name = "usr_email", nullable = false, length = 100)
    @NotNull(message = "E-mail obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @Column(name = "usr_senha", nullable = false, length = 100)
    @NotNull(message = "Senha obrigatória")
    private String senha;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY) //One (um usuario) To Many (varias tarefas) OneToMany One = Usuario (classe) / Many = Tarefas (atributo).
    private List<Tarefa> tarefas;

    public Usuario() {
    }

    public Usuario(Long id, String email, String senha) {
        this.id = id;
        this.email = email;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }
}
