package com.gs.stormtifao.controllers;

import com.gs.stormtifao.models.PessoaPerdida;
import com.gs.stormtifao.services.PessoaPerdidaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/pessoas-perdidas")
public class PessoaPerdidaController {
    private final PessoaPerdidaService service;

    public PessoaPerdidaController(PessoaPerdidaService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pessoas", service.listarTodas());
        return "pessoas/lista";
    }

    @GetMapping("/nova")
    public String novaPessoaForm(Model model) {
        model.addAttribute("pessoaPerdida", new PessoaPerdida());
        return "pessoas/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute PessoaPerdida pessoa,
                         @RequestParam("fileFoto") MultipartFile fileFoto) {
        if (fileFoto != null && !fileFoto.isEmpty()) {
            try {
                String base64 = Base64.getEncoder().encodeToString(fileFoto.getBytes());
                pessoa.setFotoBase64(base64);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        service.salvar(pessoa);
        return "redirect:/pessoas-perdidas";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        PessoaPerdida pessoa = service.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));
        model.addAttribute("pessoaPerdida", pessoa);
        return "pessoas/form";
    }

    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable Long id,
                            @ModelAttribute PessoaPerdida pessoa,
                            @RequestParam("fileFoto") MultipartFile fileFoto) {
        if (fileFoto != null && !fileFoto.isEmpty()) {
            try {
                String base64 = Base64.getEncoder().encodeToString(fileFoto.getBytes());
                pessoa.setFotoBase64(base64);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            PessoaPerdida existente = service.buscarPorId(id).orElse(null);
            if (existente != null) {
                pessoa.setFotoBase64(existente.getFotoBase64());
            }
        }
        pessoa.setId(id);
        service.salvar(pessoa);
        return "redirect:/pessoas-perdidas";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/pessoas-perdidas";
    }
}
