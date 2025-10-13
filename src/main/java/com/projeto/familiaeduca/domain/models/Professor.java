package com.projeto.familiaeduca.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Professor extends Usuario {
    @OneToOne(mappedBy = "professor")
    private Turma turma;

    @OneToMany(mappedBy = "professorAplicador")
    private List<Medicacao> medicacoesAplicadas = new ArrayList<>();

    @OneToMany(mappedBy = "professor")
    private List<ChecklistProfessor> checklistsPreenchidos = new ArrayList<>();
}
