package com.academia.academia.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.academia.academia.api.model.Matricula;
import com.academia.academia.api.service.MatriculaService;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController {
    private MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @PostMapping
    public ResponseEntity<Matricula> criar(
            @RequestParam Long alunoId,
            @RequestParam Long planoId) {

        return ResponseEntity.ok(
                matriculaService.criarMatricula(alunoId, planoId));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        matriculaService.cancelarMatricula(id);
        return ResponseEntity.noContent().build();
    }
}
