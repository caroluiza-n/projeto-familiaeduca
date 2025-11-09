package com.projeto.familiaeduca.application.responses;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class NotaResponse {
    private UUID id;
    private String disciplina;
    private String nota;
    private LocalDate dataAvaliacao;
    private AlunoResumeResponse aluno;
    private TurmaResumeResponse turma;

    @Getter @Setter
    public static class AlunoResumeResponse {
        private int matricula;
        private String nome;
    }

    @Getter @Setter
    public static class TurmaResumeResponse {
        private UUID id;
        private String nome;
    }
}