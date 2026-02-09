package com.academia.academia.api.repository;

import com.academia.academia.api.model.Plano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlanoRepository extends JpaRepository <Plano, Integer> {
    
}
