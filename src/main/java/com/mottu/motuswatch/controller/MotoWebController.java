package com.mottu.motuswatch.controller;

import com.mottu.motuswatch.dto.MotoDTO;
import com.mottu.motuswatch.model.AreaPatio;
import com.mottu.motuswatch.model.ModeloMoto;
import com.mottu.motuswatch.service.MotoService;
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
@RequestMapping("/motos")
public class MotoWebController {

    private final MotoService motoService;

    public MotoWebController(MotoService motoService) {
        this.motoService = motoService;
    }

    @GetMapping
    public String listarMotos(@RequestParam(required = false) String area,
                             @RequestParam(required = false) String placa,
                             Model model, 
                             @PageableDefault(size = 10) Pageable pageable) {
        Page<MotoDTO> motos;
        
        if (area != null && !area.isEmpty()) {
            motos = motoService.buscarPorArea(area, pageable);
        } else if (placa != null && !placa.isEmpty()) {
            motos = motoService.buscarPorPlaca(placa, pageable);
        } else {
            motos = motoService.findAll(pageable);
        }
        
        model.addAttribute("motos", motos);
        model.addAttribute("areas", AreaPatio.values());
        model.addAttribute("modelos", ModeloMoto.values());
        model.addAttribute("areaSelecionada", area);
        model.addAttribute("placaSelecionada", placa);
        return "motos/lista";
    }

    @GetMapping("/novo")
    public String novaMoto(Model model) {
        model.addAttribute("moto", new MotoDTO());
        model.addAttribute("areas", AreaPatio.values());
        model.addAttribute("modelos", ModeloMoto.values());
        model.addAttribute("isNew", true);
        return "motos/form";
    }

    @GetMapping("/{id}/editar")
    public String editarMoto(@PathVariable Long id, Model model) {
        MotoDTO moto = motoService.findById(id);
        model.addAttribute("moto", moto);
        model.addAttribute("areas", AreaPatio.values());
        model.addAttribute("modelos", ModeloMoto.values());
        model.addAttribute("isNew", false);
        return "motos/form";
    }

    @PostMapping
    public String salvarMoto(@Valid @ModelAttribute("moto") MotoDTO moto, 
                             BindingResult result, 
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "motos/form";
        }
        
        try {
            motoService.save(moto);
            redirectAttributes.addFlashAttribute("msgSuccess", "Moto salva com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("msgError", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msgError", "Erro ao salvar moto: " + e.getMessage());
        }
        
        return "redirect:/motos";
    }

    @PostMapping("/{id}")
    public String atualizarMoto(@PathVariable Long id, 
                               @Valid @ModelAttribute("moto") MotoDTO moto, 
                               BindingResult result, 
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "motos/form";
        }
        
        try {
            MotoDTO motoAtualizada = new MotoDTO(
                id, moto.placa(), moto.modelo(), moto.area(), moto.observacao(),
                moto.dataEntrada(), moto.usuarioId(),
                moto.dataCriacao(), moto.dataAtualizacao(), moto.ativo()
            );
            motoService.save(motoAtualizada);
            redirectAttributes.addFlashAttribute("msgSuccess", "Moto atualizada com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("msgError", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msgError", "Erro ao atualizar moto: " + e.getMessage());
        }
        
        return "redirect:/motos";
    }

    @GetMapping("/{id}/excluir")
    public String excluirMoto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            motoService.deleteById(id);
            redirectAttributes.addFlashAttribute("msgSuccess", "Moto excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msgError", "Erro ao excluir moto: " + e.getMessage());
        }
        
        return "redirect:/motos";
    }

}
