package com.academia.academia.api.service;

import com.academia.academia.api.model.*;
import com.academia.academia.api.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;
    private final PlanoRepository planoRepository;

    public MatriculaService(
            MatriculaRepository matriculaRepository,
            AlunoRepository alunoRepository,
            PlanoRepository planoRepository
    ) {
        this.matriculaRepository = matriculaRepository;
        this.alunoRepository = alunoRepository;
        this.planoRepository = planoRepository;
    }

    public Matricula criarMatricula(Long alunoId, Long planoId) {

        log.info("Iniciando criação de matrícula | alunoId={} | planoId={}", alunoId, planoId);

        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> {
                    log.warn("Aluno não encontrado | id={}", alunoId);
                    return new RuntimeException("Aluno não encontrado");
                });

        Plano plano = planoRepository.findById(planoId)
                .orElseThrow(() -> {
                    log.warn("Plano não encontrado | id={}", planoId);
                    return new RuntimeException("Plano não encontrado");
                });

        boolean existeAtiva =
                matriculaRepository.existsByAlunoAndStatus(aluno, "ATIVA");

        if (existeAtiva) {
            log.warn("Aluno já possui matrícula ativa | alunoId={}", alunoId);
            throw new RuntimeException("Aluno já possui matrícula ativa");
        }

        Matricula matricula = new Matricula();
        matricula.setAlunoId(alunoId);
        matricula.setPlanoId(planoId);
        matricula.setDataInicio(LocalDate.now());
        matricula.setStatus("ATIVA");
        matricula.setValorContratado(plano.getValor());

        Matricula salva = matriculaRepository.save(matricula);

        log.info("Matrícula criada com sucesso | matriculaId={}", salva.getId());

        return salva;
    }

    public void cancelarMatricula(Long matriculaId) {

        log.info("Solicitação de cancelamento | matriculaId={}", matriculaId);

        Matricula matricula = matriculaRepository.findById(matriculaId)
                .orElseThrow(() -> {
                    log.warn("Matrícula não encontrada | id={}", matriculaId);
                    return new RuntimeException("Matrícula não encontrada");
                });

        matricula.setStatus("CANCELADA");
        matriculaRepository.save(matricula);

        log.info("Matrícula cancelada com sucesso | matriculaId={}", matriculaId);
    }
}
