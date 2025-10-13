package com.projeto.familiaeduca.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Year;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Boletim {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String bimestre;

    @Column(nullable = false)
    private Year ano;

    @Lob
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aluno", nullable = false)
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_diretor", nullable = false)
    private Diretor diretor;
}
