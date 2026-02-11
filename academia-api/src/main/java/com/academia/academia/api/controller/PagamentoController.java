package com.academia.academia.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.academia.academia.api.model.Pagamento;
import com.academia.academia.api.service.PagamentoService;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    private PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<Pagamento> gerar(@RequestBody Pagamento pagamento) {
        return ResponseEntity.ok(pagamentoService.gerarPagamento(pagamento));
    }

    @PutMapping("/{id}/pagar")
    public ResponseEntity<Pagamento> pagar(@PathVariable Long id) {
        return ResponseEntity.ok(pagamentoService.pagar(id));
    }

    @GetMapping
    public ResponseEntity<List<Pagamento>> listar() {
        return ResponseEntity.ok(pagamentoService.listarTodos());
    }
}
