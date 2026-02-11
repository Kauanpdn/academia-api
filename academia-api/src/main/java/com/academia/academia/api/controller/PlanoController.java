package com.academia.academia.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.academia.academia.api.model.Plano;
import com.academia.academia.api.service.PlanoService;

@RestController
@RequestMapping("/api/planos")
public class PlanoController {
    
    private PlanoService planoService;

    public PlanoController (PlanoService planoService) {
        this.planoService = planoService;
        
    }

    @PostMapping
    public ResponseEntity<Plano> criar(@RequestBody Plano plano) {
        return ResponseEntity.ok(planoService.cadastrarPlano(plano));
    }

    @GetMapping
    public ResponseEntity<List<Plano>> listar() {
        return ResponseEntity.ok(planoService.listarPlanos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plano> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(planoService.buscarPorId(id));
    }
}
