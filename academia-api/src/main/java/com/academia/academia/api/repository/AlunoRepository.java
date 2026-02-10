package com.academia.academia.api.repository;

import com.academia.academia.api.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;



@Repository
public interface AlunoRepository extends JpaRepository <Aluno, Long> {

    // buscar aluno por email
    Optional<Aluno> findByEmail(String email);
    
    //verificar se exise email
    boolean existsByEmail(String email);
} 
