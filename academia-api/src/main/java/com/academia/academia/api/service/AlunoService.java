package com.academia.academia.api.service;

import com.academia.academia.api.model.Aluno;
import com.academia.academia.api.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    // cadastrando aluno
    public Aluno cadastrarAluno(Aluno aluno) {
        validarAluno(aluno);

        aluno.setStatus("ATIVO");

        return alunoRepository.save(aluno);
    }

    // atualizar aluno
    public Aluno atualizarAluno(Aluno aluno) {

        if (aluno.getId() == null) {
            throw new RuntimeException("ID do aluno inválido para atualização");
        }

        validarAluno(aluno);

        return alunoRepository.save(aluno);
    }

    // desativando aluno
    public void desativarAluno(Long alunoId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
            
            if ("INATIVO".equals(aluno.getStatus())) {
                throw new RuntimeException("Aluno já está inativo");
            }

            aluno.setStatus("INATIVO");
            alunoRepository.save(aluno);
    }

    //ativar aluno
    public void ativarAluno(Long alunoId){
        Aluno aluno = alunoRepository.findById(alunoId)
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

            aluno.setStatus("ATIVO");
            alunoRepository.save(aluno);
    }
    

    // validando informações do aluno e aplicando estratégia de negócio
    public void validarAluno(Aluno aluno) {
        if (aluno == null) {
            throw new RuntimeException("Aluno não pode ser nulo");
        }

        if (aluno.getNome() == null || aluno.getNome().isBlank()) {
            throw new RuntimeException("O nome é obrigatório");
        }

        if (aluno.getNome().length() < 3) {
            throw new RuntimeException("Nome deve ter no mínimo 3 caracteres");
        }

        if (aluno.getEmail() == null || aluno.getEmail().isBlank()) {
            throw new RuntimeException("Email é obrigatório");
        }

        // ⚠️ aqui tinha um bug no seu código antigo
        if (!aluno.getEmail().contains("@")) {
            throw new RuntimeException("Email inválido");
        }

        if (aluno.getTelefone() == null || aluno.getTelefone().isBlank()) {
            throw new RuntimeException("Telefone é obrigatório");
        }

        if (aluno.getDataNascimento() == null) {
            throw new RuntimeException("Data de nascimento é obrigatória");
        }

        if (aluno.getDataNascimento().isAfter(LocalDate.now())) {
            throw new RuntimeException("Data de nascimento não pode ser futura");
        }

        int idade = calcularIdade(aluno.getDataNascimento());

        if (idade < 14) {
            throw new RuntimeException("Aluno deve ter no mínimo 14 anos");
        }
    }

    private int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

}
