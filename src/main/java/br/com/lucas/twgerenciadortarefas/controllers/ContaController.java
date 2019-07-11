package br.com.lucas.twgerenciadortarefas.controllers;

import br.com.lucas.twgerenciadortarefas.modelos.Usuario;
import br.com.lucas.twgerenciadortarefas.servicos.ServicoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ContaController {

    @Autowired
    private ServicoUsuario servicoUsuario;

    @GetMapping("/login")
    public String login(){
        return "conta/login";
    }

    @GetMapping("/registration")
    public ModelAndView registration(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("conta/registrar");
        mv.addObject("usuario", new Usuario());
        return mv;
    }

    @PostMapping("/registration")
    public ModelAndView registration(@Valid Usuario usuario, BindingResult result){

        ModelAndView mv = new ModelAndView();
        Usuario usr = servicoUsuario.encontrarPorEmail(usuario.getEmail());
        if(usr != null){
            result.rejectValue("email", "", "Usuário ja cadastrado");
        }
        if(result.hasErrors()){
            mv.setViewName("conta/registrar");
            mv.addObject("usuario", usuario);
        }else {
            servicoUsuario.salvar(usuario);
            mv.setViewName("redirect:/login");
        }
        return mv;
    }
}
