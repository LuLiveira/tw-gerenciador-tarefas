package br.com.lucas.twgerenciadortarefas.controllers;

import br.com.lucas.twgerenciadortarefas.modelos.Tarefa;
import br.com.lucas.twgerenciadortarefas.modelos.Usuario;
import br.com.lucas.twgerenciadortarefas.repositories.RepositorioTarefa;
import br.com.lucas.twgerenciadortarefas.servicos.ServicoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping(value = "/tarefas")
public class TarefasController {

    @Autowired
    private RepositorioTarefa repositorioTarefa;

    @Autowired
    private ServicoUsuario servicoUsuario;

    @GetMapping("/listar")
    public ModelAndView listar(HttpServletRequest request){

        ModelAndView mv = new ModelAndView();
        mv.setViewName("tarefas/listar");
        String emailUsuario = request.getUserPrincipal().getName();
        mv.addObject("tarefas", repositorioTarefa.carregarTarefasPorUsuario(emailUsuario));
        return mv;
    }

    @GetMapping("/inserir")
    public ModelAndView inserir(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("tarefas/inserir");
        mv.addObject("tarefa", new Tarefa());
        return mv;
    }

    @PostMapping("/inserir")
    public ModelAndView inserir(@Valid Tarefa tarefa, BindingResult result, HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        if(tarefa.getDataExpiracao() == null){
            result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida", "A data de expiracao é obrigatória.");
        }else {
            if (tarefa.getDataExpiracao().before(new Date())) {
                result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida", "A data de expiracao não pode ser anterior a data atual.");
            }
        }
        if(result.hasErrors()){
            mv.setViewName("tarefas/inserir");
            mv.addObject(tarefa);
        }else {
            String emailUsuario = request.getUserPrincipal().getName();
            Usuario usuarioLogado = servicoUsuario.encontrarPorEmail(emailUsuario);
            tarefa.setUsuario(usuarioLogado);
            repositorioTarefa.save(tarefa);
            mv.setViewName("redirect:/tarefas/listar");
        }
        return mv;
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView alterar(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView();
        Tarefa tarefa = repositorioTarefa.getOne(id);
        mv.setViewName("/tarefas/alterar");
        mv.addObject("tarefa", tarefa);
        return mv;
    }

    @PostMapping("/alterar")
    public ModelAndView alterar(@Valid Tarefa tarefa, BindingResult result){
        ModelAndView mv = new ModelAndView();
        if(tarefa.getDataExpiracao() == null){
            result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida", "A data de expiracao é obrigatória.");
        }else {
            if (tarefa.getDataExpiracao().before(new Date())) {
                result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida", "A data de expiracao não pode ser anterior a data atual.");
            }
        }
        if(result.hasErrors()){
            mv.setViewName("tarefas/alterar");
            mv.addObject(tarefa);
        }else {
            mv.setViewName("redirect:/tarefas/listar");
            repositorioTarefa.save(tarefa);
        }
        return mv;
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id){
        repositorioTarefa.deleteById(id);
        return "redirect:/tarefas/listar";
    }

    @GetMapping("/concluir/{id}")
    public String concluir(@PathVariable("id") Long id){
        Tarefa tarefa = repositorioTarefa.getOne(id);
        tarefa.setConcluida(true);
        repositorioTarefa.save(tarefa);
        return "redirect:/tarefas/listar";
    }

}
