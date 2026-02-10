package com.academia.academia.api.service;

import com.academia.academia.api.model.Pagamento;
import com.academia.academia.api.repository.MatriculaRepository;
import com.academia.academia.api.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final MatriculaRepository matriculaRepository;

    public Pagamento gerarPagamento(Pagamento pagamento) {

        log.info("Iniciando geração de pagamento para matrícula {}", pagamento.getMatriculaId());

        if (!matriculaRepository.existsById(pagamento.getMatriculaId())) {
            log.warn("Matrícula {} não encontrada", pagamento.getMatriculaId());
            throw new RuntimeException("Matrícula não encontrada!");
        }

        if (pagamento.getValor().doubleValue() <= 0) {
            throw new RuntimeException("Valor do pagamento inválido!");
        }

        if (pagamento.getDataVencimento() == null) {
            throw new RuntimeException("Data de vencimento obrigatória!");
        }

        pagamento.setStatus("PENDENTE");
        pagamento.setDataPagamento(null);

        Pagamento salvo = pagamentoRepository.save(pagamento);

        log.info("Pagamento {} gerado com sucesso", salvo.getId());

        return salvo;
    }

    public Pagamento pagar(Long pagamentoId) {

        log.info("Realizando pagamento ID {}", pagamentoId);

        Pagamento pagamento = pagamentoRepository.findById(pagamentoId)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado!"));

        if ("PAGO".equals(pagamento.getStatus())) {
            throw new RuntimeException("Pagamento já foi realizado!");
        }

        pagamento.setStatus("PAGO");
        pagamento.setDataPagamento(LocalDate.now());

        return pagamentoRepository.save(pagamento);
    }

    public List<Pagamento> listarTodos() {
        return pagamentoRepository.findAll();
    }
}
