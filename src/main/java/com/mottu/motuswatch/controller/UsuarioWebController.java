package com.mottu.motuswatch.controller;

import com.mottu.motuswatch.dto.UsuarioDTO;
import com.mottu.motuswatch.model.PerfilUsuario;
import com.mottu.motuswatch.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioWebController {

    private final UsuarioService usuarioService;

    public UsuarioWebController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarUsuarios(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<UsuarioDTO> usuarios = usuarioService.findAll(pageable);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("perfis", PerfilUsuario.values());
        return "usuarios/lista";
    }

    @GetMapping("/novo")
    public String novoUsuario(Model model) {
        model.addAttribute("usuario", new UsuarioDTO());
        model.addAttribute("perfis", PerfilUsuario.values());
        model.addAttribute("isNew", true);
        return "usuarios/form";
    }

    @GetMapping("/{id}/editar")
    public String editarUsuario(@PathVariable Long id, Model model) {
        UsuarioDTO usuario = usuarioService.findById(id);
        model.addAttribute("usuario", usuario);
        model.addAttribute("perfis", PerfilUsuario.values());
        model.addAttribute("isNew", false);
        return "usuarios/form";
    }

    @PostMapping
    public String salvarUsuario(@Valid @ModelAttribute("usuario") UsuarioDTO usuario, 
                                BindingResult result, 
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "usuarios/form";
        }
        
        try {
            usuarioService.save(usuario);
            redirectAttributes.addFlashAttribute("msgSuccess", "Usuário salvo com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msgError", "Erro ao salvar usuário: " + e.getMessage());
        }
        
        return "redirect:/usuarios";
    }

    @PostMapping("/{id}")
    public String atualizarUsuario(@PathVariable Long id, 
                                  @Valid @ModelAttribute("usuario") UsuarioDTO usuario, 
                                  BindingResult result, 
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "usuarios/form";
        }
        
        try {
            UsuarioDTO usuarioAtualizado = new UsuarioDTO(
                id, usuario.nome(), usuario.telefone(), usuario.cpf(),
                usuario.email(), usuario.senha(), usuario.perfil(),
                usuario.dataCriacao(), usuario.dataAtualizacao(), usuario.ativo()
            );
            usuarioService.save(usuarioAtualizado);
            redirectAttributes.addFlashAttribute("msgSuccess", "Usuário atualizado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msgError", "Erro ao atualizar usuário: " + e.getMessage());
        }
        
        return "redirect:/usuarios";
    }

    @GetMapping("/{id}/excluir")
    public String excluirUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.deleteById(id);
            redirectAttributes.addFlashAttribute("msgSuccess", "Usuário excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msgError", "Erro ao excluir usuário: " + e.getMessage());
        }
        
        return "redirect:/usuarios";
    }
}
