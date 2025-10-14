package com.projeto.familiaeduca.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_professor", nullable = false, unique = true)
    private Professor professor;

    @OneToMany(mappedBy = "turma")
    private List<Aluno> alunos = new ArrayList<>();
}
