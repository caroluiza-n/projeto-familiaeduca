package com.projeto.familiaeduca.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Responsavel extends Usuario {
    @Column(nullable = false)
    private String endereco;

    @OneToMany(mappedBy = "responsavel")
    private List<Aluno> alunos = new ArrayList<>();

    @OneToMany(mappedBy = "responsavel")
    private List<Justificativa> justificativasEnviadas = new ArrayList<>();

    @OneToMany(mappedBy = "responsavel")
    private List<RenovacaoMatricula> renovacoesSolicitadas = new ArrayList<>();

    @OneToMany(mappedBy = "responsavel")
    private List<ChecklistResponsavel> checklistsPreenchidos = new ArrayList<>();
}
