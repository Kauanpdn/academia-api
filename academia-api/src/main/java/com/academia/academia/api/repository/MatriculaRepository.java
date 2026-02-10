package com.academia.academia.api.repository;

import com.academia.academia.api.model.Matricula;
import com.academia.academia.api.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    // lista por aluno
    List<Matricula> findByAluno(Aluno aluno);

    // verifica matrícula ativa
    boolean existsByAlunoAndStatus(Aluno aluno, String status);
}
