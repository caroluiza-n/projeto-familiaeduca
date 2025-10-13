package com.projeto.familiaeduca.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Cardapio {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String descricao;

    private LocalDate dataPublicacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_diretor", nullable = false)
    private Diretor diretor;

    @ManyToMany
    @JoinTable(name = "aviso_professor", joinColumns = @JoinColumn(name = "id_aviso"), inverseJoinColumns = @JoinColumn(name = "id_professor"))
    private Set<Professor> avisosProfessores = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "aviso_responsavel", joinColumns = @JoinColumn(name = "id_aviso"), inverseJoinColumns = @JoinColumn(name = "id_responsavel"))
    private Set<Responsavel> avisosResponsaveis = new HashSet<>();
}
