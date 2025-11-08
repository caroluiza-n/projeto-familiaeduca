package com.projeto.familiaeduca.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "turma_disciplina",
            joinColumns = @JoinColumn(name = "id_turma"),
            inverseJoinColumns = @JoinColumn(name = "id_disciplina")
    )
    private Set<Disciplina> disciplinas = new HashSet<>();
}
