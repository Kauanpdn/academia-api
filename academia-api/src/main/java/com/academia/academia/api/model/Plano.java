package com.academia.academia.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "plano")

public class Plano {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private double valor;
    private int duracaoMeses;
    private String descricao;

    public Plano(){}

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
}
