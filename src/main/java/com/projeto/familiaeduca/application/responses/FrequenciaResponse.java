package com.projeto.familiaeduca.application.responses;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

/* Formato de resposta da classe Frequencia que a API vai entregar */
@Getter
@Setter
public class FrequenciaResponse {
    private UUID id;
    private LocalDate data;
    private boolean presente;
    private AlunoResumeResponse aluno;
    private TurmaResumeResponse turma;

    @Getter
    @Setter
    public static class AlunoResumeResponse {
        private int matricula;
        private String nome;
    }

    @Getter
    @Setter
    public static class TurmaResumeResponse {
        private UUID id;
        private String nome;
    }
}