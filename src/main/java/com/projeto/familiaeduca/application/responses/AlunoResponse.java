package com.projeto.familiaeduca.application.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class AlunoResponse {
    private int matricula;
    private String nome;
    private LocalDate dataNascimento;
    private String laudo;
    private String alergias;
    private TurmaResumeResponse turma;
    private ResponsavelResumeResponse responsavel;

    @Getter
    @Setter
    public static class TurmaResumeResponse {
        private UUID id;
        private String nome;
    }

    @Getter
    @Setter
    public static class ResponsavelResumeResponse {
        private UUID id;
        private String nome;
    }
}
