package com.projeto.familiaeduca.application.responses;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

/* Formato de resposta da classe Turma que a API vai entregar */
@Getter
@Setter
public class TurmaResponse {
    private UUID id;
    private String nome;
    private ProfessorResumeResponse professor;
    private List<AlunoResumeResponse> alunos;

    @Getter
    @Setter
    public static class ProfessorResumeResponse {
        private UUID id;
        private String nome;
    }

    @Getter
    @Setter
    public static class AlunoResumeResponse {
        private int matricula;
        private String nome;
    }
}
