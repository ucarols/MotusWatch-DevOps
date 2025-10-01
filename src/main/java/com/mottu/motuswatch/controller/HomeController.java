package com.mottu.motuswatch.controller;

import com.mottu.motuswatch.service.MotoService;
import com.mottu.motuswatch.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final MotoService motoService;
    private final UsuarioService usuarioService;

    public HomeController(MotoService motoService, UsuarioService usuarioService) {
        this.motoService = motoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String home(Model model) {
        long totalMotos = motoService.count();
        long totalUsuarios = usuarioService.count();
        
        model.addAttribute("totalMotos", totalMotos);
        model.addAttribute("totalUsuarios", totalUsuarios);
        
        return "index";
    }
}
