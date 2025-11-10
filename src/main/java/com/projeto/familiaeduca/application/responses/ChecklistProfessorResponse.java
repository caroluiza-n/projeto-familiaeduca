package com.projeto.familiaeduca.application.responses;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

/* Formato de resposta da classe ChecklistProfessor que a API vai entregar */
@Getter
@Setter
public class ChecklistProfessorResponse {
    private UUID id;
    private LocalDate dataChecklist;
    private String observacoes;
    private AlunoResumeResponse aluno;
    private ProfessorResumeResponse professor;

    @Getter
    @Setter
    public static class AlunoResumeResponse {
        private int matricula;
        private String nome;
    }

    @Getter
    @Setter
    public static class ProfessorResumeResponse {
        private UUID id;
        private String nome;
    }
}